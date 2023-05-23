package com.twoics.geo.api

import com.twoics.geo.data.models.Bookmark

interface IWeatherApi {
    fun getPlaces(query: WeatherQuery): ArrayList<Bookmark>
}