package com.example.myfoodapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myfoodapp.R;
import com.example.myfoodapp.activities.admin.Admin_Login;
import com.example.myfoodapp.activities.admin.Admin_Signup;
import com.example.myfoodapp.activities.customer.Customer_Login;
import com.example.myfoodapp.activities.customer.Customer_Signup;

public class Login extends AppCompatActivity {
    private Button btnCustomer;
    private Button btnRestaurent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //customer
        btnCustomer = findViewById(R.id.button3);
        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Customer_Login.class);
                startActivity(intent);
            }
        });

        //admin or restaurent
            btnRestaurent = findViewById(R.id.button2);
            btnRestaurent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Login.this, Admin_Login.class);
                    startActivity(intent);
                }
            });
    }
}