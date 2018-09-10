package com.onos.weather.weatherapp.extentions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ViewGroup.inflate(inflater: LayoutInflater, @LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return inflater.inflate(layoutRes, this, attachToRoot)
}