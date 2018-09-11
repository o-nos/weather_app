package com.onos.weather.weatherapp.screen_main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.base.AdapterDelegate
import com.onos.weather.weatherapp.extentions.inflate
import com.onos.weather.weatherapp.utils.DateUtils
import kotlinx.android.synthetic.main.item_forecast_view.view.*

class ForecastAdapterDelegate(private val dataset: MutableList<WeatherContent>,
                              private val clickCallback: ((ForecastContent) -> Unit))
    : AdapterDelegate<ForecastAdapterDelegate.ForecastViewHolder> {

    override val adapterViewType: Int
        get() = ForecastAdapter.FORECAST_ITEM_TYPE

    override fun isForViewType(position: Int): Boolean {
        if (dataset.size - 1 >= position) {
            return dataset[position] is ForecastContent
        }
        return false
    }

    override fun getViewType(): Int {
        return adapterViewType
    }

    override fun onCreateViewHolder(parent: ViewGroup): ForecastViewHolder {
        return ForecastViewHolder(parent.inflate(R.layout.item_forecast_view))

    }

    override fun onBindViewHolder(position: Int, viewHolder: ForecastViewHolder) {
        val forecastItem = dataset[position] as ForecastContent
        viewHolder.itemView.apply {
            setOnClickListener {
                clickCallback.invoke(forecastItem)
            }
            city_name.text = forecastItem.cityName
            temperature.text = String.format(resources.getString(R.string.forecast_temperature_mask),
                    forecastItem.mainTemperature)
            description.text = forecastItem.weatherDescriptionMain
            forecast_timestamp.text = DateUtils.parseForecastDate(forecastItem.timestamp)
        }
    }


    class ForecastViewHolder(v: View) : RecyclerView.ViewHolder(v)


}