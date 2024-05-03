package com.example.greenhousecompanion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.greenhousecompanion.api.model.TemperatureData
import com.example.greenhousecompanion.api.service.ApiInterface
import com.example.greenhousecompanion.ui.screens.GreenhouseCompanionScreen
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {
    private val BASE_URL = "http://192.168.1.112"
    private lateinit var job: Job // Coroutine job - trial
    private val coroutineScope = CoroutineScope(Dispatchers.IO) // trial code
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenhouseCompanionScreen()
        }
        temperatureDataFetcher()
    }

    //trying timer with coroutines
    private fun temperatureDataFetcher() {
        job = coroutineScope.launch {
            while (isActive) { // Loop until coroutine is active
                loadTemperatureData() // Fetch data from server
                delay(5000) // Delay for 5 seconds
            }
        }
    }
    private fun loadTemperatureData() { // acaba hepsini tek fonksiyonda toplayabilir miyim?
        val gson = GsonBuilder().setLenient().create() // JSON bozuk geliyormuş şimdilik böyle lenient olarak koydum
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)) //burada da gson üstünden kurdu üç satır üstteki
            .build()

        val service = retrofit.create(ApiInterface::class.java)
        val call = service.getTemperature()

        call.enqueue(object: Callback<TemperatureData> { // ...asynchronously send the request diyor
            override fun onResponse(
                call: Call<TemperatureData>,
                response: Response<TemperatureData>
            ) {
                    if (response.isSuccessful){
                        response.body()?.let { // .let = eğer null değilse bunu çalıştır
                                println(response.body())
                        }
                    }
            }

            override fun onFailure(call: Call<TemperatureData>, t: Throwable) {
                t.printStackTrace()
            }

        }) // parantez buraya geliyor
    }
}

