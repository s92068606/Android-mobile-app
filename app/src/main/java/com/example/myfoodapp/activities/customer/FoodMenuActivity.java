package com.example.myfoodapp.activities.customer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.adapters.MenuListAdapter;
import com.example.myfoodapp.model.FoodModel;
import com.example.myfoodapp.model.Menu;

import java.util.ArrayList;
import java.util.List;

public class FoodMenuActivity extends AppCompatActivity implements MenuListAdapter.MenuListClickListener {
    private List<Menu> menuList = null;
    private MenuListAdapter menuListAdapter;
    private List<Menu> itemsInCartList;
    private int totalItemInCart = 0;
    private TextView buttonCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        // Retrieve the FoodModel object passed from the previous activity
        FoodModel foodModel = getIntent().getParcelableExtra("FoodModel");
        // Set the title and enable the back button in the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(foodModel.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Get the list of menus from the FoodModel
        menuList = foodModel.getMenus();
        // Initialize and set up the RecyclerView
        initRecyclerView();

        // Set click listener for the "Checkout" button
         buttonCheckout = findViewById(R.id.buttonCheckout);
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if there are items in the cart
                if(itemsInCartList != null && itemsInCartList.size() <= 0) {
                    Toast.makeText(FoodMenuActivity.this, "Please add some items in cart.", Toast.LENGTH_SHORT).show();
                    return;
                } // Update the FoodModel with the items in the cart and start the PaymentActivity
                foodModel.setMenus(itemsInCartList);
                Intent i = new Intent(FoodMenuActivity.this, PaymentActivity.class);
                i.putExtra("FoodModel", foodModel);
                startActivityForResult(i, 1000);
            }
        });
    }

    // Initialize the RecyclerView and set its adapter
    private void initRecyclerView() {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        menuListAdapter = new MenuListAdapter(menuList, this);
        recyclerView.setAdapter(menuListAdapter);
    }

    // Handle "Add to Cart" button click event
    @Override
    public void onAddToCartClick(Menu menu) {
        // Check if the cart list is null, create a new list if necessary
        if(itemsInCartList == null) {
            itemsInCartList = new ArrayList<>();
        }
        // Add the selected menu to the cart list
        itemsInCartList.add(menu);
        totalItemInCart = 0;

        // Calculate the total number of items in the cart
        for(Menu m : itemsInCartList) {
            totalItemInCart = totalItemInCart + m.getTotalInCart();
        }
        // Update the text of the checkout button
        buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
    }

    // Handle "Update Cart" button click event
    @Override
    public void onUpdateCartClick(Menu menu) {
        // Check if the cart list contains the selected menu
        if(itemsInCartList.contains(menu)) {
            int index = itemsInCartList.indexOf(menu);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, menu);

            totalItemInCart = 0;

            // Calculate the total number of items in the cart
            for(Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            // Update the text of the checkout button
            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    // Handle "Remove from Cart" button click event
    @Override
    public void onRemoveFromCartClick(Menu menu) {
        // Check if the cart list contains the selected menu
        if(itemsInCartList.contains(menu)) {
            itemsInCartList.remove(menu);
            totalItemInCart = 0;

            // Calculate the total number of items in the cart
            for(Menu m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
            // Update the text of the checkout button
            buttonCheckout.setText("Checkout (" +totalItemInCart +") items");
        }
    }

    // Handle options menu item selection
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    // Handle the result from the PaymentActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            // Finish the activity if the result is successful
            finish();
        }
    }
}