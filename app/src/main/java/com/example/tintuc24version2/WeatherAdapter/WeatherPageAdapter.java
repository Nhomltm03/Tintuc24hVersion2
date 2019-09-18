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
import com.example.tintuc24version2.WeatherModels.WeatherData;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class WeatherPageAdapter extends RecyclerView.Adapter<WeatherPageAdapter.WeatherViewHolder> {

    private Context context;
    private ArrayList<WeatherData> weatherDataList;

    public WeatherPageAdapter(Context context, ArrayList<WeatherData> weatherDataList) {
        this.context = context;
        this.weatherDataList = weatherDataList;
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
        Picasso.get().load(new StringBuilder("http://openweathermap.org/img/wn/").append(weatherDataList.get(position)
                .getIcon_status()).append("@2x.png").toString()).into(holder.iconWeather);
        holder.txt_detailStatus.setText(new StringBuffer(weatherDataList.get(position).Description_status));
        holder.txt_detailTime.setText(weatherDataList.get(position).DateTime);
        holder.txt_detailTemp.setText(weatherDataList.get(position).Temp_Min+"°C - "+weatherDataList.get(position).Temp_Max+"°C");
        holder.txt_detailWind.setText(weatherDataList.get(position).Wind+" m/s");
        holder.txt_detailHumidity.setText(weatherDataList.get(position).Humidity+"%");
        holder.txt_detailPressure.setText(weatherDataList.get(position).Pressure+" hPa");

    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {

        TextView txt_detailStatus ,txt_detailTemp, txt_detailWind , txt_detailHumidity, txt_detailPressure;
        TextView txt_detailTime;
        ImageView iconWeather;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_detailStatus =(TextView) itemView.findViewById(R.id.tv_detailStatus);
            txt_detailTime =(TextView) itemView.findViewById(R.id.tv_timeUpdate);
            txt_detailTemp =(TextView) itemView.findViewById(R.id.tv_detailTemp);
            txt_detailWind =(TextView) itemView.findViewById(R.id.tv_detailWind);
            txt_detailHumidity =(TextView) itemView.findViewById(R.id.tv_detailHumidity);
            txt_detailPressure =(TextView) itemView.findViewById(R.id.tv_detailPressure);
            iconWeather =(ImageView)itemView.findViewById(R.id.iconWeatherDetail);
        }
    }
}
