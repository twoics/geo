package com.twoics.geo.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoics.geo.data.models.BookmarkType
import com.twoics.geo.nav.INavigation
import com.twoics.geo.nav.Routes
import kotlinx.coroutines.launch


class SearchViewModel(
    private val navigation: INavigation
) : ViewModel() {
    private var _radius by mutableStateOf(Float.MIN_VALUE)
    private var _selectedTypes = arrayListOf<BookmarkType>()

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
                    updateRadius(event.value)
                }
            }

            is SearchEvent.OnSearchClick -> {
                navigation.navigate(Routes.BOOKMARKS)
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

    private fun updateRadius(value: Float) {
        _radius = value
    }
}
