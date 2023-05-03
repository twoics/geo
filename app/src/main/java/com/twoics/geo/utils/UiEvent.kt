package com.twoics.geo.utils

sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackBar(val message: String) : UiEvent()
}