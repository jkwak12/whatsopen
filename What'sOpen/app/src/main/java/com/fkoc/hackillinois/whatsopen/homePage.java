package com.fkoc.hackillinois.whatsopen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

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

        OnClickListener btn = new OnClickListener() {

            //Run when button is clicked
            @Override
            public void onClick(View v) {
                foodB = foodBox.isChecked();
                cafB = cafeBox.isChecked();
                gymB = gymBox.isChecked();
                buildB = buildingBox.isChecked();
                libB = libraryBox.isChecked();

                Intent goToMap = new Intent(homePage.this, MapsActivity.class);
                goToMap.putExtra("foodB",foodB);
                goToMap.putExtra("cafB",cafB);
                goToMap.putExtra("gymB",gymB);
                goToMap.putExtra("buildB",buildB);
                goToMap.putExtra("libB",libB);

                startActivity(goToMap);
            }
        };

        OnClickListener cbx = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do Nothing
            }
        };

        enter.setOnClickListener(btn);
        foodBox.setOnClickListener(cbx);
        cafeBox.setOnClickListener(cbx);
        gymBox.setOnClickListener(cbx);
        buildingBox.setOnClickListener(cbx);
        libraryBox.setOnClickListener(cbx);
    }
}