package com.twoics.geo.api

import com.twoics.geo.data.models.BookmarkType

data class PlacesResponse(
    val lat: Double,
    val long: Double,
    val xid: String,
    val type: BookmarkType
)