package com.twoics.geo.ui.screens.bookmarks

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoics.geo.data.models.Bookmark
import com.twoics.geo.data.repository.IBookmarksRepository
import com.twoics.geo.nav.INavigation
import com.twoics.geo.nav.Routes
import com.twoics.geo.ui.shared.dto.IBookmarkTransmit
import kotlinx.coroutines.launch

class BookmarksViewModel
    (
    private val navigation: INavigation,
    private var repository: IBookmarksRepository,
    private var transmitViewModel: IBookmarkTransmit
) : ViewModel() {

    private var _bookmarks = mutableStateListOf<Bookmark>()
    val bookmarks: List<Bookmark> = _bookmarks

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
                    transmitViewModel.set(event.bookmark)
                    navigation.navigate(Routes.DETAILS)
                }
            }
        }
    }

    private fun deleteBookmark(bookmark: Bookmark) {
        viewModelScope.launch {
            repository.deleteBookmark(bookmark = bookmark)
        }

        _bookmarks.remove(bookmark)
    }
}