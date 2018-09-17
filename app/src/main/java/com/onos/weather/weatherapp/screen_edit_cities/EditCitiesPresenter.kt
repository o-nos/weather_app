package com.onos.weather.weatherapp.screen_edit_cities

import com.onos.weather.weatherapp.R
import com.onos.weather.weatherapp.base.BasePresenter
import com.onos.weather.weatherapp.datebase.forecast_storage.ForecastDataStorage
import com.onos.weather.weatherapp.screen_main.adapter.ForecastContent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EditCitiesPresenter(private val forecastStorage: ForecastDataStorage) : BasePresenter<EditCitiesView>() {

    fun removeCity(content: ForecastContent, position: Int) {
        Single.just(forecastStorage.removeForecastData(content.id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view?.showMessage(R.string.removing_city) }
                .doFinally { view?.dismissLoading() }
                .subscribe(
                        {
                            view?.notifyListChanges(position)
                        },
                        {
                            processError(it)
                        }
                )
    }

}