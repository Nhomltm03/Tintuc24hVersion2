package com.example.tintuc24version2;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tintuc24version2.WeatherAPI.OpenWeatherInterface;
import com.example.tintuc24version2.WeatherAPI.WeatherApiClient;
import com.example.tintuc24version2.WeatherModels.Main;
import com.example.tintuc24version2.WeatherModels.Sys;
import com.example.tintuc24version2.WeatherModels.Weather;
import com.example.tintuc24version2.WeatherModels.WeatherResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {


    private List<Weather> weathers = new ArrayList<>();
    private Main main = new Main();
    private WeatherResult weatherResult = new WeatherResult();
    private Sys sys = new Sys();
public static  final  String API_KEY_WEATHER = "27a77fcad68bf8df89e0124c586f0d30";
public static  final  String UNITS = "metric";

private TextView textViewCity, textViewTemp, textViewStatus;
private ImageView iconWeather;

    public WeatherFragment() {
        setHasOptionsMenu(true);
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
        iconWeather = (ImageView) rootView.findViewById(R.id.iconStatusWeather) ;
        getCurrentWeather("hanoi");

        return rootView;
    }

    private void getCurrentWeather(final  String cityName ){


        OpenWeatherInterface openWeatherInterface = WeatherApiClient.getApitClient().create(OpenWeatherInterface.class);
       Call<WeatherResult> weatherCall;
       weatherCall = openWeatherInterface.getWeatherByCityName(cityName,UNITS,API_KEY_WEATHER);
       weatherCall.enqueue(new Callback<WeatherResult>() {
           @Override
           public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
               if (response.isSuccessful() && response.body().getWeather() != null){
                   if(!weathers.isEmpty()){
                       weathers.clear();
                   }
                   weathers = response.body().getWeather();
                   main = response.body().getMain();
                   weatherResult = response.body();
                   Picasso.with((Activity)getActivity())
                           .load("http://openweathermap.org/img/wn/"+weathers.get(0).getIcon()+"@2x.png").into(iconWeather);
                   sys= response.body().getSys();
                   textViewStatus.setText(weathers.get(0).getMain());
                   textViewTemp.setText(main.getTemp()+"°C");
                   Locale locale = new Locale("",sys.getCountry());
                   textViewCity.setText(weatherResult.getName()+", "+locale.getDisplayCountry());

               }
               else {
                   Toast.makeText(getActivity(), "Error No City Try Again", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<WeatherResult> call, Throwable t) {

               Toast.makeText(getActivity(), "Error No Data", Toast.LENGTH_SHORT).show();

           }
       });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_weather,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.weather_seach:
                SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
                final SearchView searchView = (SearchView) item.getActionView();
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
                searchView.setQueryHint("Search City Weather...");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        if(s.length() > 2){
                            getCurrentWeather(s);
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
                item.getIcon().setVisible(false,false);
                return true;
            case R.id.action_setting_weather:
                Intent intent = new Intent((Activity)getActivity(),DetailWeatherActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
