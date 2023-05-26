package com.twoics.geo.ui.screens.details

import com.twoics.geo.data.models.Bookmark

sealed class DetailsEvent {
    data class LikeButtonClick(val bookmark: Bookmark) : DetailsEvent()
    data class ViewAtMapButtonClick(val bookmark: Bookmark) : DetailsEvent()
    object BackButtonClick : DetailsEvent()
}