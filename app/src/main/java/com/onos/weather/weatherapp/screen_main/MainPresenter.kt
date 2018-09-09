package com.onos.weather.weatherapp.screen_main

import android.accounts.NetworkErrorException
import com.onos.weather.weatherapp.base.BasePresenter
import com.onos.weather.weatherapp.network.WeatherApiService
import com.onos.weather.weatherapp.network.response.CurrentWeatherResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenter(val apiService: WeatherApiService) : BasePresenter<MainView>() {

    fun fetchForecast(city: String) {
//        if (view?.isNetworkAvailable() == true) {}

        apiService.getCityCurrentWeather(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showLoading("Loading forecast...") }
                .doFinally { view?.dismissLoading() }
                .subscribe(
                        {
                            showForecast(it)
                        },
                        {
                            if (it is NetworkErrorException) {
                                view?.showMessage("No Internet")
                                showLastSavedForecast()
                            } else {
                                view?.showMessage(it.localizedMessage)
                            }
                        }
                )
    }

    private fun saveForecast() {

        // TODO save forecast in db

    }

    private fun showForecast(it: CurrentWeatherResponse) {
        view?.showForecastList()
    }

    private fun showLastSavedForecast() {

        // TODO get data from db and showForecast()

    }

}
