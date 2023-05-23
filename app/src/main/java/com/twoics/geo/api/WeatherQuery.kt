package com.twoics.geo.api

import com.twoics.geo.data.models.BookmarkType
import com.twoics.geo.utils.Languages


data class WeatherQuery(
    val lat: Double,
    val long: Double,
    val radius: Double,
    val category: ArrayList<BookmarkType>,
    val language: Languages
)
