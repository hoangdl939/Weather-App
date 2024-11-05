package com.example.weatherapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.weatherapp.model.HourlyWeather;
import com.example.weatherapp.service.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

public class NotificationSettingActivity extends AppCompatActivity {

    private ApiService apiService;
    private double latitude;
    private double longitude;

    private TextView timePickTextView, longitudeTextView, latitudeTextView, weatherTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);

        // Nhận tọa độ từ MainActivity
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0.0);
        longitude = intent.getDoubleExtra("longitude", 0.0);


        apiService = new ApiService(this);

        // Tham chiếu các TextView
        timePickTextView = findViewById(R.id.timePick_tv);
        longitudeTextView = findViewById(R.id.longitude_tv);
        latitudeTextView = findViewById(R.id.lattitude_tv);
        weatherTextView = findViewById(R.id.weather_tv);

        // Hiển thị tọa độ
        longitudeTextView.setText("Kinh độ: " + longitude);
        latitudeTextView.setText("Vĩ độ: " + latitude);


        // Cập nhật thời tiết
        updateWeatherInfo();

        Button selectTimeButton = findViewById(R.id.select_time_button);
        selectTimeButton.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(NotificationSettingActivity.this,
                    (TimePicker timePicker, int selectedHour, int selectedMinute) -> {
                        SharedPreferences preferences = getSharedPreferences("WeatherApp", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putInt("notification_hour", selectedHour);
                        editor.putInt("notification_minute", selectedMinute);
                        editor.apply();


                        // Hiển thị thời gian đã chọn lên TextView
                        timePickTextView.setText("Thời gian thông báo: " + selectedHour + ":" + String.format("%02d", selectedMinute));

                        // Lên lịch thông báo
                        scheduleDailyNotification(selectedHour, selectedMinute);
                        Toast.makeText(NotificationSettingActivity.this, "Đã cài đặt thời gian thông báo", Toast.LENGTH_SHORT).show();
                    }, hour, minute, true);
            timePickerDialog.show();
        });
    }

    private void scheduleDailyNotification(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, WeatherNotificationReceiver.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.RTC_WAKEUP, pendingIntent);


        }
    }

    private void updateWeatherInfo() {
        apiService.getHourlyWeather(latitude, longitude, response -> {
            try {
                JSONArray hourlyDataArray = new JSONObject(response).getJSONArray("list");
                List<HourlyWeather> hourlyWeathers = HourlyWeather.fromJsonArray(hourlyDataArray, 1);
                HourlyWeather firstHourlyWeather = hourlyWeathers.get(0);

                String temperature = String.format("%.1f°C", firstHourlyWeather.getTemperature() - 273.15);
                String description = firstHourlyWeather.getDescription();

                // Hiển thị thông tin thời tiết
                weatherTextView.setText("Thời tiết hiện tại: " + temperature + ", " + description);

            } catch (JSONException e) {
                e.printStackTrace();
                weatherTextView.setText("Không thể tải thông tin thời tiết");
            }
        });
    }

}