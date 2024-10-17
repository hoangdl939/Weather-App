package com.example.weatherapp.adapter;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.android.volley.Response;
        import com.example.weatherapp.R;
        import com.example.weatherapp.model.AppLocation;
        import com.example.weatherapp.service.ApiService;

        import org.json.JSONArray;
        import org.json.JSONException;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Locale;

public class LocationListAdapter extends BaseAdapter {
    private List<AppLocation> locationList = null;

    public LocationListAdapter(List<AppLocation> animalNamesList) {
 
        this.locationList = animalNamesList;
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        locationList.clear();
        if (charText.length() == 0) {
            locationList.addAll(arraylistBlank);
        } else {
            for (AppLocation wp : appLocations) {
                locationList.add(wp);
            }
        }
    }


}
