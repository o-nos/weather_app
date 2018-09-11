package com.onos.weather.weatherapp.screen_main.adapter

import com.onos.weather.weatherapp.network.response.CurrentWeatherResponse

open class WeatherContent(val id: Int)

class AddCityContent : WeatherContent(DEFAULT_ID) {
    companion object {
        const val DEFAULT_ID = -1
    }
}

class ForecastContent(id: Int,
                      val cityName: String,
                      val weatherDescriptionMain: String,
                      val mainTemperature: Double,
                      val icon: String,
                      val timestamp: Long) : WeatherContent(id) {

    companion object {
        fun mapResponseToForecastObject(response: CurrentWeatherResponse): ForecastContent {
            val weatherDescriptionMain = response.weather.first().main
            val timestampInLong = response.dt.toLong()
            val icon = response.weather.first().icon

            return ForecastContent(response.id, response.name, weatherDescriptionMain,
                    response.main.temp, icon, timestampInLong)
        }
    }
}
