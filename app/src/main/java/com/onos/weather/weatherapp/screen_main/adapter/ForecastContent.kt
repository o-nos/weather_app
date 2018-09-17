package com.onos.weather.weatherapp.screen_main.adapter

import android.os.Parcelable
import com.onos.weather.weatherapp.datebase.forecast_storage.ForecastData
import com.onos.weather.weatherapp.network.response.CurrentWeatherResponse
import kotlinx.android.parcel.Parcelize

abstract class WeatherContent(open val id: Int) : Parcelable

@Parcelize
class AddCityContent : WeatherContent(DEFAULT_ID), Parcelable {
    companion object {
        const val DEFAULT_ID = -1
    }
}


@Parcelize
class ForecastContent(override val id: Int,
                      val cityName: String,
                      val weatherDescriptionMain: String,
                      val mainTemperature: Double,
                      val icon: String,
                      val timestamp: Long) : WeatherContent(id), Parcelable {

    companion object {

        fun mapResponseToForecastContent(response: CurrentWeatherResponse): ForecastContent {
            val weatherDescriptionMain = response.weather.first().main
            val timestampInLong = response.dt.toLong()
            val icon = response.weather.first().icon

            return ForecastContent(response.id, response.name, weatherDescriptionMain,
                    response.main.temp, icon, timestampInLong)
        }

        fun mapForecastDataToForecastContent(forecastData: ForecastData): ForecastContent {
            return with(forecastData) {
                ForecastContent(id, cityName, weatherDescriptionMain, mainTemperature, icon, timestamp.toLong())
            }
        }

    }
}