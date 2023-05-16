package com.twoics.geo.ui.screens.search

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoics.geo.data.models.Bookmark
import com.twoics.geo.data.models.BookmarkType
import com.twoics.geo.map.IMap
import com.twoics.geo.nav.INavigation
import com.twoics.geo.nav.Routes
import com.twoics.geo.ui.shared.event.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.osmdroid.views.MapView

private object SearchModelConstant {
    const val MAX_SEARCH_AREA_METERS = 5000
    const val MIN_SEARCH_AREA_METERS = 500
}

class SearchViewModel(
    private val navigation: INavigation,
    private val map: IMap
) : ViewModel() {
    private var _radius by mutableStateOf(Float.MIN_VALUE)
    private var _selectedTypes = arrayListOf<BookmarkType>()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val mapView: MapView
        @Composable
        get() = map.redrawMap()

    val maxSearchRadius = SearchModelConstant.MAX_SEARCH_AREA_METERS
    val minSearchRadius = SearchModelConstant.MIN_SEARCH_AREA_METERS

    init {
        viewModelScope.launch {

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
//                navigation.navigate(Routes.BOOKMARKS)
//                return
                map.drawFoundedPlaces(
                    arrayListOf(
                        Bookmark(
                            id = 1,
                            name = "Театр Пушкина",
                            country = "Россиия",
                            city = "Красноярск",
                            street = "Мира",
                            house = "24",
                            lat = 56.0,
                            long = 93.0,
                            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit Morbi ac massa vehicula magna fringilla tempus.Morbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempusMorbi ac massa vehicula magna fringilla tempus..",
                            type = BookmarkType.CULTURE
                        ),
                        Bookmark(
                            id = 2,
                            name = "Vo Gan Udon",
                            country = "Россиия",
                            city = "Красноярск",
                            street = "Ленина",
                            house = "11",
                            lat = 56.0,
                            long = 91.0,
                            description = "Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test Test",
                            type = BookmarkType.FOOD
                        ),
                    )
                )
                //                navigation.navigate(Routes.BOOKMARKS)
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
