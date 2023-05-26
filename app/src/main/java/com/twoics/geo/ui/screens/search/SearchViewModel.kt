package com.twoics.geo.ui.screens.search

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoics.geo.api.IPlacesApi
import com.twoics.geo.api.PlaceRequest
import com.twoics.geo.data.models.Bookmark
import com.twoics.geo.data.models.BookmarkType
import com.twoics.geo.map.IMap
import com.twoics.geo.map.IMapDataTransfer
import com.twoics.geo.map.MapEvent
import com.twoics.geo.nav.INavigation
import com.twoics.geo.nav.Routes
import com.twoics.geo.settings.Languages
import com.twoics.geo.ui.shared.dto.IBookmarkTransmit
import com.twoics.geo.ui.shared.event.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.osmdroid.views.MapView

private object SearchModelConstant {
    const val MAX_SEARCH_AREA_METERS = 5000
    const val MIN_SEARCH_AREA_METERS = 500
}


val FOCUSED_PLACE = Bookmark(
    id = 2,
    name = "Vo Gan Udon",
    country = "Россиия",
    city = "Красноярск",
    street = "Ленина",
    house = "11",
    latitude = 56.0,
    longitude = 91.0,
    description = "Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test",
    type = BookmarkType.FOOD
)


class SearchViewModel(
    private val navigation: INavigation,
    private val map: IMap,
    private val mapDataTransfer: IMapDataTransfer,
    private var transmitViewModel: IBookmarkTransmit,
    private val placesApi: IPlacesApi
) : ViewModel() {
    private var _radius by mutableStateOf(Float.MIN_VALUE)
    private var _selectedTypes = arrayListOf<BookmarkType>()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val mapView: MapView
        @Composable
        get() = map.redrawMap()

    val isSearchAreaHidden: Boolean
        get() = map.isShowSearchArea

    val maxSearchRadius = SearchModelConstant.MAX_SEARCH_AREA_METERS
    val minSearchRadius = SearchModelConstant.MIN_SEARCH_AREA_METERS

    init {
        viewModelScope.launch {
            mapDataTransfer.mapEventsFlow.collect {
                when (it) {
                    is MapEvent.PeakPlace -> {
                        transmitViewModel.set(placesApi.getPlaceDetail(it.place))
                        navigation.navigate(Routes.DETAILS)
                    }
                }
            }
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.FilterButtonClicked -> {
                viewModelScope.launch {
                    updateSelectedTypes(event.bookmarkType)
                }
            }

            is SearchEvent.RadiusChanged -> {
                viewModelScope.launch {
                    _radius = event.value
                    redrawSearchCircle(event.value)
                }
            }

            is SearchEvent.OnSearchClick -> {
                sendUiEvent(UiEvent.ShowSnackbar("Empty amogus"))
                Log.d("SEARCH", "\tCenter: ${map.centerMapLocation}\n\tRadius: ${map.areaRadius}")
                val placeQuery = PlaceRequest(
                    lat = map.centerMapLocation.latitude,
                    long = map.centerMapLocation.longitude,
                    radius = map.areaRadius,
                    category = _selectedTypes,
                    language = Languages.EN
                )
                val places = placesApi.getPlaces(placeQuery)
                Log.d("PLACES", places.toString())
                map.drawFoundedPlaces(
                    places
                )
//                Log.d("DETAIL", placesApi.getPlaceDetail(places.first()).name)
            }

            is SearchEvent.CleanMapClick -> {
                map.clearPlaces()
            }

            is SearchEvent.HideMapAreaClick -> {
                map.showSearchArea(event.hide)
            }
        }
    }

    private fun updateSelectedTypes(bookmarkType: BookmarkType) {
        if (bookmarkType in _selectedTypes) {
            _selectedTypes.remove(bookmarkType)
            return
        }
        _selectedTypes.add(bookmarkType)
    }

    private fun redrawSearchCircle(value: Float) {
        val radius = value * (maxSearchRadius - minSearchRadius) + minSearchRadius
        map.drawSearchCircle(radius.toDouble())
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
