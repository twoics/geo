package com.twoics.geo.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twoics.geo.data.models.BookmarkType
import kotlinx.coroutines.launch


class SearchViewModel() : ViewModel() {
    private var radius by mutableStateOf(Float.MIN_VALUE)
    private var selectedTypes = arrayListOf<BookmarkType>()

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
                // TODO
            }
        }
    }

    private fun updateSelectedTypes(bookmarkType: BookmarkType) {
        if (bookmarkType in selectedTypes) {
            selectedTypes.remove(bookmarkType)
            return
        }
        selectedTypes.add(bookmarkType)
    }

    private fun updateRadius(value: Float) {
        radius = value
    }
}
