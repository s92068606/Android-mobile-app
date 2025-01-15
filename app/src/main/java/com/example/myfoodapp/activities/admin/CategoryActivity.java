package com.example.myfoodapp.activities.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfoodapp.activities.Login;
import com.example.myfoodapp.activities.admin.db.Category;
import com.example.myfoodapp.activities.admin.viewmodel.CategoryActivityViewModel;
import com.example.myfoodapp.R;
import com.example.myfoodapp.activities.customer.Dashboard;

import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryListAdapter.HandleCategoryClick {

    private CategoryActivityViewModel viewModel; // ViewModel instance for managing category data
    private TextView noResulttextView; // TextView for displaying "No result" message
    private RecyclerView recyclerView; // RecyclerView for displaying category list
    private CategoryListAdapter categoryListAdapter; // Adapter for populating the category list
    private Category categoryForEdit; // Category object for editing purposes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // Set click listener for the "Logout" button
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button logout=findViewById(R.id.louta);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the Login activity and finish the current activity
                Intent intent=new Intent(CategoryActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        // Set the title of the action bar
        getSupportActionBar().setTitle("Category List");

        // Initialize views
        noResulttextView  = findViewById(R.id.noResult);
        recyclerView  = findViewById(R.id.recyclerView);
        ImageView addNew = findViewById(R.id.addNewCategoryImageView);

        // Set a click listener for the add new category button
        addNew.setOnClickListener(v -> showAddCategoryDialog(false));

        // Initialize the ViewModel and RecyclerView
        initViewModel();
        initRecyclerView();

        // Get the list of categories from the ViewModel
        viewModel.getAllCategoryList();
    }

    // Initialize the RecyclerView with a LinearLayoutManager and CategoryListAdapter
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categoryListAdapter = new CategoryListAdapter(this, this);
        recyclerView.setAdapter(categoryListAdapter);
    }

    // Initialize the ViewModel and observe changes in the category list
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CategoryActivityViewModel.class);
        viewModel.getCategoryListObserver().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                if(categories == null) {
                    // If no categories are available, show the "No result" message
                    noResulttextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    // If categories are available, show them in the RecyclerView
                    categoryListAdapter.setCategoryList(categories);
                    recyclerView.setVisibility(View.VISIBLE);
                    noResulttextView.setVisibility(View.GONE);
                }
            }
        });
    }

    // Show the dialog to add or edit a category
    private void showAddCategoryDialog(boolean isForEdit) {
        AlertDialog dialogBuilder = new AlertDialog.Builder(this).create(); // Create an AlertDialog object
        View dialogView = getLayoutInflater().inflate( R.layout.add_category_layout, null);
        EditText enterCategoryInput = dialogView.findViewById(R.id.enterCategoryInput);
        TextView createButton = dialogView.findViewById(R.id.createButton);
        TextView cancelButton = dialogView.findViewById(R.id.cancelButton);

        if(isForEdit){
            // If editing a category, change the button text and pre-fill the category name
            createButton.setText("Update");
            enterCategoryInput.setText(categoryForEdit.categoryName);
        }
        // Set a click listener for the cancel button to dismiss the dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });

        // Set a click listener for the create/update button
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered category name from the EditText view
                String name = enterCategoryInput.getText().toString();
                if(TextUtils.isEmpty(name)) {
                    // If the category name is empty, show a toast message and return
                    Toast.makeText(CategoryActivity.this, "Enter category name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(isForEdit){
                    // If editing a category, update the category using the ViewModel
                    categoryForEdit.categoryName = name;
                    viewModel.updateCategory(categoryForEdit);
                } else {
                    // If adding a new category, insert the category using the ViewModel
                    //here we need to call view model.
                    viewModel.insertCategory(name);
                }
                dialogBuilder.dismiss();
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    // Handle item click in the RecyclerView by starting the ShowItemsListActivity
    @Override
    public void itemClick(Category category) {
        Intent intent = new Intent(CategoryActivity.this, ShowItemsListActivity.class);
        intent.putExtra("category_id", category.uid);
        intent.putExtra("category_name", category.categoryName);

        startActivity(intent);
    }

    // Handle remove item action by deleting the category using the ViewModel
    @Override
    public void removeItem(Category category) {
        viewModel.deleteCategory(category);
    }

    // Handle edit item action by saving the category for editing and showing the add/edit dialog
    @Override
    public void editItem(Category category) {
        this.categoryForEdit = category;
        showAddCategoryDialog(true);
    }
}