package com.onos.weather.weatherapp.screen_edit_cities

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.extentions.inflate
import com.onos.weather.weatherapp.screen_main.adapter.ForecastContent
import kotlinx.android.synthetic.main.item_edit_cities.view.*

class EditCitiesAdapter(private val citiesList: ArrayList<ForecastContent>,
                        private val removeItemCallback: (ForecastContent, Int) -> Unit) : RecyclerView.Adapter<EditCitiesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return EditCitiesAdapter.ViewHolder(parent.inflate(R.layout.item_edit_cities))
    }

    override fun getItemCount() = citiesList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = citiesList[position]
        viewHolder.itemView.apply {
            city_name.text = item.cityName
            clear_icon.setOnClickListener {
                removeItemCallback.invoke(item, position)
            }
        }
    }

    fun removeItem(position: Int) {
        citiesList.removeAt(position)
        notifyItemRemoved(position)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v)

}
