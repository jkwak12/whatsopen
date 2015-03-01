package com.fkoc.hackillinois.whatsopen;

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

    public double[] getLatLng() {
        double[] res = {latitude, longitude};
        return res;
    }
}
