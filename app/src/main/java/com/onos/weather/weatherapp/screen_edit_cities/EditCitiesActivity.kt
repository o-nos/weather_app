package com.onos.weather.weatherapp.screen_edit_cities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.base.BaseActivity
import com.onos.weather.weatherapp.base.WeatherApp
import com.onos.weather.weatherapp.screen_edit_cities.EditCitiesActivity.Companion.FORECAST_LIST_EXTRA
import com.onos.weather.weatherapp.screen_main.adapter.ForecastContent
import kotlinx.android.synthetic.main.activity_edit_cities.*

fun Context.newEditCitiesActivity(forecastDataList: ArrayList<ForecastContent>): Intent {
    return Intent(this, EditCitiesActivity::class.java).apply {
        putParcelableArrayListExtra(FORECAST_LIST_EXTRA, forecastDataList)
    }
}

class EditCitiesActivity : BaseActivity(), EditCitiesView {

    private val presenter: EditCitiesPresenter by lazy {
        EditCitiesPresenter(WeatherApp.forecastStorage)
    }

    private val removeItemCallback: ((ForecastContent, Int) -> Unit) = { content, position ->
        removeCityFromForecasts(content, position)
    }

    private var citiesList = arrayListOf<ForecastContent>()
    private var forecastAdapter: EditCitiesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_cities)
        title = getString(R.string.edit_cities)
        presenter.attachView(this)

        val forecastList = intent.extras?.getParcelableArrayList<ForecastContent>(FORECAST_LIST_EXTRA)
        forecastList?.let {
            citiesList.addAll(forecastList)
            initUI()
        } ?: finish()
    }

    private fun initUI() {
        cities_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        forecastAdapter = EditCitiesAdapter(citiesList, removeItemCallback)
        cities_list.adapter = forecastAdapter
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    private fun removeCityFromForecasts(content: ForecastContent, position: Int) {
        presenter.removeCity(content, position)
        // sets RESULT_OK when user deletes at least one city
        setResult(Activity.RESULT_OK)
    }

    override fun notifyListChanges(position: Int) {
        forecastAdapter?.removeItem(position)
    }

    companion object {

        const val FORECAST_LIST_EXTRA = "forecastDataList"

    }

}