package com.example.myfoodapp.activities.admin;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.activities.admin.db.Items;
import com.example.myfoodapp.activities.admin.viewmodel.ShowItemListActivityViewModel;
import com.example.myfoodapp.R;

import java.util.List;

public class ShowItemsListActivity extends AppCompatActivity implements ItemsListAdapter.HandleItemsClick {

    private int category_id; // ID of the category
    private ItemsListAdapter itemsListAdapter; // Adapter for the RecyclerView
    private ShowItemListActivityViewModel viewModel;  // ViewModel for the activity
    private RecyclerView recyclerView; // RecyclerView to display items
    private Items itemToUpdate = null; // Item being updated

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items_list);

        // Retrieve category ID and name from the intent
        category_id = getIntent().getIntExtra("category_id", 0);
        String categoryName = getIntent().getStringExtra("category_name");

        // Set the title and enable the back button in the action bar
        getSupportActionBar().setTitle(categoryName);
        // Enable the back button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize views and set click listeners
        // EditText for entering new item
        final EditText addNewItemInput  = findViewById(R.id.addNewItemInput);
        // ImageView for saving the new item
        ImageView saveButton = findViewById(R.id.saveButton);
        // Set a click listener for the saveButton
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered item name
                String itemName = addNewItemInput.getText().toString();
                if(TextUtils.isEmpty(itemName)) {  // Check if the item name is empty
                    Toast.makeText(ShowItemsListActivity.this, "Enter Item Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(itemToUpdate == null)
                    // Save the new item
                    saveNewItem(itemName);
                else
                    // Update the existing item
                    updateNewItem(itemName);
            }
        });
        // Initialize the RecyclerView and ViewModel
        initRecyclerView();
        // Initialize the ViewModel
        initViewModel();
        // Retrieve the list of items for the category
        viewModel.getAllItemsList(category_id);
    }

    private void initViewModel() {
        // Create the ViewModel instance
        viewModel = new ViewModelProvider(this).get(ShowItemListActivityViewModel.class);
        // Observe the items list LiveData
        viewModel.getItemsListObserver().observe(this, new Observer<List<Items>>() {
            @Override
            public void onChanged(List<Items> items) {
                if(items == null) {
                    recyclerView.setVisibility(View.GONE); // Hide the RecyclerView
                    findViewById(R.id.noResult).setVisibility(View.VISIBLE); // Show the "no result" view
                } else {
                    // Update the items in the adapter
                    itemsListAdapter.setCategoryList(items);
                    // Hide the "no result" view
                    findViewById(R.id.noResult).setVisibility(View.GONE);
                    // Show the RecyclerView
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        // Set the LinearLayoutManager as the layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemsListAdapter = new ItemsListAdapter(this, this); // Create the adapter instance
        recyclerView.setAdapter(itemsListAdapter); // Set the adapter for the RecyclerView
    }

    private void saveNewItem(String itemName) {
        Items item = new Items(); // Create a new Items object
        item.itemName  = itemName; // Set the item name
        item.categoryId = category_id; // Set the category ID
        viewModel.insertItems(item); // Insert the item through the ViewModel
        ((EditText) findViewById(R.id.addNewItemInput)).setText(""); // Clear the EditText view
    }

    @Override
    public void itemClick(Items item) {
        if(item.completed) {
            item.completed = false; // Toggle the completed flag of the item
        }
         else {
             item.completed = true; // Toggle the completed flag of the item
        }
        viewModel.updateItems(item); // Update the item through the ViewModel
    }

    @Override
    public void removeItem(Items item) {
        viewModel.deleteItems(item); // Delete the item through the ViewModel
    }

    @Override
    public void editItem(Items item) {
        this.itemToUpdate = item; // Set the item to be updated
        // Set the EditText with the item name for editing
        ((EditText) findViewById(R.id.addNewItemInput)).setText(item.itemName);
    }

    private void updateNewItem(String newName) {
        itemToUpdate.itemName = newName; // Update the name of the item
        viewModel.updateItems(itemToUpdate); // Update the item through the ViewModel
        ((EditText) findViewById(R.id.addNewItemInput)).setText(""); // Clear the EditText view
        itemToUpdate = null; // Reset the itemToUpdate variable
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle menu item clicks
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}