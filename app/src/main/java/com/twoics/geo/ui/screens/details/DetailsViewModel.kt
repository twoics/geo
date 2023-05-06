package com.twoics.geo.ui.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoics.geo.data.models.Bookmark
import com.twoics.geo.data.repository.IBookmarksRepository
import com.twoics.geo.nav.INavigation
import com.twoics.geo.nav.Routes
import com.twoics.geo.ui.shared.dto.IBookmarkTransmit
import com.twoics.geo.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val navigation: INavigation,
    private val repository: IBookmarksRepository,
    private var transmitViewModel: IBookmarkTransmit
) : ViewModel() {

    var bookmark: Bookmark? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            val transmitBookmark = transmitViewModel.get();
            bookmark = transmitBookmark
        }
    }

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.BackButtonClick -> {
                viewModelScope.launch {
                }
            }

            is DetailsEvent.LikeButtonClick -> {
                viewModelScope.launch {
                    navigation.navigate(Routes.SEARCH)
                    Log.e("DETAIL", "GOT IT")
                }
            }
        }
    }
}