package com.twoics.geo.api

interface IPlacesApi {
    fun getPlaces(query: PlaceRequest): ArrayList<PlacesResponse>
}