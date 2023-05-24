package com.twoics.geo.map

import com.twoics.geo.api.PlacesResponse

sealed class MapEvent {
    data class PeakPlace(val place: PlacesResponse) : MapEvent()
}