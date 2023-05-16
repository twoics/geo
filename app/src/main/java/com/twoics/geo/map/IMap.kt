package com.twoics.geo.map

import androidx.compose.runtime.Composable
import com.twoics.geo.data.models.Bookmark
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

interface IMap {
    val centerMapLocation: GeoPoint
    val areaRadius: Double

    fun drawFoundedPlaces(places: ArrayList<Bookmark>)
    fun clearPlaces()
    fun drawSearchCircle(radius: Double)
    fun focusedDrawBookmark(place: Bookmark)

    @Composable
    fun redrawMap(): MapView
}