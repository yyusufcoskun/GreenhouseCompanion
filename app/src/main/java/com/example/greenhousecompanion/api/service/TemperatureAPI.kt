package com.example.greenhousecompanion.api.service

import com.example.greenhousecompanion.api.model.TemperatureData
import retrofit2.http.GET

interface TemperatureAPI {
    @GET("/temperature")
    fun getTemperature(): TemperatureData
}