package com.onos.weather.weatherapp.screen_main

import com.onos.weather.weatherapp.base.BaseView
import com.onos.weather.weatherapp.screen_main.adapter.WeatherContent

interface MainView : BaseView {

    fun showForecastList(forecastContentList: MutableList<WeatherContent>)

}