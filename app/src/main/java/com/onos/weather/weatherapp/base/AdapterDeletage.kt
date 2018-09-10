package com.onos.weather.weatherapp.base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

interface AdapterDelegate<T : RecyclerView.ViewHolder> {

    val adapterViewType: Int

    fun isForViewType(position: Int): Boolean

    fun getViewType(): Int

    fun onCreateViewHolder(parent: ViewGroup): T

    fun onBindViewHolder(position: Int, viewHolder: T)

}
