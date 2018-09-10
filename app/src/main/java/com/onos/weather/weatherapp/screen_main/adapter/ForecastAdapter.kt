package com.onos.weather.weatherapp.screen_main.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.extentions.inflate

class ForecastAdapter(var dataset: MutableList<WeatherContent> = mutableListOf(),
                      clickCallback: ((WeatherContent) -> Unit))
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val FORECAST_ITEM_TYPE = 1
        const val ADD_CITY_ITEM_TYPE = 2
    }

    private val forecastAdapterDelegate = ForecastAdapterDelegate(dataset, clickCallback)
    private val addCityAdapterDelegate = AddCityAdapterDelegate(dataset, clickCallback)

    override fun getItemViewType(position: Int): Int {
        return when {
            forecastAdapterDelegate.isForViewType(position) -> {
                forecastAdapterDelegate.getViewType()
            }
            addCityAdapterDelegate.isForViewType(position) -> {
                addCityAdapterDelegate.getViewType()
            }
            else -> super.getItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            forecastAdapterDelegate.getViewType() -> forecastAdapterDelegate.onCreateViewHolder(parent)
            addCityAdapterDelegate.getViewType() -> addCityAdapterDelegate.onCreateViewHolder(parent)
            else -> ForecastAdapter.ViewHolder(parent.inflate(R.layout.item_adapter_delegate_error))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            forecastAdapterDelegate.getViewType() ->
                forecastAdapterDelegate.onBindViewHolder(position, holder as ForecastAdapterDelegate.ForecastViewHolder)
            addCityAdapterDelegate.getViewType() ->
                addCityAdapterDelegate.onBindViewHolder(position, holder as AddCityAdapterDelegate.AddCityViewHolder)
        }
    }

    override fun getItemCount(): Int = dataset.size


    fun updateDataset(weatherListContent: List<WeatherContent>) {
        val oldDataset: MutableList<WeatherContent> = mutableListOf()

        dataset.toCollection(oldDataset)
        dataset.clear()
        dataset.addAll(weatherListContent)

        val diffResult = DiffUtil.calculateDiff(WeatherContentDiffCallback(weatherListContent, oldDataset))
        diffResult.dispatchUpdatesTo(this)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)


}