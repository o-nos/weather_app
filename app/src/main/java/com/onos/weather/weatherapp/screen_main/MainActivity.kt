package com.onos.weather.weatherapp.screen_main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.base.BaseActivity
import com.onos.weather.weatherapp.base.WeatherApp
import com.onos.weather.weatherapp.screen_add_city.newAddCityActivity
import com.onos.weather.weatherapp.screen_main.adapter.AddCityContent
import com.onos.weather.weatherapp.screen_main.adapter.ForecastAdapter
import com.onos.weather.weatherapp.screen_main.adapter.ForecastContent
import com.onos.weather.weatherapp.screen_main.adapter.WeatherContent
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {

    private val TAG = MainActivity::class.java.simpleName

    companion object {
        private const val ADD_CITY_REQUEST_CODE = 1000
    }

    private val presenter: MainPresenter by lazy {
        MainPresenter(WeatherApp.apiService, WeatherApp.forecastStorage)
    }

    private val forecastItemCallback: ((WeatherContent) -> Unit) = {
        when (it) {
            is ForecastContent -> Log.d(TAG, "${it.cityName} is clicked")
            is AddCityContent -> startActivityForResult(newAddCityActivity(), ADD_CITY_REQUEST_CODE)
        }
    }

    private val forecastAdapter = ForecastAdapter(mutableListOf(), forecastItemCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)

        initUI()

        presenter.fetchAndSaveAllForecasts()

    }

    private fun initUI() {
        forecast_cities_list.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        forecast_cities_list.adapter = forecastAdapter

        swipe_to_refresh.setOnRefreshListener {
            //            presenter.fetchForecastByCity("Poltava") // TODO remove hardcoded value
            presenter.fetchAndSaveAllForecasts()
        }
    }

    override fun showForecastList(forecastContentList: List<WeatherContent>) {
        forecastAdapter.updateDataset(forecastContentList)
        if (swipe_to_refresh.isRefreshing) {
            swipe_to_refresh.isRefreshing = false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_CITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            presenter.loadForecastListFromStorage()
        }
    }

    // TODO add menu with actions: Edit locations screen, Temperature units

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

}
