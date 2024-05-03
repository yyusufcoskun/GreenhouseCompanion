package com.example.greenhousecompanion.api.service

import com.example.greenhousecompanion.api.model.HumidityData
import com.example.greenhousecompanion.api.model.TemperatureData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("/temperature")
    fun getTemperature(): Call<TemperatureData>

    @GET("/humidity")
    fun getHumidity(): Call<HumidityData>
}