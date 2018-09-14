package com.onos.weather.weatherapp.screen_main

import android.accounts.NetworkErrorException
import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.base.BasePresenter
import com.onos.weather.weatherapp.datebase.forecast_storage.ForecastDataStorage
import com.onos.weather.weatherapp.network.WeatherApiService
import com.onos.weather.weatherapp.screen_main.adapter.ForecastContent.Companion.mapForecastDataToForecastContent
import com.onos.weather.weatherapp.screen_main.adapter.ForecastContent.Companion.mapResponseToForecastContent
import com.onos.weather.weatherapp.screen_main.adapter.WeatherContent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.UnknownHostException

class MainPresenter(private val apiService: WeatherApiService,
                    private val forecastStorage: ForecastDataStorage) : BasePresenter<MainView>() {

    private val TAG = MainPresenter::class.java.simpleName

    fun fetchAndSaveAllForecasts() {
        val disposable = forecastStorage.getAllForecastData()
                .subscribeOn(Schedulers.io())
                .map { list -> list.map { it.id } }
                .flatMap { apiService.getGroupWeatherList(it) }
                .map { it.list }
                .flatMap { forecastStorage.saveForecast(it) }
                .map { list ->
                    list.map { mapResponseToForecastContent(it) }

                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            showForecastList(it)
                        },
                        {
                            showForecastErrorMessage(it)
                            loadForecastListFromStorage()
                        }
                )

        disposableList?.add(disposable)

    }

    fun loadForecastListFromStorage() {
        forecastStorage.getAllForecastData()
                .subscribeOn(Schedulers.io())
                .map { forecastList ->
                    forecastList.map {
                        mapForecastDataToForecastContent(it)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showLoading(R.string.loading_forecast) }
                .doFinally { view?.dismissLoading() }
                .subscribe(
                        {
                            showForecastList(it)
                        },
                        {
                            processError(it)
                        }
                )

    }

    private fun showForecastList(forecastContentList: List<WeatherContent>) {
//        val addCityContent = AddCityContent()
//        val listToShow = forecastContentList.toMutableList()
//        listToShow.add(addCityContent) // TODO fix bug with showing only one item instead of whole list
        view?.showForecastList(forecastContentList)
    }

    private fun showForecastErrorMessage(throwable: Throwable) {
        when (throwable) {
            is NetworkErrorException -> view?.showMessage(R.string.no_internet)
            is UnknownHostException -> view?.showMessage(R.string.no_internet)
            else -> view?.showMessage(throwable.localizedMessage)
        }
    }

}
