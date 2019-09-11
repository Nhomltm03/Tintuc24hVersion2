package com.example.tintuc24version2.WeatherAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tintuc24version2.R;
import com.example.tintuc24version2.Utils;
import com.example.tintuc24version2.WeatherModels.WeatherForecaseResult;
import com.squareup.picasso.Picasso;


public class WeatherPageAdapter extends RecyclerView.Adapter<WeatherPageAdapter.WeatherViewHolder> {

    private Context context;
    private WeatherForecaseResult weatherForecaseResult;

    public WeatherPageAdapter(Context context, WeatherForecaseResult weatherForecaseResult) {
        this.context = context;
        this.weatherForecaseResult = weatherForecaseResult;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_weather,
                parent, false);

        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        Picasso.with(context).load(new StringBuilder("http://openweathermap.org/img/wn/").append(weatherForecaseResult.list.get(position)
                .weather.get(0).getIcon()).append("@2x.png").toString()).into(holder.iconWeather);
        holder.txt_detailStatus.setText(new StringBuffer(weatherForecaseResult.list.get(position).weather.get(0).getDescription()));

        holder.txt_detailCityName.setText(new StringBuffer(weatherForecaseResult.getCity().getName()).
                append(", ").append(weatherForecaseResult.getCity().getCountry()));
        holder.txt_detailTime.setText(weatherForecaseResult.list.get(position).dt_txt);

        holder.txtSunset.setText(Utils.DateToTimeFormat(weatherForecaseResult.getCity().getSunset()+""));
        holder.txtSunrise.setText(Utils.DateToTimeFormat(weatherForecaseResult.getCity().getSunrise()+""));


    }

    @Override
    public int getItemCount() {
        return weatherForecaseResult.getList().size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView txt_detailStatus , txt_detailCityName ,txt_detailTime, txtSunset, txtSunrise;
        ImageView iconWeather;



        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_detailTime = (TextView) itemView.findViewById(R.id.tv_detailTime);
            txt_detailCityName = (TextView)itemView.findViewById(R.id.tv_City);
            txt_detailStatus = (TextView)itemView.findViewById(R.id.tv_detailStatus);
            txtSunrise = (TextView)itemView.findViewById(R.id.textSunrise);
            txtSunset = (TextView)itemView.findViewById(R.id.textSunset);
            iconWeather =(ImageView)itemView.findViewById(R.id.weather_icon);
        }
    }
}
