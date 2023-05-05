package com.twoics.geo.ui.screens.bookmarks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoics.geo.data.repository.IBookmarksRepository
import com.twoics.geo.utils.Routes
import com.twoics.geo.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BookmarksViewModel
    (private var repository: IBookmarksRepository) : ViewModel() {

    var bookmarks = repository.getAll()
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {

        }
    }

    fun onEvent(event: BookmarksEvent) {
        when (event) {
            is BookmarksEvent.DeleteClick -> {
                viewModelScope.launch {
                    repository.deleteBookmark(bookmark = event.bookmark)
                    Log.d("DEBUG", "Delete")
                }
            }

            is BookmarksEvent.DetailClick -> {
                viewModelScope.launch {
                    sendUiEvent(UiEvent.Navigate(Routes.DETAILS))
                    Log.d("DEBUG", "DETAIL");
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}