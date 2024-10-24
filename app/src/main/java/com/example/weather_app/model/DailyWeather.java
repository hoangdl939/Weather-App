package com.example.weatherapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Entity(foreignKeys = {
    @ForeignKey(entity = AppLocation.class, parentColumns = "id", childColumns = "location_id", onDelete = ForeignKey.CASCADE)})
public class DailyWeather {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long time;

    private long sunrise;

    private long sunset;

    @ColumnInfo(name = "min_temp")
    private double minTemp;

    @ColumnInfo(name = "max_temp")
    private double maxTemp;

    private String weather;

    private String description;

    @ColumnInfo(name = "location_id")
    private long locationId;

    @ColumnInfo(name = "last_update")
    private long lastUpdate;

    public DailyWeather() {
        Date now = new Date();
        lastUpdate = now.getTime();
    }

    public DailyWeather(long time, long sunrise, long sunset, double minTemp, double maxTemp,
        String weather, String description, long locationId) {
        this.time = time;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.weather = weather;
        this.description = description;
        this.locationId = locationId;
        Date now = new Date();
        lastUpdate = now.getTime();
    }
}
