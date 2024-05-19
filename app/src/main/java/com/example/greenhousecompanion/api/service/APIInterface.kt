package com.example.greenhousecompanion.api.service

import com.example.greenhousecompanion.api.model.HumidityData
import com.example.greenhousecompanion.api.model.LightIntensityData
import com.example.greenhousecompanion.api.model.SoilMoistureData
import com.example.greenhousecompanion.api.model.TemperatureData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("/temperature")
    fun getTemperature(): Call<TemperatureData>

    @GET("/humidity")
    fun getHumidity(): Call<HumidityData>

    @GET("/moisture")
    fun getSoilMoisture(): Call<SoilMoistureData>

    @POST("/waterOn")
    fun turnPumpOn(): Call<Void>

    @POST("/waterOff")
    fun turnPumpOff(): Call<Void>

    @POST("/fanOn")
    fun turnFanOn(): Call<Void>

    @POST("/fanOff")
    fun turnFanOff(): Call<Void>


}