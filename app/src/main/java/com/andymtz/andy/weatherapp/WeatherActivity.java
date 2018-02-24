package com.andymtz.andy.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;

public class WeatherActivity extends AppCompatActivity {

    private final String APP_ID= "";
    private String endpoint = "http://api.openweathermap.org/data/2.5/weather?zip=%s,us&units=imperial&APPID=%s";
    private RequestQueue requestQueue;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        Intent intent = getIntent();
        final String strZipCode = intent.getStringExtra(MainActivity.ZIP_CODE);

        requestQueue = Volley.newRequestQueue(this);
        gson = new GsonBuilder().create();

        fetchWeather(strZipCode);

        Button btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchWeather(strZipCode);
            }
        });
    }

    private void fetchWeather(String zipCode) {
        StringRequest request = new StringRequest(Request.Method.GET,
                String.format(endpoint, zipCode, APP_ID), onPostsLoaded, onPostsError);

        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            displayWeather(response);

            Log.i("WeatherActivity", response);
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            displayError();

            Log.e("WeatherActivity", error.toString());
        }
    };

    private void displayError() {
        TextView txtTemp = findViewById(R.id.txtTemp);
        TextView txtDescription = findViewById(R.id.txtDescription);
        TextView txtTitleWeather = findViewById(R.id.txtTitleWeather);

        txtTemp.setText("Temp: Not Found");
        txtDescription.setText("Description: Not Found");
        txtTitleWeather.setText("Weather Not Found");
    }

    private void displayWeather(String response) {
        DecimalFormat df = new DecimalFormat("### F");
        Forecast forecast = gson.fromJson(response, Forecast.class);
        TextView txtTemp = findViewById(R.id.txtTemp);
        TextView txtDescription = findViewById(R.id.txtDescription);
        TextView txtTitleWeather = findViewById(R.id.txtTitleWeather);
        ImageView imgWeather = findViewById(R.id.imgWeather);

        double dblTemp = forecast.getMain().getTemp();
        String strDescription = forecast.getWeather()[0].getDescription();

        txtTemp.setText("Temp: " + df.format(dblTemp));
        txtDescription.setText("Description: " + strDescription);
        txtTitleWeather.setText(forecast.getName() + " Weather");
        int intIcon = selectIcon(forecast.getWeather()[0].getIcon());
        imgWeather.setImageDrawable(getResources().getDrawable(intIcon));

    }

    private int selectIcon(String iconID) {

        int icon;
        switch (iconID.substring(0, 2)) {
            case "02":
                icon = R.drawable.few_clouds;
                break;
            case "03":
                icon = R.drawable.scattered_clouds;
                break;
            case "04":
                icon = R.drawable.scattered_clouds;
                break;
            case "09":
                icon = R.drawable.shower_rain;
                break;
            case "10":
                icon = R.drawable.rain;
                break;
            case "11":
                icon = R.drawable.tunderstorm;
                break;
            case "13":
                icon = R.drawable.snow;
                break;
            case "50":
                icon = R.drawable.mist;
                break;
            default:
                icon = R.drawable.clear_sky_day;
        }

        return icon;
    }
}
