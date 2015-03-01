package com.fkoc.hackillinois.whatsopen;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Time;

/**
 * Created by ah299_000 on 2/28/2015.
 */
public class Spot {

    private String name;
    private int open_time, closing_time;
    private double latitude, longitude;

    Spot(String n, double lat, double lon, int o, int c) {
        name = n;
        latitude = lat;
        longitude = lon;
        open_time = o;
        closing_time = c;
    }

    public int[] getTimes() {
        int[] res = {open_time, closing_time};
        return res;
    }

    public String getName() {
        return name;
    }

    public LatLng getLatLng() {
        LatLng res = new LatLng(latitude, longitude);
        return res;
    }

    public boolean isOpen() {
        int currentHour = new Time(System.currentTimeMillis()).getHours();
        int currentMinute = new Time(System.currentTimeMillis()).getMinutes();
        int currentTime = currentHour * 60 + currentMinute;
        if (closing_time > open_time) {
            if (currentTime > open_time && currentTime < closing_time) {
                return true;
            }
        } else {
            if ((currentTime > open_time && currentTime > closing_time) || (currentTime < open_time && currentTime > closing_time)) {
                return true;
            }
        }
        return false;
    }
}
