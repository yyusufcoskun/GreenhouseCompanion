#include <ESP8266WiFi.h>
#include <ESPAsyncWebServer.h>
#include <DHT.h>
#include <ArduinoJson.h>

#define DHT_PIN 5 // D1 - Digital pin connected to the DHT sensor, use GPIO number not pin number
#define DHTTYPE DHT11 // DHT 11
#define SOIL_SENSOR_PIN A0
#define WATER_PUMP_PIN 13 // D7
#define FAN_PIN 4 //D2 

const char* ssid = ""; // don't forget to change these when you make a hotspot
const char* password = "";

//const char* ssid = "airbenders4"; 
//const char* password = "airpass123";

DHT dht(DHT_PIN, DHTTYPE); // humidity - temp sensor

float temperature = 0;
float humidity = 0;
int soilMoisture = 0;

//IPAddress local_IP(192, 168, 154, 109); // Set the static IP address
IPAddress local_IP(192, 168, 1, 109);
//IPAddress gateway(192, 168, 154, 189);    // Set the gateway
IPAddress gateway(192, 168, 1, 1);    
IPAddress subnet(255, 255, 255, 0); 




AsyncWebServer server(80);

void setup() {
  Serial.begin(9600);
  
  dht.begin();
  pinMode(FAN_PIN, OUTPUT);
  pinMode(WATER_PUMP_PIN, OUTPUT);
  digitalWrite(FAN_PIN, LOW); 
  digitalWrite(WATER_PUMP_PIN, LOW); 
  
  if (!WiFi.config(local_IP, gateway, subnet)) {
    Serial.println("STA Failed to configure");
  } else {
    Serial.println("STA Configured successfully");
  }

  
  // Connect to wifi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  
  Serial.print("Connected to WiFi, the IP address is: ");
  Serial.println(WiFi.localIP());

  // Server endpoints
  server.on("/", HTTP_GET, [](AsyncWebServerRequest *request){
    request->send(200, "text/plain", "ESP8266 Web Server");
  });

  server.on("/temperature", HTTP_GET, [](AsyncWebServerRequest *request){
    if (!isnan(temperature)) {
      String response;
      DynamicJsonDocument doc(1024);
      doc["temperature"] = temperature;
      serializeJson(doc, response);
      request->send(200, "application/json", response);
      Serial.println("Temperature sent successfully.");
    } else {
      request->send(500, "text/plain", "Failed to read temperature");
      Serial.println("Temperature couldn't be sent.");
    }
  });

  server.on("/humidity", HTTP_GET, [](AsyncWebServerRequest *request){
    if (!isnan(humidity)) {
      String response;
      DynamicJsonDocument doc(1024);
      doc["humidity"] = humidity;
      serializeJson(doc, response);
      request->send(200, "application/json", response);
      Serial.println("Humidity sent successfully.");
    } else {
      request->send(500, "text/plain", "Failed to read humidity");
      Serial.println("Humidity couldn't be sent.");
    }
  });

  server.on("/moisture", HTTP_GET, [](AsyncWebServerRequest *request){
    if (!isnan(soilMoisture)) {
      String response;
      DynamicJsonDocument doc(1024);
      doc["soilMoisture"] = soilMoisture;
      serializeJson(doc, response);
      request->send(200, "application/json", response);
      Serial.println("Soil moisture sent successfully.");
    } else {
      request->send(500, "text/plain", "Failed to read soil moisture");
      Serial.println("Soil moisture couldn't be sent.");
    }
  });

  server.on("/fanOn", HTTP_ANY, [](AsyncWebServerRequest *request){
    digitalWrite(FAN_PIN, HIGH);
    request->send(200, "text/plain", "Fan status: ON");
    Serial.println("Fan status: ON");
  });

  server.on("/fanOff", HTTP_ANY, [](AsyncWebServerRequest *request){
    digitalWrite(FAN_PIN, LOW);
    request->send(200, "text/plain", "Fan status: OFF");
    Serial.println("Fan status: OFF");
  });

  server.on("/waterOn", HTTP_ANY, [](AsyncWebServerRequest *request){
    digitalWrite(WATER_PUMP_PIN, HIGH);
    request->send(200, "text/plain", "Pump status: ON");
    Serial.println("Pump status: ON");
  });

  server.on("/waterOff", HTTP_ANY, [](AsyncWebServerRequest *request){
    digitalWrite(WATER_PUMP_PIN, LOW);
    request->send(200, "text/plain", "Pump status: OFF");
    Serial.println("Pump status: OFF");
  });

  // Start server
  server.begin();
  Serial.println("HTTP server started");
}

void loop() {
  readSensors(); // Read sensor data

  // ------------------------ Write to serial to debug -------------------------------------
  Serial.print("Humidity: ");
  Serial.print(humidity);
  Serial.println("%");
  Serial.print("Temperature: ");
  Serial.print(temperature);
  Serial.println(" °C");
  Serial.print("Soil moisture level: %");
  Serial.println(soilMoisture);

  delay(1000); // delay to lower buffer
}

void readSensors() {
  temperature = dht.readTemperature();
  humidity = dht.readHumidity();
  soilMoisture = 100 - (analogRead(SOIL_SENSOR_PIN)/10.24);
}
