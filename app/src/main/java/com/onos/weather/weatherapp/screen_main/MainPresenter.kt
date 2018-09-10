package com.onos.weather.weatherapp.screen_main

import android.accounts.NetworkErrorException
import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.base.BasePresenter
import com.onos.weather.weatherapp.network.WeatherApiService
import com.onos.weather.weatherapp.network.response.CurrentWeatherResponse
import com.onos.weather.weatherapp.screen_main.adapter.AddCityContent
import com.onos.weather.weatherapp.screen_main.adapter.ForecastContent
import com.onos.weather.weatherapp.screen_main.adapter.ForecastContent.Companion.mapResponseToForecastObject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(private val apiService: WeatherApiService) : BasePresenter<MainView>() {

    fun fetchForecastByCity(city: String) {
        val disposable = apiService.getCityCurrentWeather(city)
                .subscribeOn(Schedulers.io())
//                .flatMap { saveForecast(it) }
                .map { mapResponseToForecastObject(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showLoading(R.string.loading_forecast) }
                .doFinally { view?.dismissLoading() }
                .subscribe(
                        {
                            showForecastForCity(it)
                        },
                        {
                            showForecastErrorMessage(it)
                            showLastSavedForecast()
                        }
                )

        subscriptionList?.add(disposable)
    }

    private fun showForecastErrorMessage(throwable: Throwable) {
        when (throwable) {
            is NetworkErrorException -> view?.showMessage(R.string.no_internet)
            else -> view?.showMessage(throwable.localizedMessage)
        }
    }

    private fun saveForecast(it: CurrentWeatherResponse): Single<CurrentWeatherResponse> {
        return Single.just(it)
                .map {
                    // TODO save forecast in db
                    it
                }
    }

    private fun showForecastForCity(forecastContent: ForecastContent) {
        val addCityContent = AddCityContent()
        val forecastContentList = mutableListOf(forecastContent, addCityContent)

        view?.showForecastList(forecastContentList)
    }

    private fun showLastSavedForecast() {

        // TODO get data from db and showForecastForCity()

    }

}
