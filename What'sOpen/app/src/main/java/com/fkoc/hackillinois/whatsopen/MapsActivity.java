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

import java.util.*;
import java.sql.Time;
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
                    .field("category_ids").equal(347)
                    .sortAsc("$distance")
                    .only("name", "latitude", "longitude", "hours");

            task.execute(restaurant_query);
        }
        if (cafB) {
            Query cafe_query = new Query()
                    .within(new Circle(s_latitude, s_longitude, search_radius))
                    .field("category_ids").equal(342)
                    .sortAsc("$distance")
                    .only("name", "latitude", "longitude", "hours");

            task.execute(cafe_query);
        }
        if (gymB) {
            ArrayList<Spot> gyms = new ArrayList();
            gyms.add(new Spot("CRCE", 40.104673, -88.221772, 390, 1439));
            gyms.add(new Spot("ARC", 40.100763, -88.236044, 360, 1439));
            gyms.add(new Spot("UI Ice Arena", 40.105849, -88.232461, 1170, 1290));
            gyms.add(new Spot("Bowling", 40.109349, -88.227177, 690, 1410));
            gyms.add(new Spot("Billiards", 40.109349, -88.227177, 690, 1410));
            gyms.add(new Spot("YMCA", 40.106602, -88.229154, 540, 960));

            for (Spot s : gyms) {
                MarkerOptions mo = new MarkerOptions()
                .title(s.getName())
                .position(s.getLatLng());
                if (s.isOpen()) {
                    mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.greentri));
                } else {
                    mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.redtriangle1));
                }
                mMap.addMarker(mo);
            }
        }
        if (buildB) {
            ArrayList<Spot> buildings = new ArrayList();
            buildings.add(new Spot("Loomis Lab", 40.110806, -88.223225, 420, 1260));
            buildings.add(new Spot("ECE Building", 40.114835, -88.228050, 420, 1200));
            buildings.add(new Spot("Siebel", 40.113877, -88.224894, 420, 1200));
            buildings.add(new Spot("Digital Computer Laboratory", 40.113163, -88.226654, 480, 1439));
            buildings.add(new Spot("Psychology Building", 40.107520, -88.229962, 420, 1200));
            buildings.add(new Spot("Mechanical Eng Lab", 40.111787, -88.226214, 480, 1439));
            buildings.add(new Spot("Engineering Hall", 40.110885, -88.226847, 0, 1439));
            buildings.add(new Spot("Altgeld Hall", 40.109297, -88.228341, 540, 1200));
            buildings.add(new Spot("McKinley Health Center", 40.102878, -88.219802, 480, 1020));
            buildings.add(new Spot("Illini Union Bookstore", 40.108302, -88.229211, 420, 1380));
            buildings.add(new Spot("TIS College Bookstore", 40.109652, -88.230501, 530, 1080));
            for (Spot s : buildings) {
                MarkerOptions mo = new MarkerOptions()
                        .title(s.getName())
                        .position(s.getLatLng());
                if (s.isOpen()) {
                    mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.greentri));
                } else {
                    mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.redtriangle1));
                }
                mMap.addMarker(mo);
            }
        }
        if (libB) {
            ArrayList<Spot> libraries = new ArrayList();
            libraries.add(new Spot("Grainger Library", 40.112504, -88.226904, 480, 1439));
            libraries.add(new Spot("Math Library", 40.109420, -88.228277, 540, 1200));
            libraries.add(new Spot("Chem Library", 40.108411, -88.226035, 510, 1260));
            //libraries.add(new Spot("Health Sciences Library", 40.108575, -88.226035, o, c));
            //libraries.add(new Spot("Communications Library", 40.105628, -88.228158, o, c));
            //libraries.add(new Spot("Classics Library", 40.104549, -88.228399, 540, 1140));
            libraries.add(new Spot("English Library", 40.104376, -88.228753, 540, 1140));
            //libraries.add(new Spot("Archives", 40.104610, -88.228850, o, c));
            libraries.add(new Spot("Undergraduate Library", 40.104643, -88.227182, 0, 1439));
            libraries.add(new Spot("ACES Library", 40.102813, -88.225181, 510, 1600));
            libraries.add(new Spot("Ricker Architecture and Art Library", 40.103432, -88.229095, 510, 1320));
            //libraries.add(new Spot("Agricultural Library", 40.102850, -88.225025, o, c));
            libraries.add(new Spot("Music Library", 40.106539, -88.223051, 510, 1600));
            //libraries.add(new Spot("Allen Hall Library", 40.104080, -88.220887, o, c));
            libraries.add(new Spot("Law Library", 40.100980, -88.231991, 480, 1439));
            libraries.add(new Spot("Map and Geography Library", 40.101209, -88.229084, 510, 1020));
            //libraries.add(new Spot("Ike Library", 40.103950, -88.235264, o, c));
            for (Spot s : libraries) {
                MarkerOptions mo = new MarkerOptions()
                        .title(s.getName())
                        .position(s.getLatLng());
                if (s.isOpen()) {
                    mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.greentri));
                } else {
                    mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.redtriangle1));
                }
                mMap.addMarker(mo);
            }
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
                            mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.greentri));
                        } else {
                            mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.redtriangle1));
                        }
                        mMap.addMarker(mo);
                    } catch (NullPointerException e) {
                        //No business hour information available
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

    // determines from JSON if place is currently open
    boolean isOpen(JSONObject hours) {
        String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};
        try {
            JSONArray times_1 = hours.getJSONArray(days[Calendar.DAY_OF_WEEK - 1]);
            JSONArray times = times_1.getJSONArray(0);
            int currentHour = new Time(System.currentTimeMillis()).getHours();
            int currentMinute = new Time(System.currentTimeMillis()).getMinutes();
            int currentTime = currentHour * 60 + currentMinute;
            int open_t = Integer.parseInt(times.getString(0).split(":")[0]) * 60 + Integer.parseInt(times.getString(0).split(":")[1]);
            int close_t = Integer.parseInt(times.getString(1).split(":")[0]) * 60 + Integer.parseInt(times.getString(1).split(":")[1]);
            if (close_t > open_t) {
                if (currentTime > open_t && currentTime < close_t) {
                    return true;
                }
            } else {
                if ((currentTime > open_t && currentTime > close_t) || (currentTime < open_t && currentTime > close_t)) {
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
