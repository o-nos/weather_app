package com.onos.weather.weatherapp.base

import android.app.Application
import com.onos.weather.weatherapp.network.WeatherApiService

class WeatherApp : Application() {

    companion object {

        lateinit var apiService: WeatherApiService

    }

    override fun onCreate() {
        super.onCreate()

        apiService = WeatherApiService.create()

    }


}