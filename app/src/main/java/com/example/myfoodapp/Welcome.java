package com.example.myfoodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myfoodapp.activities.Login;
import com.example.myfoodapp.activities.admin.CategoryActivity;
import com.example.myfoodapp.activities.customer.NavigationDrawer;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //welcome
        Button welcome = findViewById(R.id.btnwelcome);
        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUserLoggedIn()) {
                    startActivity(new Intent(Welcome.this, NavigationDrawer.class));
                } else if (isAdminLoggedIn()) {
                    startActivity(new Intent(Welcome.this, CategoryActivity.class));
                } else {
                    startActivity(new Intent(Welcome.this, Login.class));
                }

            }
        });

    }
    private boolean isUserLoggedIn() {
        // Retrieve the shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Retrieve the authentication status flag from shared preferences
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        // Return the authentication status
        return isLoggedIn;
    }

    private boolean isAdminLoggedIn() {
        // Retrieve the shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Retrieve the authentication status flag from shared preferences
        boolean isLoggedIn = sharedPreferences.getBoolean("isAdminLoggedIn", false);

        // Return the authentication status
        return isLoggedIn;
    }
}