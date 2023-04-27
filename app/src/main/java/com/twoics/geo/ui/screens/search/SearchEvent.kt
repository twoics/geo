package com.twoics.geo.ui.screens.search

import com.twoics.geo.data.models.BookmarkType

sealed class SearchEvent {
    data class FilterButtonClicked(val bookmarkType: BookmarkType) : SearchEvent()
    data class RadiusChanged(val value: Float) : SearchEvent()
    object OnSearchClick: SearchEvent()
}