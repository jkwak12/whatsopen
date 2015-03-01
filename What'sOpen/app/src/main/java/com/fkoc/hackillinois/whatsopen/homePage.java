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
    public boolean foodB, libB, cafB, buildB, gymB;

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
        buildingBox = (CheckBox) findViewById(R.id.buildingBox);

        enter = (Button) findViewById(R.id.enter);


        enter.setOnClickListener(new OnClickListener(){

            //Run when button is clicked
            @Override
            public void onClick(View v) {

                foodB = foodBox.isChecked();
                cafB = (cafeBox.isChecked());
                gymB = (gymBox.isChecked());
                buildB = (buildingBox.isChecked());
                libB = (libraryBox.isChecked());
            }
        });

    }
}