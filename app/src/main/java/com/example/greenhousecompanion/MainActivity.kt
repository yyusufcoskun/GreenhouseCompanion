package com.example.greenhousecompanion

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
import android.Manifest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {
    private val BASE_URL = "http://192.168.1.105"
    private lateinit var job: Job // Coroutine job - trial
    private val coroutineScope = CoroutineScope(Dispatchers.IO) // trial code

    private var temperature by mutableStateOf("")
    private var humidity by mutableStateOf("")
    private var soilMoisture by mutableStateOf("")

    private var temperatureNotificationId: Int = 0
    private var humidityNotificationId: Int = 0
    private var soilMoistureNotificationId: Int = 0

    private val upperTemperatureThreshold = 23.0 //TODO Change these parameters
    private val lowerTemperatureThreshold = 20.0
    private val upperHumidityThreshold = 65.0
    private val lowerHumidityThreshold = 40.0
    private val upperSoilMoistureThreshold = 50.0
    private val lowerSoilMoistureThreshold = 25.0
    //TODO add soil moisture sensor as well


    private val CHANNEL_ID = "channelID"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreenhouseCompanionScreen(
                temperature = temperature,
                humidity = humidity //TODO add soil moisture too, also as a parameter in MainScreen.kt
            )
        }

        createNotificationChannel()

        temperatureAndHumidityDataFetcher()
        //soilMoistureDataFetcher() //TODO IMPORANT!!!!!!!!!!!!!!!! uncomment this
    }

    // ------------------ COROUTINES TIMERS FOR FETCHING DATA ON AN INTERVAL -----------------------------------------------------------
    //trying timer with coroutines

    private fun temperatureAndHumidityDataFetcher() {
        job = coroutineScope.launch {
            while (isActive) { // Loop until coroutine is active
                loadTemperatureData() // Fetch data from server
                delay(100)
                loadHumidityData()
                delay(5000) // TODO Delay for 5 seconds -- make 60
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
    private fun loadTemperatureData() { // TODO refactor these functions
        val gson = GsonBuilder().setLenient().create() // JSON hala bozuk mu geliyor acaba? web server değiştirdik.
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

                        if (temperatureValue > upperTemperatureThreshold) {
                            sendNotification("Temperature", "high")
                            println("Temperature high notification sent.")
                        } else if (temperatureValue < lowerTemperatureThreshold){
                            sendNotification("Temperature", "low")
                            println("Temperature low notification sent.")
                        }

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

                        if (humidityValue > upperHumidityThreshold) {
                            sendNotification("Humidity", "high")
                            println("Humidity high notification sent.")
                        } else if (humidityValue < lowerHumidityThreshold){
                            sendNotification("Humidity", "low")
                            println("Humidity low notification sent.")
                        }

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

                        if (soilMoistureValue > upperSoilMoistureThreshold) {
                            sendNotification("Dirt", "wet")
                            println("Soil wet notification sent.")
                        } else if (soilMoistureValue < lowerSoilMoistureThreshold){
                            sendNotification("Dirt", "dry")
                            println("Soil dry notification sent.")
                        }

                    }
                }
            }

            override fun onFailure(call: Call<SoilMoistureData>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    // ------------------ NOTIFICATION FUNCTIONS -----------------------------------------------------------------------------------
    private fun sendNotification(dataType: String, valueType: String){ // dataType = Temperature, Humidity... valueType = High/Low

        val notificationId = when (dataType) { // sensör verisi tipine göre notificationları stack yapıyor
            "Temperature" -> {
                if (temperatureNotificationId == 0) {
                    temperatureNotificationId = System.currentTimeMillis().toInt()
                }
                temperatureNotificationId
            }
            "Humidity" -> {
                if (humidityNotificationId == 0) {
                    humidityNotificationId = System.currentTimeMillis().toInt()
                }
                humidityNotificationId
            }"Dirt" -> {
                if (soilMoistureNotificationId == 0) {
                    soilMoistureNotificationId = System.currentTimeMillis().toInt()
                }
                soilMoistureNotificationId
            }
            else -> System.currentTimeMillis().toInt()
        }

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.waterdrop3)
            .setContentTitle("Greenhouse Alert!")
            .setContentText("$dataType is too ${valueType}!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@with
            }
            notify(notificationId, builder.build())
        }
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "First channel"
            val descriptionText = "Channel desc"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

