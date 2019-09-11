package com.example.tintuc24version2.WeatherModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResult {
    @SerializedName("coord")
    @Expose
    public Coord coord;

    @SerializedName("weather")
    @Expose
    public List<Weather> weather;

    @SerializedName("base")
    @Expose
    public   String base;

    @SerializedName("main")
    @Expose
    public   Main main;

    @SerializedName("visibility")
    @Expose
    public   int visibility;

    @SerializedName("wind")
    @Expose
    public   Wind wind;

    @SerializedName("clouds")
    @Expose
    public   Cloud cloud;

    @SerializedName("dt")
    @Expose
    public   int dt;

    @SerializedName("sys")
    @Expose
    public   Sys sys;

    @SerializedName("timezone")
    @Expose
    public   int  timezone;

    @SerializedName("id")
    @Expose
    public   int id;

    @SerializedName("name")
    @Expose
    private  String name;

    @SerializedName("cod")
    @Expose
    public   int cod;

    public WeatherResult() {
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}
