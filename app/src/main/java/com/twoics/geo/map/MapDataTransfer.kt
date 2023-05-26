package com.twoics.geo.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MapDataTransfer : IMapDataTransfer, ViewModel() {
    private val mapChannel: Channel<MapEvent> = Channel()
    override val mapEventsFlow: Flow<MapEvent> = mapChannel.receiveAsFlow()

    override fun sendEvent(event: MapEvent) {
        viewModelScope.launch {
            mapChannel.send(event)
        }
    }
}