package com.onos.weather.weatherapp.screen_main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.base.BaseActivity
import com.onos.weather.weatherapp.base.WeatherApp
import com.onos.weather.weatherapp.screen_main.adapter.AddCityContent
import com.onos.weather.weatherapp.screen_main.adapter.ForecastAdapter
import com.onos.weather.weatherapp.screen_main.adapter.ForecastContent
import com.onos.weather.weatherapp.screen_main.adapter.WeatherContent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {

    private val TAG = MainActivity::class.java.simpleName

    private val presenter: MainPresenter by lazy {
        MainPresenter(WeatherApp.apiService, WeatherApp.forecastStorage, WeatherApp.gsonParser)
    }

    private val forecastItemCallback: ((WeatherContent) -> Unit) = {
        when (it) {
            is ForecastContent -> Log.d(TAG, "${it.cityName} is clicked")
            is AddCityContent -> Log.d(TAG, "Add City is clicked")
        }
    }

    private val forecastAdapter = ForecastAdapter(mutableListOf(), forecastItemCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)

        initUI()

        presenter.fetchForecastByCity("Poltava") // TODO remove hardcoded value

    }

    private fun initUI() {
        forecast_cities_list.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        forecast_cities_list.adapter = forecastAdapter

        swipe_to_refresh.setOnRefreshListener {
            presenter.fetchForecastByCity("Poltava") // TODO remove hardcoded value
        }
    }

    override fun showForecastList(forecastContentList: MutableList<WeatherContent>) {
        forecastAdapter.updateDataset(forecastContentList)
        if (swipe_to_refresh.isRefreshing) {
            swipe_to_refresh.isRefreshing = false
        }
    }

    // TODO add menu with actions: Edit locations screen, Temperature units

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

}
