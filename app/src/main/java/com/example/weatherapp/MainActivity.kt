package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weatherapp.api.RetrofitClient
import com.example.weatherapp.api.WeatherAPI
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.WeatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val api: WeatherAPI = RetrofitClient.getRetrofit().create(WeatherAPI :: class.java)


    override fun onCreate(savedInstanceState: Bundle?) = with(binding) {
        super.onCreate(savedInstanceState)
        setContentView(root)

        val key = getString(R.string.weather_api_key)

        api.getWeatherData("Kara-Balta", key).enqueue(object : Callback<WeatherData> {
            override fun onResponse(
                call: Call<WeatherData>,
                response: Response<WeatherData>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    Log.i("$$$", "${data.name}")
                    cityName.text = data.name.toString()
                    cityId.text = data.id.toString()
                    temp.text = data.main.temp.toString()
                }
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Log.e("$$$", "Something happened wrong")
                Log.i("$$$", t.cause.toString())
            }
        })

    }
}