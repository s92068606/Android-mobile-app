package com.example.myfoodapp.activities.customer;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfoodapp.R;
import com.example.myfoodapp.model.FoodModel;

public class PaymentSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        // Retrieve the FoodModel object passed from the previous activity
        FoodModel foodModel = getIntent().getParcelableExtra("FoodModel");
        // Configure the ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(foodModel.getName());
        actionBar.setDisplayHomeAsUpEnabled(false);

        // Set a click listener for the "Done" button
        TextView buttonDone = findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Finish the activity when the button is clicked
            }
        });
    }
}