package com.example.myfoodapp.activities.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.R;
import com.example.myfoodapp.activities.Login;
import com.example.myfoodapp.adapters.RestaurantListAdapter;
import com.example.myfoodapp.model.FoodModel;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class Dashboard extends AppCompatActivity implements RestaurantListAdapter.RestaurantListClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Set the title of the action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Menu List");

        // Get the list of FoodModel from the JSON file
        List<FoodModel> foodModelList =  getRestaurantData();

        // Initialize and set up the RecyclerView
        initRecyclerView(foodModelList);

        // Set click listener for the "View Maps" button
        Button viewBtn=findViewById(R.id.viewMaps);
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Dashboard.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for the "Logout" button
        Button logout=findViewById(R.id.lout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Login activity and finish the current activity
                Intent intent=new Intent(Dashboard.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Initialize the RecyclerView and set its adapter
    private void initRecyclerView(List<FoodModel> foodModelList) {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RestaurantListAdapter adapter = new RestaurantListAdapter(foodModelList, this);
        recyclerView.setAdapter(adapter);
    }

    // Read and parse the JSON file to retrieve the restaurant data
    private List<FoodModel> getRestaurantData() {
        InputStream is = getResources().openRawResource(R.raw.detail);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try{
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while(( n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0,n);
            }
        }catch (Exception e) {
            // Handle any exceptions that occur during file reading or parsing

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        FoodModel[] foodModels =  gson.fromJson(jsonStr, FoodModel[].class);
        List<FoodModel> restList = Arrays.asList(foodModels);

        return  restList;
    }

    // Handle item click events in the RecyclerView
    @Override
    public void onItemClick(FoodModel foodModel) {
        // Start the FoodMenuActivity with the selected FoodModel
        Intent intent = new Intent(Dashboard.this, FoodMenuActivity.class);
        intent.putExtra("FoodModel", foodModel);
        startActivity(intent);
    }
}