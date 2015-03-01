package com.fkoc.hackillinois.whatsopen;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class homePage extends Activity {
    private CheckBox foodBox, libraryBox, cafeBox, buildingBox, gymBox;
    private Button enter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        foodBox = (CheckBox) findViewById(R.id.foodBox);
        libraryBox = (CheckBox) findViewById(R.id.libraryBox);
        cafeBox = (CheckBox) findViewById(R.id.cafeBox);
        gymBox = (CheckBox) findViewById(R.id.gymBox);
        libraryBox = (CheckBox) findViewById(R.id.libraryBox);

        enter = (Button) findViewById(R.id.enter);


        enter.setOnClickListener(new OnClickListener(){

            //Run when button is clicked
            @Override
            public void onClick(View v) {

                StringBuffer result = new StringBuffer();
                result.append("Food check : ").append(foodBox.isChecked());
                result.append("\nCoffee check :").append(cafeBox.isChecked());
                result.append("\nGym check : ").append(gymBox.isChecked());
                result.append("\nBuildings check : ").append(buildingBox.isChecked());
                result.append("\nLibrary check :").append(libraryBox.isChecked());

                Toast.makeText(homePage.this, result.toString(),
                        Toast.LENGTH_LONG).show();

            }
        });

    }
}