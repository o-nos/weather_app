package com.onos.weather.weatherapp.datebase.forecast_storage

import com.onos.weather.weatherapp.network.response.CurrentWeatherResponse
import io.reactivex.Single

interface ForecastDataStorage {

    fun getAllForecastData(): Single<List<ForecastData>?>
    fun getForecastDataByCity(cityName: String): ForecastData?
    fun getForecastDataByCityId(id: String): ForecastData?
    fun getForecastDataByCityIds(id: List<String>): List<ForecastData>?
    fun putForecastData(weatherData: ForecastData)
    fun saveForecast(weatherResponse: CurrentWeatherResponse): Single<CurrentWeatherResponse>
    fun saveForecast(weatherResponse: List<CurrentWeatherResponse>): Single<List<CurrentWeatherResponse>>
    fun removeForecastData(id: Int)
    fun removeAllForecastData()

}
