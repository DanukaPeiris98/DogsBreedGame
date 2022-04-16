package com.example.gameofdogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    Switch timer;
    boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = (Switch) findViewById(R.id.switch1);
        timer.setChecked(false);
    }

    public void identifyTheBreed(View view) {
        Intent int1 = new Intent(this, IdentifyTheBreed.class);
        int1.putExtra("Timerstatus", status);
        startActivity(int1);
    }

    public void identifyTheDog(View view) {
        Intent int2 = new Intent(this, IdentifyTheDog.class);
        int2.putExtra("Timerstatus", status);
        startActivity(int2);
    }

    public void searchDogBreed(View view) {
        Intent int3 = new Intent(this, SearchDogBreeds.class);
        startActivity(int3);
    }

    public void setStatus(View view) {
        //get switch status
        status = !status;
        timer.setChecked(status);
    }
}
