package com.example.tintuc24version2;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tintuc24version2.FragmentAdapter.SectionsPagerAdapter;
import com.example.tintuc24version2.Models.Article;
import com.example.tintuc24version2.Models.News;
import com.example.tintuc24version2.NewsApdapter.NewsAdapter;
import com.example.tintuc24version2.WeatherAPI.OpenWeatherInterface;
import com.example.tintuc24version2.WeatherAPI.WeatherApiClient;
import com.example.tintuc24version2.WeatherModels.Weather;
import com.example.tintuc24version2.WeatherModels.WeatherResult;
import com.example.tintuc24version2.api.ApiClient;
import com.example.tintuc24version2.api.ApiInterface;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {


    private List<Weather> weathers = new ArrayList<>();
public static  final  String API_KEY_WEATHER = "27a77fcad68bf8df89e0124c586f0d30";
public static  final  String UNITS = "metric";

private TextView textViewCity, textViewTemp, textViewStatus;

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_weather, container, false);

        textViewCity = (TextView) rootView.findViewById(R.id.tv_City);
        textViewStatus = (TextView) rootView.findViewById(R.id.tv_Status);
        textViewTemp = (TextView) rootView.findViewById(R.id.tv_Temp);






        return rootView;
    }

    private void getCurrentWeather( final  String cityName ){
        OpenWeatherInterface openWeatherInterface = WeatherApiClient.getApitClient().create(OpenWeatherInterface.class);
       Call<WeatherResult> weatherCall;
       weatherCall = openWeatherInterface.getWeatherByCityName(cityName,UNITS,API_KEY_WEATHER);

       weatherCall.enqueue(new Callback<WeatherResult>() {
           @Override
           public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {

           }

           @Override
           public void onFailure(Call<WeatherResult> call, Throwable t) {

           }
       });






    }

}
