package com.fkoc.hackillinois.whatsopen;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;


public class homePage extends ActionBarActivity {
    boolean food, cafe, library, building, gym;
    Button toMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Button toMap = (Button) findViewById(R.id.enter);

        toMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent goToMap = new Intent(homePage.this, MapsActivity.class);
                startActivity(goToMap);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeBooleans(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.foodBox:
                if (checked) {
                    food = true;
                } else {
                    food = false;
                }
                break;
            case R.id.cafeBox:
                if (checked) {
                    cafe = true;
                } else {
                }
                break;
            case R.id.libraryBox:
                if (checked) {
                    library = true;
                } else {
                }
                break;
            case R.id.buildingBox:
                if (checked) {
                    building = true;
                } else {
                }
                break;
            case R.id.gymBox:
                if (checked) {
                    gym = true;
                } else {
                }
                break;
        }
    }
}
