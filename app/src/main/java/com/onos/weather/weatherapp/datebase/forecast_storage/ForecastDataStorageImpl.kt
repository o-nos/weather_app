package com.onos.weather.weatherapp.datebase.forecast_storage

import com.google.gson.Gson
import com.onos.weather.weatherapp.datebase.forecast_storage.ForecastData.Companion.mapCurrentWeatherResponseToForecastData
import com.onos.weather.weatherapp.network.response.CurrentWeatherResponse
import com.onos.weather.weatherapp.utils.logd
import io.reactivex.Observable
import io.reactivex.Single

class ForecastDataStorageImpl(private val forecastDataDao: ForecastDataDao,
                              private val gsonParser: Gson) : ForecastDataStorage {

    private val TAG = ForecastDataStorageImpl::class.java.simpleName

    override fun getAllForecastData(): Single<List<ForecastData>?> {
        return Single.just(forecastDataDao.getAllForecastData())
    }

    override fun getForecastDataByCity(cityName: String): ForecastData? {
        return forecastDataDao.getForecastDataByCity(cityName)
    }

    override fun getForecastDataByCityId(id: String): ForecastData? {
        return forecastDataDao.getForecastDataByCityId(id)
    }

    override fun getForecastDataByCityIds(id: List<String>): List<ForecastData>? {
        // TODO To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun putForecastData(weatherData: ForecastData) {
        forecastDataDao.insertForecastData(weatherData)
    }

    override fun removeAllForecastData() {
        forecastDataDao.removeAllForecastData()
    }

    override fun removeForecastData(id: Int) {
        forecastDataDao.removeForecastData(id)
    }

    /**
     * Saves forecast for given city.
     * @param weatherResponse forecast response to be saved in DB
     * @return Single that emits the weatherResponse, which was given as a param
     */
    override fun saveForecast(weatherResponse: CurrentWeatherResponse): Single<CurrentWeatherResponse> {
        return Single.just(weatherResponse)
                .map {
                    val forecastData = mapCurrentWeatherResponseToForecastData(weatherResponse, gsonParser)
                    putForecastData(forecastData)
                    logd(TAG, "forecastData is saved, id = ${forecastData.id}")
                    it
                }
    }


    /**
     * Iterates forecast list and saves forecast for each city.
     * @param weatherResponse response to be saved in DB
     * @return Single that emits the weatherResponse list, which was given as a param
     */
    override fun saveForecast(weatherResponse: List<CurrentWeatherResponse>): Single<List<CurrentWeatherResponse>> {
        return Observable.fromIterable(weatherResponse)
                .flatMapSingle {
                    saveForecast(it)
                }
                .doOnComplete {
                    logd(TAG, "updateDocumentsConsentInfo, iteration is complete.")
                }
                .last(weatherResponse.first())
                .flatMap {
                    Single.just(weatherResponse)
                }
    }

}