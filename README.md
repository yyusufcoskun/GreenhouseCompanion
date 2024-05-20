# Greenhouse Companion
Greenhouse Companion is an Android application written in Kotlin that allows users to monitor and control environmental conditions in their greenhouse. The app connects to an ESP8266 equipped with sensors for temperature, humidity, and soil moisture, as well as a water pump and a fan. The app provides a simple, user-friendly interface to view sensor data and control the actuators remotely.

## Features
- **Real-time Monitoring:** View live temperature, humidity, and soil moisture levels from your greenhouse.
- **Remote Control:** Control fan and water pump directly from the app.
- **Notifications:** Get notified about extreme conditions in your greenhouse. (Condition thresholds are pre-set)

## Screenshots
<img src="https://github.com/yyusufcoskun/GreenhouseCompanion/assets/95757221/56c83c06-3a1a-4df5-8b9e-c57de8234f32" alt="GreenhouseCompanion Main Screen" width="300"/>
<img src="https://github.com/yyusufcoskun/GreenhouseCompanion/assets/95757221/6c860835-b16c-4be2-aac3-75c35780d010" alt="GreenhouseCompanion Notifications" width="300"/>

## How it works
Sensor and actuator functions are set on the ESP8266 Built-in Web Server. The Kotlin app sends GET and POST requests to endpoints defined in the ESP8266 code.

## Requirements
- ESP8266 
- Matching Wi-Fi connection
- Android Phone or Android Studio Phone Emulator
- DHT11 Sensor
- Soil Moisture Sensor
- Water Pump
- Cooler Fan

## Credits
This project was created by Yusuf Coşkun. Special thanks to Mr. [Atıl Samancıoğlu](https://github.com/atilsamancioglu) and Mr. Hakan Özkara for equipping me with the necessary skills to create this project.

## Disclaimer
Greenhouse Companion is a hobby project and is for **personal use only**. I kindly request this rule to be followed.

