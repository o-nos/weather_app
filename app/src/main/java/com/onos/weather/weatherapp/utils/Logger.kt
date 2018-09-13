package com.onos.weather.weatherapp.utils

import android.util.Log
import com.onos.weather.weatherapp.BuildConfig

fun logd(tag: String, message: String) {
    if (isLogEnabled()) {
        Log.d(tag, message)
    }
}

fun loge(tag: String, message: String, throwable: Throwable? = null) {
    if (isLogEnabled()) {
        if (throwable != null) {
            Log.e(tag, message, throwable)
        } else {
            Log.e(tag, message)
        }
    }
}

private fun isLogEnabled() = BuildConfig.DEBUG