package com.onos.weather.weatherapp.base

import io.reactivex.disposables.CompositeDisposable


abstract class BasePresenter<T : BaseView> {

    var view: T? = null
    protected var disposableList: CompositeDisposable? = CompositeDisposable()

    fun attachView(v: T) {
        view = v
        onViewAttached()
    }

    fun detachView() {
        view = null
        onViewDetached()
        disposableList?.dispose()
        disposableList = null
    }

    protected open fun onViewAttached() {
    }

    protected open fun onViewDetached() {
        clearAllSubscriptions()
    }

    fun processError(throwable: Throwable) {
        view?.showMessage(throwable.localizedMessage)
    }

    fun clearAllSubscriptions() {
        disposableList?.let {
            if (!it.isDisposed) {
                it.clear()
                disposableList = CompositeDisposable()
            }
        }
    }

}