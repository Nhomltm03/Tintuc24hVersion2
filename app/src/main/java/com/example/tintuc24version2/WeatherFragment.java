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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private String sendCityname= "Hanoi";
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
        loadCurrentWeatherData("Hanoi");
        return rootView;
    }



    public  void  loadCurrentWeatherData(String cityName){
        String queryUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid=27a77fcad68bf8df89e0124c586f0d30";
        RequestQueue requestQueue = Volley.newRequestQueue((Activity)getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, queryUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String cityName = jsonObject.getString("name");
                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            Locale locale = new Locale("",country);
                            String fullname = cityName+", "+locale.getDisplayCountry();
                            textViewCity.setText(fullname);
                            JSONObject jsonObjectTemp = jsonObject.getJSONObject("main");
                            String temp = jsonObjectTemp.getString("temp");
                            textViewTemp.setText(temp+"°C");
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("description");
                            String icon = jsonObjectWeather.getString("icon");
                            textViewStatus.setText(status);
                            Picasso.get()
                                    .load("http://openweathermap.org/img/wn/"+icon+"@2x.png").into(iconWeather);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error No Data", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(stringRequest);

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
                            loadCurrentWeatherData(s);
                            sendCityname = s;
                        }else {
                            sendCityname ="Hanoi";
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
                intent.putExtra("cityName",sendCityname);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
