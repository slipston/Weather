package hu.ait.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import hu.ait.weather.adapter.CityAdapter
import hu.ait.weather.data.WeatherResult
import hu.ait.weather.network.WeatherAPI
import kotlinx.android.synthetic.main.city_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class CityDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.city_details)

        val cityName = intent.getStringExtra(CityAdapter.CITY_NAME)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherAPI = retrofit.create(WeatherAPI::class.java)
        val cityWeatherCall = weatherAPI.getWeather(cityName, getString(R.string.metric), getString(
                    R.string.appid))
        cityWeatherCall.enqueue(object : Callback<WeatherResult>{
            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                tvDetailsName.text = t.message
            }

            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                var weatherResult = response.body()
//                tvDetailsName.text = "${response.body()}" // for debugging
                tvDetailsName.text = "${weatherResult?.name}"
                tvTemp.text = "Temperature: ${weatherResult?.main?.temp} C"
                tvMaxTemp.text = "Max temp: ${weatherResult?.main?.temp_max} C"
                tvMinTemp.text = "Min temp: ${weatherResult?.main?.temp_min} C"
                tvHumidity.text = "Humidity: ${weatherResult?.main?.humidity}%"

                val sunriseEpoch = weatherResult?.sys?.sunrise?.toLong()
                val sunriseTime = SimpleDateFormat("HH:mm:ss")
                    .format(Date(sunriseEpoch!!.times(1000)))
                tvSunrise.text = "Sunrise: ${sunriseTime} UTC"

                val sunsetEpoch = weatherResult?.sys?.sunset?.toLong()
                val sunsetTime = SimpleDateFormat("HH:mm:ss")
                    .format(Date(sunsetEpoch!!.times(1000)))
                tvSunset.text = "Sunset: ${sunsetTime} UTC"

                Glide.with(this@CityDetails) .load(
                    ("https://openweathermap.org/img/w/" + response.body()?.weather?.get(0)?.icon + ".png"))
                    .into(ivWeatherIcon)

            }
        }
        )
    }
}