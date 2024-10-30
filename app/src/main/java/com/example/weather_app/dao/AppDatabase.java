package com.example.weather_app.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.weather_app.model.DailyWeather;
import com.example.weather_app.model.HourlyWeather;
import com.example.weather_app.model.AppLocation;

@Database(entities = {DailyWeather.class, HourlyWeather.class, AppLocation.class}, version = 2, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DailyWeatherDao dailyWeatherDao();
    public abstract HourlyWeatherDao hourlyWeatherDao();
    public abstract LocationDao locationDao();
}