package com.example.weatherapp.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.example.weatherapp.model.AppLocation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Util {

    private Util() {
    }

    public static String formatDate(String format, Date date, Integer offset) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        simpleDateFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        simpleDateFormat.setLenient(false);
        if (offset != null) {
            date.setTime(date.getTime() + offset * 1000);
        }
        return simpleDateFormat.format(date);
    }

    public static String getLocationNameByLatLng(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        String locationName = "Unknown Location";

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                // Bạn có thể lấy các phần khác nhau của địa chỉ
                locationName = address.getLocality() != null ? address.getLocality() : address.getFeatureName();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return locationName;
    }

    public static AppLocation getAppLocationByLatLng(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        AppLocation appLocation = null;

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String name = address.getLocality() != null ? address.getLocality() : address.getFeatureName();
                String state = address.getAdminArea(); // Tỉnh/Thành phố
                String country = address.getCountryName(); // Quốc gia

                appLocation = new AppLocation(name, latitude, longitude, state, country);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return appLocation;
    }
}
