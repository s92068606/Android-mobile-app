package com.example.myfoodapp.activities.admin.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.myfoodapp.activities.admin.db.AppDatabase;
import com.example.myfoodapp.activities.admin.db.Items;
import java.util.List;

//managing the data related to the item list for the ShowItemListActivity.
public class ShowItemListActivityViewModel extends AndroidViewModel {

    // variable declare listOfItems is a MutableLiveData object that holds a list of items
    private MutableLiveData<List<Items>> listOfItems;
    // variable declare appDatabase is an instance of the AppDatabase class
    private AppDatabase appDatabase;

    // Constructor for the ViewModel
    public ShowItemListActivityViewModel(Application application) {
        super(application);
        // initializes the listOfItems with a new instance of MutableLiveData
        listOfItems = new MutableLiveData<>();
        //obtains an instance of the AppDatabase using the application context
        appDatabase = AppDatabase.getDBinstance(getApplication().getApplicationContext());
    }

    // Getter method to observe the list of items
    public MutableLiveData<List<Items>>  getItemsListObserver() {
        return listOfItems;
    }

    // Method to retrieve all items for a specific category from the database
    public void getAllItemsList(int categoryID) {
        // Retrieve the list of items from the database using the shoppingListDao
        List<Items> itemsList=  appDatabase.shoppingListDao().getAllItemsList(categoryID);
        // If the list is not empty, update the MutableLiveData object with the new list
        if(itemsList.size() > 0)
        {
            listOfItems.postValue(itemsList);
        }else {
            listOfItems.postValue(null);    // If the list is empty, post null to indicate an empty list
        }
    }

    // Method to insert a new item into the database
    public void insertItems(Items item) {
        // Insert the item into the database using the shoppingListDao
        appDatabase.shoppingListDao().insertItems(item);
        // Update the list of items by calling getAllItemsList for the corresponding category
        getAllItemsList(item.categoryId);
    }

    // Method to update an existing item in the database
    public void updateItems(Items item) {
        // Update the item in the database using the shoppingListDao
        appDatabase.shoppingListDao().updateItems(item);
        // Update the list of items by calling getAllItemsList for the corresponding category
        getAllItemsList(item.categoryId);
    }

    // Method to delete an item from the database
    public void deleteItems(Items item) {
        // Delete the item from the database using the shoppingListDao
        appDatabase.shoppingListDao().deleteItem(item);
        // Update the list of items by calling getAllItemsList for the corresponding category
        getAllItemsList(item.categoryId);
    }
}