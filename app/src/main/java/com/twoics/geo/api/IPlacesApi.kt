package com.twoics.geo.api

import com.twoics.geo.data.models.Bookmark

interface IPlacesApi {
    fun getPlaces(query: PlaceRequest): ArrayList<PlacesResponse>
    fun getPlaceDetail(response: PlacesResponse): Bookmark
}