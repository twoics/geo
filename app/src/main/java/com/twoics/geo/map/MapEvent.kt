package com.twoics.geo.map

import com.twoics.geo.data.models.Bookmark

sealed class MapEvent {
    data class PeakPlace(val bookmark: Bookmark) : MapEvent()
}