package com.fkoc.hackillinois.whatsopen;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.factual.driver.Circle;
import com.factual.driver.Factual;
import com.factual.driver.Query;
import com.factual.driver.ReadResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.api.client.util.Lists;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    protected Factual factual = new Factual("Yx635b7JwNAZTJDlXVyZXbOImAl6mIrgBRbOI9Bb", "4XJBrrwnXnRQ4X27HSJMxQcg1wbiPpzoPg5YmxIv");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        // Enable Location Tracking
        mMap.setMyLocationEnabled(true);

        FactualRetrievalTask task = new FactualRetrievalTask();

        double s_latitude = 40.112475;
        double s_longitude = -88.226863;
        int search_radius = 2000;
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            s_latitude = location.getLatitude();
            s_longitude = location.getLongitude();
        }

        //retrieve user choices
        Bundle extras = getIntent().getExtras();
        boolean foodB = extras.getBoolean("foodB");
        boolean cafB = extras.getBoolean("cafB");
        boolean gymB = extras.getBoolean("gymB");
        boolean buildB = extras.getBoolean("buildB");
        boolean libB = extras.getBoolean("libB");

        if (foodB) {
            Query restaurant_query = new Query()
                    .within(new Circle(s_latitude, s_longitude, search_radius))
                    .sortAsc("$distance")
                    .only("name", "latitude", "longitude", "rating", "hours");

            task.execute(restaurant_query);
        }
        if (cafB) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(s_latitude, s_longitude)));
        }
        if (gymB) {
            //Nothing for now
        }
        if (buildB) {
            //Nothing for now
        }
        if (libB) {
            //Nothing for now
        }
    }

    protected class FactualRetrievalTask extends AsyncTask<Query, Integer, List<ReadResponse>> {

        @Override
        protected List<ReadResponse> doInBackground(Query... params) {
            List<ReadResponse> results = Lists.newArrayList();
            for (Query q : params) {
                results.add(factual.fetch("restaurants-us", q));
            }
            return results;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected void onPostExecute(List<ReadResponse> responses) {
            for (ReadResponse response : responses) {
                for (Map<String, Object> restaurant : response.getData()) {

                    String name = (String) restaurant.get("name");
                    double latitude = (double) restaurant.get("latitude");
                    double longitude = (double) restaurant.get("longitude");
                    MarkerOptions mo = new MarkerOptions()
                            .position(new LatLng(latitude, longitude))
                            .alpha(0.8f)
                            .title(name);
                    try {
                        JSONObject hours = (JSONObject) restaurant.get("hours");
                        if (isOpen(hours)) {
                            mo.icon(BitmapDescriptorFactory.defaultMarker());
                        } else {
                            mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.redtriangle1));
                        }
                        mo.icon(BitmapDescriptorFactory.defaultMarker());
                        mo.snippet(hours.toString());
                        mMap.addMarker(mo);
                    } catch (NullPointerException e) {
                        mo.snippet("No hours available");
                    }
                    //Number rating = (Number) restaurant.get("rating");
                    //boolean open24 = (boolean) restaurant.get("open_24hrs");
                    //boolean deliver = (boolean) restaurant.get("meal_deliver");
                    //boolean takeout = (boolean) restaurant.get("meal_takeout");
                }
            }
        }
    }

    // JSON if store is open
    // JSONObject is named hours
    // assuming datetime is imported


    boolean isOpen(JSONObject hours) {
        String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
        try {
            JSONArray times_1 = hours.getJSONArray(days[Calendar.DAY_OF_WEEK - 1]);
            JSONArray times = times_1.getJSONArray(0);
            Calendar now = Calendar.getInstance();
            int currentTime = now.HOUR_OF_DAY * 60 + now.MINUTE;
            int open_t = Integer.parseInt(times.getString(0).split(":")[0]) * 60 + Integer.parseInt(times.getString(0).split(":")[1]);
            int close_t = Integer.parseInt(times.getString(1).split(":")[0]) * 60 + Integer.parseInt(times.getString(1).split(":")[1]);
            if (close_t > open_t) {
                if (currentTime > open_t & currentTime < close_t) {
                    return true;
                }
            } else {
                if (currentTime > open_t & currentTime > close_t) {
                    return true;
                }
            }
        } catch (JSONException e) {
            //this day could not be found
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        LatLng GraingerLatLng = new LatLng(40.112475, -88.226863);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GraingerLatLng, 16));
    }
}
