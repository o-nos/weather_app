package com.onos.weather.weatherapp.screen_add_city

import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.base.BasePresenter
import com.onos.weather.weatherapp.datebase.forecast_storage.ForecastDataStorage
import com.onos.weather.weatherapp.network.WeatherApiService
import com.onos.weather.weatherapp.utils.logd
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddCityPresenter(private val apiService: WeatherApiService,
                       private val forecastStorage: ForecastDataStorage) : BasePresenter<AddCityView>() {

    private val TAG = AddCityPresenter::class.java.simpleName

    fun getForecastForCity(cityName: String) {
        val disposable = apiService.getCityCurrentWeather(cityName)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    forecastStorage.saveForecast(it)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showLoading(R.string.loading_forecast) }
                .doFinally { view?.dismissLoading() }
                .subscribe(
                        {
                            logd(TAG, "forecast for ${cityName} is loaded from API")
                            view?.goBackToMainScreen(true)
                        },
                        {
                            processError(it)
                        }
                )

        this.disposableList?.add(disposable)
    }

}
