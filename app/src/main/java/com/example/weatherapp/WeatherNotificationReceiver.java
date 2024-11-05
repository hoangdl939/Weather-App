package com.example.weatherapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.weatherapp.model.HourlyWeather;
import com.example.weatherapp.service.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class WeatherNotificationReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        double latitude = intent.getDoubleExtra("latitude", 0.0);
        double longitude = intent.getDoubleExtra("longitude", 0.0);

        ApiService apiService = new ApiService(context);
        apiService.getHourlyWeather(latitude, longitude, response -> {
            try {
                JSONArray hourlyDataArray = new JSONObject(response).getJSONArray("list");
                List<HourlyWeather> hourlyWeathers = HourlyWeather.fromJsonArray(hourlyDataArray, 1);
                HourlyWeather firstHourlyWeather = hourlyWeathers.get(0);

                String temperature = String.format("%.1f°C", firstHourlyWeather.getTemperature() - 273.15); // Chuyển từ Kelvin sang Celsius
                String humidity = "Độ ẩm: " + firstHourlyWeather.getHumidity() + "%";
                String description = firstHourlyWeather.getDescription();

                sendNotification(context, "Thời tiết hôm nay ", "Trời có " + description + "\n" + "Nhiệt độ: " + temperature  + ", " + humidity);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    private void sendNotification(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "weather_channel";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Weather Notifications", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.weather_icon)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());
    }
}