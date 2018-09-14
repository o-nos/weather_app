package com.onos.weather.weatherapp.network.response

data class CurrentWeatherListResponse(
        val cnt: Int,
        val list: List<CurrentWeatherResponse>
)