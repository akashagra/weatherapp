package com.example.avma1997.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

/**
 * Created by Avma1997 on 11/6/2017.
 */

public class WeatherRecyclerAdapter extends RecyclerView.Adapter<WeatherRecyclerAdapter.WeatherViewHolder> {

    private Context mContext;

    private WeatherClickListener mListener;
    private ArrayList<Weather> mWeatherList;

    public interface WeatherClickListener {
        void onItemClick(View view, int position);
    }


    public WeatherRecyclerAdapter(Context context, ArrayList<Weather> weatherList, WeatherClickListener listener) {
        mContext = context;
        mWeatherList = weatherList;
        mListener = listener;
    }


    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.weather_item, parent, false);
        return new WeatherViewHolder(itemView, mListener);
    }

    public void onBindViewHolder(WeatherViewHolder holder, int position) {
        Weather weather = mWeatherList.get(position);
//        holder.titleTextView.setText(movie.title);
        //      Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342" + movie.poster_path).into(holder.posterImageView);
        holder.tempTextview.setText(Math.round(weather.weatherMainDetails.temp - 273) + "");
        holder.minTextview.setText("Min: " + Math.round(weather.weatherMainDetails.temp_min - 273) + "");
        holder.maxTextview.setText("Max: " + Math.round(weather.weatherMainDetails.temp_max - 273) + "");
        holder.dateTextView.setText("Date :  " + weather.dt_txt.substring(0, 10) + "");
        holder.windSpeedTextView.setText("Wind Speed :  " + weather.wind.speed + "kmph");
        holder.windDirectionTextView.setText("Wind Direction :" + weather.wind.deg);


    }

    public int getItemCount() {
        return mWeatherList.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tempTextview;
        TextView minTextview;
        TextView maxTextview;
        TextView dateTextView;
        TextView timetextView;
        TextView dayTextView;
        TextView windDirectionTextView;
        TextView windSpeedTextView;


        WeatherClickListener mWeatherClickListener;

        public WeatherViewHolder(View itemView, WeatherClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            mWeatherClickListener = listener;

            tempTextview = itemView.findViewById(R.id.temperature_textview);
            minTextview = itemView.findViewById(R.id.temp_min_textview);
            maxTextview = itemView.findViewById(R.id.temp_max_textview);
            dateTextView = itemView.findViewById(R.id.date_textview);
            timetextView = itemView.findViewById(R.id.time_text_view);
            windSpeedTextView = itemView.findViewById(R.id.wind_speed);
            windDirectionTextView = itemView.findViewById(R.id.wind_direction);
            dayTextView = itemView.findViewById(R.id.day_textview);

        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            int position = getAdapterPosition();
//            if (position != RecyclerView.NO_POSITION) {
//                if (id == R.id.weather_layout) {
//                    mWeatherClickListener.onItemClick(view, position);
//                }
//
//            }
//            final FoldingCell fc =view.findViewById(R.id.folding_cell);
//            fc.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    fc.toggle(false);
//                }
//            });
//        }


        }
    }
}



