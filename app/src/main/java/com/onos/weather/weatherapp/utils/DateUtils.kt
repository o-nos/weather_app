package com.onos.weather.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val MILLS_IN_SECOND = 1000L
    private const val FORECAST_DATE_FORMAT = "MM.dd.yy HH:mm"

    fun parseForecastDate(forecastDate: Long): String {
        val historyDateFormat = SimpleDateFormat(FORECAST_DATE_FORMAT, Locale.ENGLISH)
        return historyDateFormat.format(getDateFromTimeInSec(forecastDate))
    }

    private fun getDateFromTimeInSec(timeInSeconds: Long): Date {
        return Date(timeInSeconds * MILLS_IN_SECOND)
    }

}