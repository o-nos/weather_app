package com.onos.weather.weatherapp.base

import android.app.Application
import android.arch.persistence.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.onos.weather.weatherapp.datebase.AppDataBase
import com.onos.weather.weatherapp.datebase.forecast_storage.ForecastDataStorage
import com.onos.weather.weatherapp.datebase.forecast_storage.ForecastDataStorageImpl
import com.onos.weather.weatherapp.network.WeatherApiService

class WeatherApp : Application() {

    companion object {

        lateinit var apiService: WeatherApiService
        lateinit var appDatabase: AppDataBase
        lateinit var forecastStorage: ForecastDataStorage
        lateinit var gsonParser: Gson

    }

    override fun onCreate() {
        super.onCreate()

        apiService = WeatherApiService.create()
        appDatabase = Room.databaseBuilder(this, AppDataBase::class.java, AppDataBase.DB_NAME)
                .allowMainThreadQueries()
                .build()
        forecastStorage = ForecastDataStorageImpl(appDatabase.getForecastDataDao())

        gsonParser = GsonBuilder().create()

    }


}