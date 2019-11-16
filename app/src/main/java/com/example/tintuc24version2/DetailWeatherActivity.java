package com.example.tintuc24version2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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
    public static final String JSON_OBJECT_KEY_CITY = "city";
    public static final String JSON_OBJECT_KEY_COUNTRY = "country";
    public static final String JSON_OBJECT_KEY_NAME = "name";
    public static final String JSON_OBJECT_KEY_POPULATION = "population";
    public static final String JSON_OBJECT_KEY_COORD = "coord";
    public static final String JSON_OBJECT_KEY_LAT = "lat";
    public static final String JSON_OBJECT_KEY_LON = "lon";
    public static final String JSON_OBJECT_KEY_MAIN = "main";
    public static final String JSON_OBJECT_KEY_LIST = "list";
    public static final String JSON_OBJECT_KEY_TEMP_MAX = "temp_max";
    public static final String JSON_OBJECT_KEY_TEMP_MIN = "temp_min";
    public static final String JSON_OBJECT_KEY_HUMIDITY = "humidity";
    public static final String JSON_OBJECT_KEY_PRESSURE = "pressure";
    public static final String JSON_OBJECT_KEY_DATE_TIME = "dt";
    public static final String JSON_OBJECT_KEY_WEATHER = "weather";
    public static final String JSON_OBJECT_KEY_DESCRIPTION = "description";
    public static final String JSON_OBJECT_KEY_ICON = "icon";
    public static final String JSON_OBJECT_KEY_WIND = "wind";
    public static final String JSON_OBJECT_KEY_SPEED = "speed";
    public static final String DATE_TIME_FORMAT_CURRENT_DAY = "EEEE MM/dd/yyyy";
    public static final String DATE_TIME_FORMAT_CURRENT_HOURS = "HH";
    public static final String EXTRA_QUERY_NAME = "cityName";
    RecyclerView rvForecast;
    private TextView tvFullName, tvPopulation, tvGeocoord;
    private WeatherPageAdapter weatherPageAdapter;
    public ArrayList<WeatherData> weatherDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_weather);
        rvForecast = findViewById(R.id.weatherForcast_recylceview);
        rvForecast.setHasFixedSize(true);
        rvForecast.setLayoutManager(new LinearLayoutManager(getParent(), LinearLayoutManager.HORIZONTAL, false));
        tvFullName = findViewById(R.id.tv_fullName);
        tvGeocoord = findViewById(R.id.tv_detailGeoCoord);
        tvPopulation = findViewById(R.id.tv_detailPopulation);
        loadWeatherForecastData(getIntent().getStringExtra(EXTRA_QUERY_NAME));
        weatherDataList = new ArrayList<>();
        weatherPageAdapter = new WeatherPageAdapter(getBaseContext(), weatherDataList);
        rvForecast.setAdapter(weatherPageAdapter);
    }


    public void loadWeatherForecastData(String cityName) {
        String queryUrl = BASE_URL + cityName + UNITS + CNT + API_KEY_WEATHER;
        RequestQueue requestQueue = Volley.newRequestQueue(DetailWeatherActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, queryUrl,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject(JSON_OBJECT_KEY_CITY);
                            Locale locale = new Locale("", jsonObjectCity.getString(JSON_OBJECT_KEY_COUNTRY));
                            tvFullName.setText(jsonObjectCity.getString(JSON_OBJECT_KEY_NAME) + ", " + locale.getDisplayCountry());
                            tvPopulation.setText(jsonObjectCity.getString(JSON_OBJECT_KEY_POPULATION));
                            tvGeocoord.setText("[" + jsonObjectCity.getJSONObject(JSON_OBJECT_KEY_COORD).getString(JSON_OBJECT_KEY_LAT) + "," +
                                    jsonObjectCity.getJSONObject(JSON_OBJECT_KEY_COORD).getString(JSON_OBJECT_KEY_LON) + "]");
                            JSONArray jsonArrayList = jsonObject.getJSONArray(JSON_OBJECT_KEY_LIST);
                            for (int i = 0; i < jsonArrayList.length(); i++) {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                Date date_Formatter = new Date(Long.valueOf(jsonObjectList.getString(JSON_OBJECT_KEY_DATE_TIME)) * 1000L);
                                @SuppressLint("SimpleDateFormat") String detailTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT_CURRENT_DAY).format(date_Formatter) + "h " +
                                        new SimpleDateFormat(DATE_TIME_FORMAT_CURRENT_HOURS).format(date_Formatter);

                                JSONObject jsonObjectMain = jsonObjectList.getJSONObject(JSON_OBJECT_KEY_MAIN);
                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray(JSON_OBJECT_KEY_WEATHER);
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String maxTemp = jsonObjectMain.getString(JSON_OBJECT_KEY_TEMP_MAX) + "";
                                String minTemp = jsonObjectMain.getString(JSON_OBJECT_KEY_TEMP_MIN) + "";
                                String humidity = jsonObjectMain.getString(JSON_OBJECT_KEY_HUMIDITY) + "";
                                String pressure = jsonObjectMain.getString(JSON_OBJECT_KEY_PRESSURE) + "";
                                String status = jsonObjectWeather.getString(JSON_OBJECT_KEY_DESCRIPTION);
                                String icon = jsonObjectWeather.getString(JSON_OBJECT_KEY_ICON);
                                String wind = jsonObjectList.getJSONObject(JSON_OBJECT_KEY_WIND).getString(JSON_OBJECT_KEY_SPEED);

                                weatherDataList.add(new WeatherData(minTemp, maxTemp, humidity, pressure, wind, detailTimeFormat
                                        , status, icon));

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
