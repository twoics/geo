package com.twoics.geo.api

import com.twoics.geo.data.models.BookmarkType
import com.twoics.geo.settings.Languages


data class PlaceRequest(
    val lat: Double,
    val long: Double,
    val radius: Double,
    val minRate: Int = 1,
    val category: ArrayList<BookmarkType>,
    val language: Languages
)
