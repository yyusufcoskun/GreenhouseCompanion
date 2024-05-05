#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <DHT.h>
#include <ArduinoJson.h>

#define DHTPIN 5     // Digital pin connected to the DHT sensor, use GPIO number not pin number
#define DHTTYPE DHT11   // DHT 11
#define LIGHT_SENSOR_PIN A0

const char* ssid = ""; // don't forget to change these when you make a hotspot
const char* password = "";

// Creates web server instanc
ESP8266WebServer server(80);

DHT dht(DHTPIN, DHTTYPE); // humidity - temp sensor

float temperature = 0;
float humidity = 0;
int lightIntensity = 0;

void setup() {
  Serial.begin(9600);
  
  dht.begin();
  
  
  // Connect to wifi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.print("Connected to WiFi, the IP address is: ");
  Serial.println(WiFi.localIP());

  // Server endpoints -- you can't do them in 1 method like it used to be in the RPi web server code.
  server.on("/", handleRoot);
  server.on("/temperature", HTTP_GET, sendTemperature);
  server.on("/humidity", HTTP_GET, sendHumidity);
  server.on("/light", HTTP_GET, sendLightIntensity);

  // Start server
  server.begin();
  Serial.println("HTTP server started");
}

void loop() {
  server.handleClient();
  
  readSensors(); // Read sensor data

  // Make multiple of these ----------------------------------------
  if (isnan(humidity) || isnan(temperature)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }
  if (isnan(lightIntensity)) {
    Serial.println("Failed to read from light sensor!");
    return;
  }
  // ----------------------------------------------------------------


  // ------------------------ Write to serial to debug -------------------------------------
  Serial.print("Humidity: ");
  Serial.println(humidity);
  Serial.print(" %\t");
  Serial.print("Temperature: ");
  Serial.print(temperature);
  Serial.println(" °C");
  Serial.print(" ");
  Serial.print("Light intensity: ");
  Serial.println(lightIntensity);
  //Serial.println("\t");
  //Serial.print("Soil moisture level: ");
  //Serial.print(moisture);
  
  delay(1000); // delay to lower buffer
}

void readSensors() {
  temperature = dht.readTemperature();
  humidity = dht.readHumidity();
  lightIntensity = analogRead(LIGHT_SENSOR_PIN);
}

void handleRoot() {
  server.send(200, "text/plain", "ESP8266 Web Server");
}

void sendTemperature() {
  if (!isnan(temperature)) {
    server.send(200, "application/json", String("{\"temperature\":") + temperature + "}");
  } else {
    server.send(500, "text/plain", "Failed to read temperature");
  }
}

void sendHumidity() {
  if (!isnan(humidity)) {
    server.send(200, "application/json", String("{\"humidity\":") + humidity + "}");
  } else {
    server.send(500, "text/plain", "Failed to read humidity");
  }
}

void sendLightIntensity() {
  if(!isnan(lightIntensity)){
    server.send(200, "application/json", String("{\"lightIntensity\":") + lightIntensity + "}");
  } else {
    server.send(500, "text/plain", "Failed to read light intensity");
  }
}

/* I tried to make it shorter but no use I guess
void sendData(dataSource) {
  if (!isnan(dataSource)) {
    server.send(200, "application/json", String("{\" + String(dataSource) + \":") + dataSource + "}");
  } else {
    switch(dataSource){
      case temperature:
      server.send(500, "text/plain", "Failed to read temperature.");
      break;

      case humidity:
      server.send(500, "text/plain", "Failed to read humidity.");
      break;

      case lightIntensity:
      server.send(500, "text/plain", "Failed to read light intensity.");
      break;
    }
  }
}
*/
