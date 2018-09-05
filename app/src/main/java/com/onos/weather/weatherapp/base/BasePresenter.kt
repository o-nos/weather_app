package com.onos.weather.weatherapp.base

import io.reactivex.disposables.CompositeDisposable


abstract class BasePresenter<T : BaseView> {

    var view: T? = null
    protected var subscriptionList: CompositeDisposable? = CompositeDisposable()

    fun attachView(v: T) {
        view = v
        onViewAttached()
    }

    fun detachView() {
        view = null
        onViewDetached()
        subscriptionList?.dispose()
        subscriptionList = null
    }

    protected open fun onViewAttached() {
    }

    protected open fun onViewDetached() {
        clearAllSubscriptions()
    }

    fun clearAllSubscriptions() {
        subscriptionList?.let {
            if (!it.isDisposed) {
                it.clear()
                subscriptionList = CompositeDisposable()
            }
        }
    }

}