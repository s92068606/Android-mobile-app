package com.example.myfoodapp.activities.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

import com.example.myfoodapp.R;

public class MainActivity extends AppCompatActivity {
    private Button btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button in the layout
        btnMap = findViewById(R.id.mapbt);
        btnMap.setOnClickListener(view -> {
            // Launch the Dashboard activity
            Intent intent = new Intent(MainActivity.this, Dashboard.class);
            startActivity(intent);
        });
        //Check if location permission is granted
        if(isLocationPermissionGranted()){
            // Create a new instance of the Map_Fragment and replace the frame layout with it
            Fragment fragment=new Map_Fragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
        }
        else {
            // Request location permission if it is not granted
            requestLocationPermission();
        }
    }
    //check the location permission granted or not
    private boolean isLocationPermissionGranted(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            return true;}
        else {
            return false;}
    }

    //request location permission
    private void requestLocationPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},101);
    }
}