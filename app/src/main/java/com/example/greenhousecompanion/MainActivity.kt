package com.example.greenhousecompanion

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.greenhousecompanion.api.model.HumidityData
import com.example.greenhousecompanion.api.model.SoilMoistureData
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
    private val BASE_URL = "http://192.168.1.113"
    private lateinit var job: Job // Coroutine job - trial
    private val coroutineScope = CoroutineScope(Dispatchers.IO) // trial code

    private var temperature by mutableStateOf("")
    private var humidity by mutableStateOf("")
    private var soilMoisture by mutableStateOf("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenhouseCompanionScreen(
                temperature = temperature,
                humidity = humidity
            )
        }
        temperatureDataFetcher()
        humidityDataFetcher()
        //soilMoistureDataFetcher()
    }


    // ------------------ COROUTINES TIMERS FOR FETCHING DATA ON AN INTERVAL -----------------------------------------------------------
    //trying timer with coroutines
    private fun temperatureDataFetcher() {
        job = coroutineScope.launch {
            while (isActive) { // Loop until coroutine is active
                loadTemperatureData() // Fetch data from server
                delay(5000) // Delay for 5 seconds -- make 60
            }
        }
    }

    private fun humidityDataFetcher() {
        job = coroutineScope.launch {
            while (isActive) {
                loadHumidityData()
                delay(5000) // Delay for 5 seconds -- make 60
            }
        }
    }

    private fun soilMoistureDataFetcher() {
        job = coroutineScope.launch {
            while (isActive) {
                loadSoilMoistureData()
                delay(5000) // Delay for 5 seconds
            }
        }
    }


    // ------------------ LOAD DATA FUNCTIONS -------------------------------------------------------------------------------------------

    // ------------------ LOAD TEMPERATURE DATA -------------------------------------------------
    private fun loadTemperatureData() { // acaba hepsini tek fonksiyonda toplayabilir miyim?
        val gson = GsonBuilder().setLenient().create() // JSON bozuk geliyormuş şimdilik böyle lenient olarak koydum -- değiştirilebilir
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
                                val temperatureValue = it.temperature
                                temperature = temperatureValue.toString()
                        }
                    }
            }

            override fun onFailure(call: Call<TemperatureData>, t: Throwable) {
                t.printStackTrace()
            }

        }) // parantez buraya geliyor
    }


    // ------------------ LOAD HUMIDITY DATA -------------------------------------------------

    private fun loadHumidityData() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val service = retrofit.create(ApiInterface::class.java)
        val call = service.getHumidity()

        call.enqueue(object: Callback<HumidityData> {
            override fun onResponse(
                call: Call<HumidityData>,
                response: Response<HumidityData>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        println(response.body())
                        val humidityValue = it.humidity
                        humidity = humidityValue.toString()
                    }
                }
            }

            override fun onFailure(call: Call<HumidityData>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    // ------------------ LOAD SOIL MOISTURE DATA -------------------------------------------------

    private fun loadSoilMoistureData() {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val service = retrofit.create(ApiInterface::class.java)
        val call = service.getSoilMoisture()

        call.enqueue(object: Callback<SoilMoistureData> {
            override fun onResponse(
                call: Call<SoilMoistureData>,
                response: Response<SoilMoistureData>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                        println(response.body())
                        val soilMoistureValue = it.soilMoisture
                        soilMoisture = soilMoistureValue.toString()
                    }
                }
            }

            override fun onFailure(call: Call<SoilMoistureData>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}

