package com.twoics.geo.ui.screens.bookmarks

import com.twoics.geo.data.models.Bookmark

sealed class BookmarksEvent{
    data class DeleteClick(val bookmark: Bookmark) : BookmarksEvent()
    data class DetailClick(val bookmark: Bookmark) : BookmarksEvent()
    object BackButtonClick : BookmarksEvent()
}