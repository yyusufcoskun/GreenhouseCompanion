package com.example.greenhousecompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.greenhousecompanion.api.model.TemperatureData
import com.example.greenhousecompanion.api.service.ApiInterface
import com.example.greenhousecompanion.ui.screens.GreenhouseCompanionScreen
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {
    private val BASE_URL = "http://192.168.1.116"
    //private var temperatureData: TemperatureData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenhouseCompanionScreen()
        }
        loadTemperatureData()
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
                                println(response.body()) // not getting any data here, ya burada problem var ya da modelde
                        }
                    }
            }

            override fun onFailure(call: Call<TemperatureData>, t: Throwable) {
                t.printStackTrace()
            }

        }) // parantez buraya geliyor
    }
}

