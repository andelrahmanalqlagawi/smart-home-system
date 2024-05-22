#if defined(ESP32)
#include <WiFi.h>
#elif defined(ESP8266)
#include <FirebaseESP8266.h>
#include <ESP8266WiFi.h>
#endif
#include <Firebase_ESP_Client.h>
#include <addons/RTDBHelper.h>
#include <Keypad.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <Servo.h>

// WiFi credentials
#define WIFI_SSID "Red"
#define WIFI_PASSWORD "12345678"

// Firebase credentials
#define DATABASE_URL "smart-home0-default-rtdb.firebaseio.com" //<databaseName>.firebaseio.com or <databaseName>.<region>.firebasedatabase.app
#define DATABASE_SECRET "txyk2OyH8j9Ldwv1JfpnhgRrR1LBp377alMy9c1c"

FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;

LiquidCrystal_I2C lcd(0x27, 20, 4);
Servo myservo;

const byte ROWS = 2; 
const byte COLS = 2; 
char hexaKeys[ROWS][COLS] = {
  {'0', '1'},
  {'2', '3'}
};
byte rowPins[ROWS] = {2, 13}; 
byte colPins[COLS] = {15, 10}; 
Keypad customKeypad = Keypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS);

const int passwordLength = 4; 
String entered_password = ""; 
const char mypassword[5] = "1111"; 
int attempts = 0;
const int maxAttempts = 3;

#define tmp A0
#define led D0
#define fan 9
#define trig D5
#define echo D6

int distance;
long duration;
float temperature;

void setup() {
  pinMode(trig, OUTPUT);
  pinMode(led, OUTPUT);
  pinMode(fan, OUTPUT);
  pinMode(tmp, INPUT);
  pinMode(echo, INPUT);

  Serial.begin(115200);
  //lcd.begin();

  // Connect to Wi-Fi
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());

  // Firebase setup
  config.database_url = DATABASE_URL;
  config.signer.tokens.legacy_token = DATABASE_SECRET;
  Firebase.reconnectWiFi(true);
  Firebase.begin(&config, &auth);

  lcd.print("System Ready");
}

void loop() {
  ledToggle();
  fanToggle();
  lcdMessage();
  getTemp();
  getNearest();
  getPassword();
  delay(2000);
}

void ledToggle() {
  if (Firebase.RTDB.getString(&fbdo, "Led/state")) {
    String ledState = fbdo.stringData();
    Serial.println("LED state: " + ledState);
    digitalWrite(led, ledState == "ON" ? HIGH : LOW);
  } else {
    Serial.println("Failed to get LED state: " + fbdo.errorReason());
  }
}

void fanToggle() {
  if (Firebase.RTDB.getString(&fbdo, "FanMotor/state")) {
    String fanState = fbdo.stringData();
    Serial.println("Fan state: " + fanState);
    digitalWrite(fan, fanState == "ON" ? HIGH : LOW);
  } else {
    Serial.println("Failed to get Fan state: " + fbdo.errorReason());
  }
}

void lcdMessage() {
  if (Firebase.RTDB.getString(&fbdo, "messages/state")) {
    String message = fbdo.stringData();
    Serial.println("LCD message: " + message);
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(message);
  } else {
    Serial.println("Failed to get LCD message: " + fbdo.errorReason());
  }
}

void getTemp() {
  temperature = analogRead(tmp);
  Firebase.RTDB.setFloat(&fbdo, "Temperature/temp", temperature);
  Serial.println("Temperature: " + String(temperature));
}

void getNearest() {
  digitalWrite(trig, LOW);
  delayMicroseconds(2);
  digitalWrite(trig, HIGH);
  delayMicroseconds(10);
  digitalWrite(trig, LOW);
  duration = pulseIn(echo, HIGH);
  distance = duration * 0.034 / 2;
  Firebase.RTDB.setFloat(&fbdo, "Distance", distance);
  Serial.println("Distance: " + String(distance));
 
}
