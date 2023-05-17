package com.twoics.geo.map

import kotlinx.coroutines.flow.Flow

interface IMapDataTransfer {
    val mapEventsFlow: Flow<MapEvent>
    fun sendEvent(event: MapEvent)
}