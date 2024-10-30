package com.example.weather_app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.weather_app.model.DailyWeather;
import com.example.weather_app.model.HourlyWeather;

import java.util.List;

@Dao
public interface DailyWeatherDao {
    @Query("SELECT * from DailyWeather where location_id = :locationId")
    List<DailyWeather> getByLocationId(long locationId);

    @Insert
    void insert(List<DailyWeather> weatherList);

    @Query("DELETE from DailyWeather where location_id = :locationId")
    void deleteByLocationId(long locationId);
}
