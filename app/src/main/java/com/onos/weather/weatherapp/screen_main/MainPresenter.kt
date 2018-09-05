package com.onos.weather.weatherapp.screen_main

import com.onos.weather.weatherapp.base.BasePresenter

class MainPresenter : BasePresenter<MainView>() {

    fun fetchForecast() {

//        view?.isNetworkAvailable()

    }

    private fun saveForecast() {

        // TODO save forecast in db

    }

    private fun showForecast() {
        view?.showForecastList()
    }

    private fun showLastSavedForecast() {

        // TODO get data from db and showForecast()

    }

}
