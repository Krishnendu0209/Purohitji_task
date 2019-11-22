package com.example.weapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{

    public static String BaseUrl = "http://api.openweathermap.org/";
    public static String AppId = "f94f818433441cbc61215d2e632fac59";
    private EditText etCityName;
    private Button btGetWeather;
    private TextView tvWeatherDetails;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); // The UI elements getting initialised

        btGetWeather.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if (!etCityName.getText().toString().isEmpty())
                {
                    String cityName = etCityName.getText().toString();
                    getCurrentData(cityName);
                }
                else
                {
                    Toast emptyCity = Toast.makeText(MainActivity.this,"Empty City",Toast.LENGTH_SHORT);
                    emptyCity.show();
                }
            }
        });
    }

    public void init()
    {
        btGetWeather = findViewById(R.id.btnWeatherDetails);
        etCityName = findViewById(R.id.etCityName);
        tvWeatherDetails = findViewById(R.id.tvWeatherDetails);
    }
    void getCurrentData(String cityName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherServiceApi service = retrofit.create(WeatherServiceApi.class); // Create retrofit request
        Call<WeatherResponse> call = service.getCurrentWeatherData(cityName, AppId);
        call.enqueue(new Callback<WeatherResponse>()
        {
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    String stringBuilder = "Country : " +
                            weatherResponse.sys.country +
                            "\n\n" +
                            "City : " +
                            weatherResponse.name +
                            "\n\n" +
                            "Present Temperature : " +
                            weatherResponse.main.temp +
                            "\n\n" +
                            "Temperature(Min) : " +
                            weatherResponse.main.temp_min +
                            "\n\n" +
                            "Temperature(Max) : " +
                            weatherResponse.main.temp_max +
                            "\n\n" +
                            "Humidity : " +
                            weatherResponse.main.humidity +
                            "%\n\n" +
                            "Atmospheric Pressure : " +
                            weatherResponse.main.pressure +
                            "hPa\n\n" +
                            "Wind Speed :" +
                            weatherResponse.wind.speed;

                    Toast dataFetchSuccess = Toast.makeText(MainActivity.this,"Data Fetched!",Toast.LENGTH_SHORT);
                    dataFetchSuccess.show();

                    tvWeatherDetails.setText(stringBuilder);
                }
            }
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t)
            {
                etCityName.setText(t.getMessage());
            }
        });
    }

}