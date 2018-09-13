package com.onos.weather.weatherapp.datebase.forecast_storage

interface ForecastDataStorage {

    fun getAllForecastData(): List<ForecastData>?
    fun getForecastDataByCity(cityName: String): ForecastData?
    fun getForecastDataByCityId(id: String): ForecastData?
    fun putForecastData(weatherData: ForecastData)
    fun removeAllForecastData()

}
