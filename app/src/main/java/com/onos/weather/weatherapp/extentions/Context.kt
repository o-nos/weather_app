package com.onos.weather.weatherapp.extentions

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager.activeNetworkInfo?.let {
        return it.isAvailable
    }
    return false
}
