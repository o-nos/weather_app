package com.onos.weather.weatherapp.datebase.forecast_storage

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface ForecastDataDao {

    @Query("SELECT * from ${ForecastData.FORECAST_TABLE_NAME}")
    fun getAllForecastData(): List<ForecastData>

    @Insert(onConflict = REPLACE)
    fun insertForecastData(weatherData: ForecastData)

    @Query("SELECT * from ${ForecastData.FORECAST_TABLE_NAME} WHERE ${ForecastData.FORECAST_COLUMN_CITY_NAME} = :cityName")
    fun getForecastDataByCity(cityName: String): ForecastData?

    @Query("SELECT * from ${ForecastData.FORECAST_TABLE_NAME} WHERE ${ForecastData.FORECAST_COLUMN_ID} = :id")
    fun getForecastDataByCityId(id: String): ForecastData?

    @Query("DELETE from ${ForecastData.FORECAST_TABLE_NAME}")
    fun removeAllForecastData()

    @Query("DELETE from ${ForecastData.FORECAST_TABLE_NAME} WHERE ${ForecastData.FORECAST_COLUMN_ID} = :id")
    fun removeForecastData(id: Int)

}