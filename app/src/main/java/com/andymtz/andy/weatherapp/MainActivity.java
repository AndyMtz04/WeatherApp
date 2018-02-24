package com.andymtz.andy.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String ZIP_CODE = "edu.txtstate.weatherapp.ZIP_CODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, WeatherActivity.class);
        final EditText txtZipCode = findViewById(R.id.txtZipCode);
        Button btnFetchWeather = findViewById(R.id.btnFetchWeather);

        btnFetchWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra(ZIP_CODE, txtZipCode.getText().toString());
                startActivity(intent);
            }
        });

        txtZipCode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                txtZipCode.setText("");
                return false;
            }
        });
    }
}
