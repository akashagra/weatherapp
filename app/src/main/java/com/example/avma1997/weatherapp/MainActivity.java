package com.example.avma1997.weatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<Weather> weatherList;
    FusedLocationProviderClient mFusedLocationProviderClient;
    WeatherRecyclerAdapter mAdapter;
    RecyclerView mRecyclerView;
    LocationRequest mLocationRequest;
    DividerItemDecoration decoration;
    LocationCallback mLocationCallback;
    boolean mRequestingLocationUpdates;
    WeatherListItemClick mListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        weatherList=new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new WeatherRecyclerAdapter(MainActivity.this, weatherList, new WeatherRecyclerAdapter.WeatherClickListener() {
            public void onItemClick(View view, int position) {
                if (mListener != null) {
                    mListener.onListItemClicked(weatherList.get(position));
                }
            }


        });


        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());



        // api key: a377f010b15396517096c86fe16a0642
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        5);


            } else {
                accessLocation();
            }

        } else {
            accessLocation();
        }

    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 5: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {         // If request is cancelled, the result arrays are empty.
                    accessLocation();
                } else {
                    finish();
                }

            }

        }
    }


    public void accessLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            //Toast.makeText(MainActivity.this,
                                //    "Lat: " + location.getLatitude() + " Long: " + location.getLongitude()
                                  //  , Toast.LENGTH_LONG).show();
                            double latitude=location.getLatitude();
                            double longitude=location.getLongitude();
                            fetchWeatherForecast( latitude,longitude );


                        }
                    }

                });



    }

    private void fetchWeatherForecast(double latitude, double longitude) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<WeatherResponse> call = apiInterface.getWeatherList(latitude+"",longitude+"","a377f010b15396517096c86fe16a0642");
        // enqueue function takes place on different thread and is asynchronous
        call.enqueue(new Callback<WeatherResponse>() {

            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Log.i("reach", "aa raha h");
                WeatherResponse weatherResponse = response.body();
                final ArrayList<Weather> weatherArrayList = weatherResponse.getWeatherList();

                //String t = movieArrayList.get(0).title;

                //Log.i("mytag", t);
                onDownloadComplete(weatherArrayList);
               // MovieDatabase db = MovieDatabase.getInstancePopular(getContext());
              //  final MovieDao movieDao = db.movieDao();
                //new AsyncTask<Void, Void, Void>() {
                  //  protected Void doInBackground(Void... voids) {
                    //    movieDao.insertAll(movieArrayList.toArray( new Moviedb[movieArrayList.size()]));
//                        return null;

                }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {

            }

        });



           // @Override
          //  public void onFailure(Call<WeatherResponse> call, Throwable t) {

            //}
       // });




    }



    public void onDownloadComplete(ArrayList<Weather> weatherArrayList) {
        this.weatherList.clear();
        // Algorithm
        Weather w=weatherArrayList.get(0);
        String date=w.dt_txt;
        int i;
        for( i=1;i<weatherArrayList.size();i++){
           if(weatherArrayList.get(i).dt_txt.substring(0,10).equals(date.substring(0,10)))
           {
               continue;
           }
           else{
               break;
           }

        }
        for(int j=i;j<weatherArrayList.size();j=j+8)
        {
            Weather weather=weatherArrayList.get(j);


            double max=weather.weatherMainDetails.temp_max;
            double min=weather.weatherMainDetails.temp_min;
            for(int k=j+1;k<j+8&&k<weatherArrayList.size();k++){
                if(max<weatherArrayList.get(k).weatherMainDetails.temp_max)
                {
                    max=weatherArrayList.get(k).weatherMainDetails.temp_max;
                    weather.weatherMainDetails.temp_max=max;
                }
                if(min>weatherArrayList.get(k).weatherMainDetails.temp_min)
                {
                    min=weatherArrayList.get(k).weatherMainDetails.temp_min;
                    weather.weatherMainDetails.temp_min=min;
                }

            }
            weather.weatherMainDetails.temp=(weather.weatherMainDetails.temp_max+weather.weatherMainDetails.temp_min)/2;
        this.weatherList.add(weather);
        }
        Log.i("temp",weatherList.get(0).weatherMainDetails.temp+"");
        if((weatherList.get(0).weatherMainDetails.temp-273)<15)
        {
            View backgroundImage=findViewById(R.id.main_view);
            backgroundImage.setBackgroundResource(R.drawable.cold);
            Drawable background = backgroundImage.getBackground();
            background.setAlpha(20);

        }
        else if((weatherList.get(0).weatherMainDetails.temp-273)<30)
        {
            View backgroundImage=findViewById(R.id.main_view);
            backgroundImage.setBackgroundResource(R.drawable.pleasant);
            Drawable background = backgroundImage.getBackground();
            background.setAlpha(20);

        }
        else
        {
            View backgroundImage=findViewById(R.id.main_view);
            backgroundImage.setBackgroundResource(R.drawable.sunny);
            Drawable background = backgroundImage.getBackground();
            background.setAlpha(20);
        }
        //this.weatherList.addAll(weatherArrayList);

        mAdapter.notifyDataSetChanged();

    }









    protected void onPause() {
        super.onPause();
        if(mRequestingLocationUpdates){
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
            mRequestingLocationUpdates = false;
        }
    }


}
interface WeatherListItemClick{
    void onListItemClicked(Weather w);
}

