package com.example.tintuc24version2;
import android.annotation.SuppressLint;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    private static final String JSON_OBJECT_KEY_NAME = "name";
    private static final String JSON_OBJECT_KEY_SYS = "sys";
    private static final String JSON_OBJECT_KEY_COUNTRY = "country";
    private static final String JSON_OBJECT_KEY_MAIN = "main";
    private static final String JSON_OBJECT_KEY_TEMP = "temp";
    private static final String JSON_OBJECT_KEY_WEATHER = "weather";
    private static final String JSON_OBJECT_KEY_DESCRIPTION = "description";
    private static final String JSON_OBJECT_KEY_ICON = "icon";
    private static final String URL_CONVERT_ICON = "http://openweathermap.org/img/wn/";
    private static final String URL_CONVERT_ICON_TYPE = "@2x.png";
    private static final String EXTRA_CITY_NAME = "cityName";
    private static final String QUERY_HINT = "Search City Weather...";
    private static final String DEFAULT_CITY_NAME= "Hanoi";
    private String sendCityName;
    private TextView tvCity, tvTemp, tvStatus;
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

        tvCity = rootView.findViewById(R.id.tv_City);
        tvStatus= rootView.findViewById(R.id.tv_status);
        tvTemp = rootView.findViewById(R.id.tv_Temp);
        iconWeather = rootView.findViewById(R.id.iconStatusWeather);
        loadCurrentWeatherData(DEFAULT_CITY_NAME);
        return rootView;
    }

    private void  loadCurrentWeatherData(String cityName){
        String queryUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric&appid=27a77fcad68bf8df89e0124c586f0d30";
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, queryUrl,
                new com.android.volley.Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Locale locale = new Locale("",jsonObject
                                    .getJSONObject(JSON_OBJECT_KEY_SYS)
                                    .getString(JSON_OBJECT_KEY_COUNTRY));
                            tvCity.setText(jsonObject
                                    .getString(JSON_OBJECT_KEY_NAME)+", "+locale.getDisplayCountry());
                            tvTemp.setText(jsonObject
                                    .getJSONObject(JSON_OBJECT_KEY_MAIN)
                                    .getString(JSON_OBJECT_KEY_TEMP)+"°C");
                            JSONObject jsonObjectWeather = jsonObject
                                    .getJSONArray(JSON_OBJECT_KEY_WEATHER)
                                    .getJSONObject(0);
                            tvStatus.setText(jsonObjectWeather
                                    .getString(JSON_OBJECT_KEY_DESCRIPTION));
                            Picasso.get()
                                    .load(URL_CONVERT_ICON
                                            +jsonObjectWeather.getString(JSON_OBJECT_KEY_ICON)+ URL_CONVERT_ICON_TYPE)
                                    .into(iconWeather);

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
                SearchManager searchManager = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);
                final SearchView searchView = (SearchView) item.getActionView();
                assert searchManager != null;
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
                searchView.setQueryHint(QUERY_HINT);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        if(s.length() > 2){
                            loadCurrentWeatherData(s);
                            sendCityName = s;
                        }else {
                            sendCityName ="Hanoi";
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
                Intent intent = new Intent(getActivity(),DetailWeatherActivity.class);
                intent.putExtra(EXTRA_CITY_NAME,sendCityName);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
