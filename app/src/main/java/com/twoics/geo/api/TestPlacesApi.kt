package com.twoics.geo.api

import com.twoics.geo.data.models.BookmarkType

class TestPlacesApi : IPlacesApi {
    val PLACES = arrayListOf(
        PlacesResponse(
            long = 93.0,
            lat = 56.0,
            type = BookmarkType.CULTURE,
            xid = "N3578106364"
        ),
        PlacesResponse(
            long = 93.1,
            lat = 56.0,
            type = BookmarkType.SPORT,
            xid = "N5901097711"
        )

    )

    override fun getPlaces(query: PlaceRequest): ArrayList<PlacesResponse> {
        return PLACES
    }
}