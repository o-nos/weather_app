package com.onos.weather.weatherapp.network

import com.onos.weather.weatherapp.network.response.CurrentWeatherListResponse
import com.onos.weather.weatherapp.network.response.CurrentWeatherResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {

    companion object {

        private const val WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/"
        private const val WEATHER_ICON_URL = "http://openweathermap.org/img/w/"
        private const val APP_API_ID = "885d506d226d73901d96b180c50af51c"
        private const val API_UNITS = "metric" // TODO add menu item that changes units (ENUM vals)

        fun create(): WeatherApiService {

            val retrofit = Retrofit.Builder()
                    .client(provideHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(WEATHER_BASE_URL)
                    .build()

            return retrofit.create(WeatherApiService::class.java)
        }

        private fun provideHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor(provideLoggingInterceptor())
                    .build()
        }

        private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        fun getIconUrl(iconTitle: String): String {
            return "$WEATHER_ICON_URL$iconTitle.png"
        }

    }

    @GET("weather")
    fun getCityCurrentWeather(@Query("q") cityName: String,
                              @Query("APPID") appId: String = APP_API_ID,
                              @Query("units") units: String = API_UNITS): Single<CurrentWeatherResponse>

    @GET("group")
    fun getGroupWeatherList(@Query("id") array: List<Int>,
                            @Query("APPID") appId: String = APP_API_ID,
                            @Query("units") units: String = API_UNITS): Single<CurrentWeatherListResponse>

}
