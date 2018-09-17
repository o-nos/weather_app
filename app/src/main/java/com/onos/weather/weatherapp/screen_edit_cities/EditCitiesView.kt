package com.onos.weather.weatherapp.screen_edit_cities

import com.onos.weather.weatherapp.base.BaseView

interface EditCitiesView : BaseView {

    fun notifyListChanges(position: Int)

}