package com.onos.weather.weatherapp.screen_main

import android.accounts.NetworkErrorException
import com.google.gson.Gson
import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.base.BasePresenter
import com.onos.weather.weatherapp.datebase.forecast_storage.ForecastData
import com.onos.weather.weatherapp.datebase.forecast_storage.ForecastDataStorage
import com.onos.weather.weatherapp.network.WeatherApiService
import com.onos.weather.weatherapp.network.response.CurrentWeatherResponse
import com.onos.weather.weatherapp.screen_main.adapter.AddCityContent
import com.onos.weather.weatherapp.screen_main.adapter.ForecastContent
import com.onos.weather.weatherapp.screen_main.adapter.ForecastContent.Companion.mapForecastDataToForecastContent
import com.onos.weather.weatherapp.screen_main.adapter.ForecastContent.Companion.mapResponseToForecastContent
import com.onos.weather.weatherapp.utils.logd
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class MainPresenter(private val apiService: WeatherApiService,
                    private val forecastStorage: ForecastDataStorage,
                    private val gsonParser: Gson) : BasePresenter<MainView>() {

    private val TAG = MainPresenter::class.java.simpleName

    fun fetchForecastByCity(city: String) {
        val disposable = apiService.getCityCurrentWeather(city)
                .subscribeOn(Schedulers.io())
                .flatMap { saveForecast(it) }
                .map { mapResponseToForecastContent(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showLoading(R.string.loading_forecast) }
                .doFinally { view?.dismissLoading() }
                .subscribe(
                        {
                            logd(TAG, "forecast for ${it.cityName} is loaded from API")
                            showForecastForCity(it)
                        },
                        {
                            showForecastErrorMessage(it)
                            showLastSavedForecast(city)
                        }
                )

        subscriptionList?.add(disposable)
    }

    private fun showForecastErrorMessage(throwable: Throwable) {
        when (throwable) {
            is NetworkErrorException -> view?.showMessage(R.string.no_internet)
            is UnknownHostException -> view?.showMessage(R.string.no_internet)
            else -> view?.showMessage(throwable.localizedMessage)
        }
    }

    private fun saveForecast(weatherResponse: CurrentWeatherResponse): Single<CurrentWeatherResponse> {
        return Single.just(weatherResponse)
                .map {
                    val jsonForecastData = gsonParser.toJson(weatherResponse)
                    val forecastData = ForecastData(weatherResponse.id, weatherResponse.name,
                            weatherResponse.weather.first().main, weatherResponse.main.temp,
                            weatherResponse.weather.first().icon, weatherResponse.dt,
                            jsonForecastData)
                    forecastStorage.putForecastData(forecastData)
                    logd(TAG, "weatherData is saved, id = ${forecastData.id}")
                    it
                }
    }

    private fun showForecastForCity(forecastContent: ForecastContent) {
        val addCityContent = AddCityContent()
        val forecastContentList = mutableListOf(forecastContent, addCityContent)

        view?.showForecastList(forecastContentList)
    }

    private fun showLastSavedForecast(city: String) {
        val disposable = Single.just(forecastStorage.getForecastDataByCity(city))
                .subscribeOn(Schedulers.io())
                .map { mapForecastDataToForecastContent(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showLoading(R.string.loading_forecast) }
                .doFinally { view?.dismissLoading() }
                .subscribe(
                        {
                            logd(TAG, "forecast for ${it.cityName} is loaded from DB")
                            showForecastForCity(it)
                        },
                        {
                            showForecastErrorMessage(it)
                        }
                )

        subscriptionList?.add(disposable)
    }

}
