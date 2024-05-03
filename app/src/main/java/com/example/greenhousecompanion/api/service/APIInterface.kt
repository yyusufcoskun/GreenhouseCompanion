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

    @GET("/light")
    fun getLightIntensity(): Call<LightIntensityData>

    @GET("/soil")
    fun getSoilMoisture(): Call<SoilMoistureData>

    //@POST("/pump")
    //fun postWaterPump(): implement post here

    //@POST("/fan")
    //fun postFan(): implement post here
}