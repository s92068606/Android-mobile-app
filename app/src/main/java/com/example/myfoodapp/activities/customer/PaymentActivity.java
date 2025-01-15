package com.example.myfoodapp.activities.customer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.adapters.PlaceYourOrderAdapter;
import com.example.myfoodapp.model.FoodModel;
import com.example.myfoodapp.model.Menu;

public class PaymentActivity extends AppCompatActivity {

    private EditText cName, cAddress, cCity, cState, cZip, cCardNumber, cCardExpiry, cCardPin ;
    private RecyclerView cartItemsRecyclerView;
    private TextView tvSubtotalAmount, tvDeliveryChargeAmount, tvDeliveryCharge, tvTotalAmount, buttonPlaceYourOrder;
    private SwitchCompat switchDelivery;
    private boolean isDeliveryOn;
    private PlaceYourOrderAdapter placeYourOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Get the FoodModel object from the previous activity
        FoodModel foodModel = getIntent().getParcelableExtra("FoodModel");
        // Set up the ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(foodModel.getName());
        actionBar.setSubtitle(foodModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Initialize EditText fields, TextViews, RecyclerView, and SwitchCompat
        cName = findViewById(R.id.inputName);
        cAddress = findViewById(R.id.inputAddress);
        cCity = findViewById(R.id.inputCity);
        cState = findViewById(R.id.inputState);
        cZip = findViewById(R.id.inputZip);
        cCardNumber = findViewById(R.id.inputCardNumber);
        cCardExpiry = findViewById(R.id.inputCardExpiry);
        cCardPin = findViewById(R.id.inputCardPin);
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount);
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);
        switchDelivery = findViewById(R.id.switchDelivery);

        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);

        // Set click listeners for the button and switch
        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(foodModel);
            }
        });

        switchDelivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    // Show the delivery-related views
                    cAddress.setVisibility(View.VISIBLE);
                    cCity.setVisibility(View.VISIBLE);
                    cState.setVisibility(View.VISIBLE);
                    cZip.setVisibility(View.VISIBLE);
                    tvDeliveryChargeAmount.setVisibility(View.VISIBLE);
                    tvDeliveryCharge.setVisibility(View.VISIBLE);
                    isDeliveryOn = true;
                    calculateTotalAmount(foodModel);
                } else {
                    // Hide the delivery-related views
                    cAddress.setVisibility(View.GONE);
                    cCity.setVisibility(View.GONE);
                    cState.setVisibility(View.GONE);
                    cZip.setVisibility(View.GONE);
                    tvDeliveryChargeAmount.setVisibility(View.GONE);
                    tvDeliveryCharge.setVisibility(View.GONE);
                    isDeliveryOn = false;
                    calculateTotalAmount(foodModel);
                }
            }
        });
        // Initialize and set up the RecyclerView
        initRecyclerView(foodModel);
        // Calculate and display the total amount
        calculateTotalAmount(foodModel);
    }

    private void calculateTotalAmount(FoodModel foodModel) {
        float subTotalAmount = 0f;

        // Calculate the subtotal amount by summing up the prices of menu items
        for(Menu m : foodModel.getMenus()) {
            subTotalAmount += m.getPrice() * m.getTotalInCart();
        }

        // Display the subtotal amount
        tvSubtotalAmount.setText("Rs."+String.format("%.2f", subTotalAmount));
        if(isDeliveryOn) {
            // Display the delivery charge amount
            tvDeliveryChargeAmount.setText("Rs."+String.format("%.2f", foodModel.getDelivery_charge()));
            subTotalAmount += foodModel.getDelivery_charge();
        }
        // Display the total amount
        tvTotalAmount.setText("Rs."+String.format("%.2f", subTotalAmount));
    }

    private void onPlaceOrderButtonClick(FoodModel foodModel) {
        String cardNumber = cCardNumber.getText().toString().trim();

        // Validate user inputs
        if(TextUtils.isEmpty(cName.getText().toString())) {
            cName.setError("Please enter your name ");
            return;
        } else if(isDeliveryOn && TextUtils.isEmpty(cAddress.getText().toString())) {
            cAddress.setError("Please enter your address ");
            return;
        }else if(isDeliveryOn && TextUtils.isEmpty(cCity.getText().toString())) {
            cCity.setError("Please enter your city ");
            return;
        }else if(isDeliveryOn && TextUtils.isEmpty(cState.getText().toString())) {
            cState.setError("Please enter your zip ");
            return;
        }else if( TextUtils.isEmpty(cCardNumber.getText().toString())) {
            cCardNumber.setError("Please enter your card number ");
            return;
        }else if (cardNumber.length() != 16) {
            cCardNumber.setError("Card number should be 16 digits");
            return;
        }else if( TextUtils.isEmpty(cCardExpiry.getText().toString())) {
            cCardExpiry.setError("Please enter card expiry ");
            return;
        }else if( TextUtils.isEmpty(cCardPin.getText().toString())) {
            cCardPin.setError("Please enter card pin/cvv ");
            return;
        }
        //// Start the PaymentSuccessActivity and pass the FoodModel object ////
        Intent i = new Intent(PaymentActivity.this, PaymentSuccessActivity.class);
        i.putExtra("FoodModel", foodModel);
        startActivityForResult(i, 1000);
    }

    private void initRecyclerView(FoodModel foodModel) {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeYourOrderAdapter = new PlaceYourOrderAdapter(foodModel.getMenus());
        cartItemsRecyclerView.setAdapter(placeYourOrderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Handle the result from PaymentSuccessActivity
        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle ActionBar's back button click
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}