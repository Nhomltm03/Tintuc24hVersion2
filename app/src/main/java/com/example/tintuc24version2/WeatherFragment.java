package com.example.tintuc24version2;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tintuc24version2.NewsApdapter.NewsAdapter;
import com.example.tintuc24version2.WeatherAPI.OpenWeatherInterface;
import com.example.tintuc24version2.WeatherAPI.WeatherApiClient;
import com.example.tintuc24version2.WeatherModels.Main;
import com.example.tintuc24version2.WeatherModels.Sys;
import com.example.tintuc24version2.WeatherModels.Weather;
import com.example.tintuc24version2.WeatherModels.WeatherResult;

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
    private Main main = new Main();
    private WeatherResult weatherResult = new WeatherResult();
    private Sys sys = new Sys();
public static  final  String API_KEY_WEATHER = "27a77fcad68bf8df89e0124c586f0d30";
public static  final  String UNITS = "metric";

private TextView textViewCity, textViewTemp, textViewStatus;
private Button btnSearch;
private EditText editTextSearch;

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
        btnSearch = (Button) rootView.findViewById(R.id.button_search);
        editTextSearch = (EditText) rootView.findViewById(R.id.editText_search);
        getCurrentWeather("hanoi");
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = editTextSearch.getText().toString();
                getCurrentWeather(keyword);
            }
        });

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
//                   if(!weathers.isEmpty()){
//                       weathers.clear();
//                   }
                   weathers = response.body().getWeather();
                   main = response.body().getMain();
                   weatherResult = response.body();
                   sys= response.body().getSys();
                   textViewStatus.setText(weathers.get(0).getMain());
                   textViewTemp.setText(main.getTemp()+"°C");
                   textViewCity.setText(weatherResult.getName()+" ,"+sys.getCountry());

               }
           }

           @Override
           public void onFailure(Call<WeatherResult> call, Throwable t) {

           }
       });

    }

}
