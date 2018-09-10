package com.onos.weather.weatherapp.screen_main.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.base.AdapterDelegate
import com.onos.weather.weatherapp.extentions.inflate

class AddCityAdapterDelegate(private val dataset: MutableList<WeatherContent>,
                             private val clickCallback: (WeatherContent) -> Unit)
    : AdapterDelegate<AddCityAdapterDelegate.AddCityViewHolder> {

    override val adapterViewType: Int
        get() = ForecastAdapter.ADD_CITY_ITEM_TYPE

    override fun isForViewType(position: Int): Boolean {
        if (dataset.size - 1 >= position) {
            return dataset[position] is AddCityContent
        }
        return false
    }

    override fun getViewType(): Int {
        return adapterViewType
    }

    override fun onCreateViewHolder(parent: ViewGroup): AddCityViewHolder {
        return AddCityViewHolder(parent.inflate(R.layout.item_add_city_view))
    }

    override fun onBindViewHolder(position: Int, viewHolder: AddCityViewHolder) {
        val addCityItem = dataset[position] as AddCityContent
        viewHolder.itemView.apply {
            setOnClickListener {
                clickCallback.invoke(addCityItem)
            }
        }
    }


    class AddCityViewHolder(v: View) : RecyclerView.ViewHolder(v)

}
