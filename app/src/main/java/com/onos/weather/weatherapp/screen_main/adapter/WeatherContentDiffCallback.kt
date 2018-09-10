package com.onos.weather.weatherapp.screen_main.adapter

import android.support.v7.util.DiffUtil

class WeatherContentDiffCallback(private val weatherListContent: List<WeatherContent>,
                                 private val oldDataset: MutableList<WeatherContent>) : DiffUtil.Callback() {

    override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
        return oldDataset[p0].id == weatherListContent[p1].id
    }

    override fun getOldListSize() = oldDataset.size

    override fun getNewListSize() = weatherListContent.size

    override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
        return oldDataset[p0] == weatherListContent[p1]
    }

}
