package com.example.tintuc24version2;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class NextDayWeatherFragment extends Fragment {

public static  final String URL =
        "http://api.openweathermap.org/data/2.5/forecast?q=hanoi&units=metric&cnt=4&appid=27a77fcad68bf8df89e0124c586f0d30";
    public NextDayWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_next_day_weather, container, false);
    }


}
