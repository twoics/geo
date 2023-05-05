package com.twoics.geo.ui.screens.bookmarks

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoics.geo.data.models.Bookmark
import com.twoics.geo.data.repository.IBookmarksRepository
import com.twoics.geo.utils.Routes
import com.twoics.geo.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BookmarksViewModel
    (private var repository: IBookmarksRepository) : ViewModel() {

    private var _bookmarks = mutableStateListOf<Bookmark>()
    val bookmarks: List<Bookmark> = _bookmarks

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            _bookmarks.addAll(repository.getAll())
        }
    }

    fun onEvent(event: BookmarksEvent) {
        when (event) {
            is BookmarksEvent.DeleteClick -> {
                viewModelScope.launch {
                    deleteBookmark(event.bookmark)
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

    private fun deleteBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.deleteBookmark(bookmark = bookmark)
        }

        _bookmarks.remove(bookmark)
    }
}