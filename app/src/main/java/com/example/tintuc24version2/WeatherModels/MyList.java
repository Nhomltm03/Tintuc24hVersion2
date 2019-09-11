package com.example.tintuc24version2.WeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyList {
    @SerializedName("dt")
    @Expose
    public   int dt;

    @SerializedName("main")
    @Expose
    public   Main main;


    @SerializedName("weather")
    @Expose
    public List<Weather> weather;

    @SerializedName("wind")
    @Expose
    public Wind wind;

    @SerializedName("sys")
    @Expose
    public Sys_Forecast sys_forecast;

    @SerializedName("dt_txt")
    @Expose
    public String dt_txt;

    @SerializedName("rain")
    @Expose
    public Rain rain;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Sys_Forecast getSys_forecast() {
        return sys_forecast;
    }

    public void setSys_forecast(Sys_Forecast sys_forecast) {
        this.sys_forecast = sys_forecast;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }
}
