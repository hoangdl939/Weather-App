package com.example.weatherapp.service;

import static com.example.weatherapp.util.DefaultConfig.NUMBER_OF_DAYS;
import static com.example.weatherapp.util.DefaultConfig.NUMBER_OF_HOURS;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.model.AppLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiService {
    private Context context;
    private final String apiKey = "b4408e48ed0b9adc52d854b0e7a1a67f";
    private final String hourlyWeatherUrl = "https://api.openweathermap.org/data/2.5/forecast";
    private final String dailyWeatherUrl = "https://api.openweathermap.org/data/2.5/forecast";
    private final String geocodingUrl = "http://api.openweathermap.org/geo/1.0/direct";

    public ApiService(Context context) {
        this.context = context;
    }

    public void getHourlyWeather(double latitude, double longitude,
        Response.Listener<String> responseListener) {
        String requestUrl =
                hourlyWeatherUrl + "?lat=" + latitude + "&lon=" + longitude + "&cnt=" + NUMBER_OF_HOURS
                        + "&appid=" + apiKey + "&lang=vi";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl,
            responseListener,
            error -> Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}