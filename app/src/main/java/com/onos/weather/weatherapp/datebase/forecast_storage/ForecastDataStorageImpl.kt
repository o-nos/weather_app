package com.onos.weather.weatherapp.datebase.forecast_storage

class ForecastDataStorageImpl(private val forecastDataDao: ForecastDataDao) : ForecastDataStorage {

    override fun getAllForecastData(): List<ForecastData>? {
        return forecastDataDao.getAllForecastData()
    }

    override fun getForecastDataByCity(cityName: String): ForecastData? {
        return forecastDataDao.getForecastDataByCity(cityName)
    }

    override fun getForecastDataByCityId(id: String): ForecastData? {
        return forecastDataDao.getForecastDataByCityId(id)
    }

    override fun putForecastData(weatherData: ForecastData) {
        forecastDataDao.insertForecastData(weatherData)
    }

    override fun removeAllForecastData() {
        forecastDataDao.removeAllForecastData()
    }

}