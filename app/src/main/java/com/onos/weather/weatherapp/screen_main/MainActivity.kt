package com.onos.weather.weatherapp.screen_main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {

    private val presenter: MainPresenter by lazy {
        MainPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)

        initUI()

        presenter.fetchForecast()

    }

    private fun initUI() {

        forecast_cities_list.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // TODO init recycler view with adapter

    }

    override fun showForecastList() {

        // TODO show forecast list

    }

    // TODO add pull to refresh

    // TODO add menu with actions: edit locations

    override fun onStop() {
        presenter.detachView()
        super.onStop()
    }

}
