package com.onos.weather.weatherapp.datebase.forecast_storage

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.Gson
import com.onos.weather.weatherapp.datebase.forecast_storage.ForecastData.Companion.FORECAST_TABLE_NAME
import com.onos.weather.weatherapp.network.response.CurrentWeatherResponse

@Entity(tableName = FORECAST_TABLE_NAME)
data class ForecastData(@PrimaryKey @ColumnInfo(name = FORECAST_COLUMN_ID) var id: Int,
                        @ColumnInfo(name = FORECAST_COLUMN_CITY_NAME) val cityName: String,
                        @ColumnInfo(name = FORECAST_COLUMN_DESCR) val weatherDescriptionMain: String,
                        @ColumnInfo(name = FORECAST_COLUMN_TEMPERATURE_MAIN) val mainTemperature: Double,
                        @ColumnInfo(name = FORECAST_COLUMN_ICON) val icon: String,
                        @ColumnInfo(name = FORECAST_COLUMN_TIMESTAMP) val timestamp: Int,
                        @ColumnInfo(name = FORECAST_COLUMN_JSON) val jsonData: String) {

    companion object {

        const val FORECAST_TABLE_NAME = "ForecastData"
        const val FORECAST_COLUMN_ID = "id"
        const val FORECAST_COLUMN_CITY_NAME = "city_name"
        const val FORECAST_COLUMN_DESCR = "description"
        const val FORECAST_COLUMN_TEMPERATURE_MAIN = "temperature_main"
        const val FORECAST_COLUMN_ICON = "icon"
        const val FORECAST_COLUMN_TIMESTAMP = "timestamp"
        const val FORECAST_COLUMN_JSON = "json"

        fun mapCurrentWeatherResponseToForecastData(response: CurrentWeatherResponse, gsonParser: Gson): ForecastData {
            val jsonForecastData = gsonParser.toJson(response)
            return ForecastData(response.id, response.name,
                    response.weather.first().main, response.main.temp,
                    response.weather.first().icon, response.dt,
                    jsonForecastData)
        }

    }


}