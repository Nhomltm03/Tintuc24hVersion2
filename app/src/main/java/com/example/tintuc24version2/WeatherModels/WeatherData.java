package com.example.tintuc24version2.WeatherModels;

public class WeatherData {
    public String Temp_Min;
    public String Temp_Max;
    public String Humidity;
    public String Pressure;
    public String Wind;
    public String DateTime;
    public String Description_status;
    public String Icon_status;
    public String Sunset;
    public String Sunrise;

    public WeatherData(String temp_Min, String temp_Max, String humidity, String pressure,
                       String wind, String dateTime, String description_status,
                       String icon_status,String sunset,String sunrise) {
       Temp_Min = temp_Min;
       Temp_Max = temp_Max;
       Humidity = humidity;
       Pressure = pressure;
       Wind = wind;
       DateTime = dateTime;
       Description_status = description_status;
       Icon_status = icon_status;
       Sunset = sunset;
       Sunrise = sunrise;
    }

    public String getTemp_Min() {
        return Temp_Min;
    }

    public void setTemp_Min(String temp_Min) {
        Temp_Min = temp_Min;
    }

    public String getTemp_Max() {
        return Temp_Max;
    }

    public void setTemp_Max(String temp_Max) {
        Temp_Max = temp_Max;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getPressure() {
        return Pressure;
    }

    public void setPressure(String pressure) {
        Pressure = pressure;
    }

    public String getWind() {
        return Wind;
    }

    public void setWind(String wind) {
        Wind = wind;
    }


    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getDescription_status() {
        return Description_status;
    }

    public void setDescription_status(String description_status) {
        Description_status = description_status;
    }

    public String getIcon_status() {
        return Icon_status;
    }

    public void setIcon_status(String icon_status) {
        Icon_status = icon_status;
    }

    public String getSunset() {
        return Sunset;
    }

    public void setSunset(String sunset) {
        Sunset = sunset;
    }

    public String getSunrise() {
        return Sunrise;
    }

    public void setSunrise(String sunrise) {
        Sunrise = sunrise;
    }
}
