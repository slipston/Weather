package hu.ait.weather.network

import android.telecom.Call
import hu.ait.weather.data.WeatherResult
import retrofit2.http.GET
import retrofit2.http.Query

// host: https://api.openweathermap.org
// path: /data/2.5/weather?
// query city, country: q=Budapest,hu
// units: &units=metric
// application id: &appid=9986ba90fe48fb5d20b9baf4285895d0
// https://api.openweathermap.org/data/2.5/weather?q=Budapest,hu&units=metric&appid=9986ba90fe48fb5d20b9baf4285895d0

interface WeatherAPI {
    @GET("/data/2.5/weather")
    fun getWeather(@Query("q") q: String, @Query("units") units : String, @Query("appid") appid : String) : retrofit2.Call<WeatherResult>
}