package com.example.avma1997.weatherapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Avma1997 on 11/6/2017.
 */

public class WeatherResponse {
    @SerializedName("list")
    private ArrayList<Weather> weatherList;

    public ArrayList<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(ArrayList<Weather> weatherList) {
        this.weatherList = weatherList;
    }


}
class Weather{
    long dt;
    @SerializedName("main")
    WeatherMainDetails weatherMainDetails;
    @SerializedName("weather")
    ArrayList<WeatherDetails> weatherDetails;
    Clouds clouds;
    Wind wind;
    String dt_txt;


}
class WeatherMainDetails{

    double temp;
    double temp_min;
    double temp_max;
    double pressure;
    double sea_level;
    double grnd_level;
    double humidity;
    double temp_kf;

    public WeatherMainDetails(double temp, double temp_min, double temp_max, double pressure, double sea_level, double grnd_level, double humidity, double temp_kf) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.sea_level = sea_level;
        this.grnd_level = grnd_level;
        this.humidity = humidity;
        this.temp_kf = temp_kf;
    }
}

class WeatherDetails{
    int id;
    String main;
    String description;
    String icon;

    public WeatherDetails(int id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }
}
class Clouds{
    @SerializedName("all")
    int quantity;

    public Clouds(int quantity) {
        this.quantity = quantity;
    }
}
class Wind{
    double speed;
    double deg;

    public Wind(double speed, double deg) {
        this.speed = speed;
        this.deg = deg;
    }
}
