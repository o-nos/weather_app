package com.onos.weather.weatherapp.base

import android.content.Context
import android.support.annotation.StringRes

interface BaseView {

    fun showLoading()
    fun showLoading(message: String)
    fun showLoading(message: Int)
    fun dismissLoading()

    fun showMessage(message: String)
    fun showMessage(@StringRes messageResId: Int)

    fun getAppContext(): Context
    fun isNetworkAvailable(): Boolean

}
