package com.example.tintuc24version2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tintuc24version2.WeatherAdapter.WeatherPageAdapter;
import com.example.tintuc24version2.WeatherModels.WeatherData;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailWeatherActivity extends AppCompatActivity {

    public static final String CNT = "&cnt=40";
    public static final String API_KEY_WEATHER = "&appid=27a77fcad68bf8df89e0124c586f0d30";
    public static final String UNITS = "&units=metric";
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";
    RecyclerView recyclerView_forecast;
    private TextView txt_fullName,txt_population,txt_geocoord;
    private  WeatherPageAdapter weatherPageAdapter;
    public ArrayList<WeatherData> weatherDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);
        recyclerView_forecast = (RecyclerView) findViewById(R.id.weatherForcast_recylceview);
        recyclerView_forecast.setHasFixedSize(true);
        recyclerView_forecast.setLayoutManager(new LinearLayoutManager(getParent(),LinearLayoutManager.HORIZONTAL,false));
        txt_fullName =(TextView) findViewById(R.id.tv_fullName);
        txt_population =(TextView) findViewById(R.id.tv_detailPopulation);
        txt_geocoord =(TextView) findViewById(R.id.tv_detailGeoCoord);
        Intent intent = getIntent();
        String queryName = intent.getStringExtra("cityName");
        loadWeatherForecastData(queryName);
        weatherDataList = new ArrayList<WeatherData>();
        weatherPageAdapter = new WeatherPageAdapter(getBaseContext(),weatherDataList);
        recyclerView_forecast.setAdapter(weatherPageAdapter);

    }


    public  void  loadWeatherForecastData(String cityName){

        String queryUrl = BASE_URL+cityName+UNITS+CNT+API_KEY_WEATHER;
        RequestQueue requestQueue = Volley.newRequestQueue(DetailWeatherActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, queryUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                            String cityID = jsonObjectCity.getString("id");
                            String country = jsonObjectCity.getString("country");
                            Locale locale = new Locale("",country);
                            String fullname = name+", "+locale.getDisplayCountry();
                            txt_fullName.setText(fullname);
                            String population  = jsonObjectCity.getString("population");
                            txt_population.setText(population);
                            String geoCoord_lat = jsonObjectCity.getJSONObject("coord").getString("lat");
                            String geoCoord_lon = jsonObjectCity.getJSONObject("coord").getString("lon");
                            Double lon = Double.valueOf(geoCoord_lon);
                            Double lat = Double.valueOf(geoCoord_lat);
                            String  geoCoord = "["+lat+","+lon+"]";
                            txt_geocoord.setText(geoCoord);
                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArrayList.length(); i++) {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                String dateTime = jsonObjectList.getString("dt");
                                long m_timeFormat = Long.valueOf(dateTime);
                                Date date_Formater = new Date(m_timeFormat * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE MM/dd/yyyy");
                                SimpleDateFormat simpleDateFormatHour = new SimpleDateFormat("HH");
                                String detail_TimeFormat =simpleDateFormatHour.format(date_Formater)+"h "+
                                        simpleDateFormat.format(date_Formater);

                                String sunset = jsonObjectCity.getString("sunset")+"";
                                String sunrise = jsonObjectCity.getString("sunrise")+"";

                                JSONObject jsonObjectMain = jsonObjectList.getJSONObject("main");
                                String maxTemp = jsonObjectMain.getString("temp_max")+"";
                                String minTemp = jsonObjectMain.getString("temp_min")+"";
                                String humidity = jsonObjectMain.getString("humidity")+"";
                                String pressure = jsonObjectMain.getString("pressure")+"";

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");

                                JSONObject jsonObjectWind = jsonObjectList.getJSONObject("wind");
                                String wind = jsonObjectWind.getString("speed");

                                weatherDataList.add(new WeatherData(minTemp,maxTemp,humidity,pressure,wind,detail_TimeFormat
                                ,status,icon,sunset,sunrise));


                            }
                            weatherPageAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getParent(), "Error No Data", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(stringRequest);

    }



}
