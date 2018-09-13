package com.onos.weather.weatherapp.datebase

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.onos.weather.weatherapp.datebase.AppDataBase.Companion.DB_VERSION
import com.onos.weather.weatherapp.datebase.forecast_storage.ForecastData
import com.onos.weather.weatherapp.datebase.forecast_storage.ForecastDataDao

@Database(entities = [ForecastData::class],
        version = DB_VERSION)
abstract class AppDataBase : RoomDatabase() {

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "weather_app_db"
    }

    abstract fun getForecastDataDao(): ForecastDataDao

}