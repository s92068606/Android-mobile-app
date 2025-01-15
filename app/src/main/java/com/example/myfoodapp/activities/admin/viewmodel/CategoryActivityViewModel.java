package com.example.myfoodapp.activities.admin.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.myfoodapp.activities.admin.db.AppDatabase;
import com.example.myfoodapp.activities.admin.db.Category;

import java.util.List;

//This class extends the AndroidViewModel class, which is a subclass of ViewModel
//specifically designed to store and manage UI-related data
public class CategoryActivityViewModel extends AndroidViewModel {

    //listOfCategory is an instance of MutableLiveData
    //that holds a list of Category objects. It allows observation of changes to its value.
    private MutableLiveData<List<Category>> listOfCategory;
    //appDatabase is an instance of AppDatabase, which is the Room database.
    private AppDatabase appDatabase;

    //This is the constructor for the CategoryActivityViewModel class.
    //It receives an Application object
    public CategoryActivityViewModel(Application application) {
        super(application);
        //initializes the listOfCategory as a new MutableLiveData
        listOfCategory = new MutableLiveData<>();
        //the appDatabase as the instance of the Room database.
        appDatabase = AppDatabase.getDBinstance(getApplication().getApplicationContext());
    }

    //This method returns the listOfCategory as a MutableLiveData object.
    public MutableLiveData<List<Category>>  getCategoryListObserver() {
        // It allows observers to observe changes to the list of categories.
        return listOfCategory;
    }

    //This method retrieves all the categories from the database using the DAO's getAllCategoriesList() method
    public void getAllCategoryList() {
        List<Category> categoryList=  appDatabase.shoppingListDao().getAllCategoriesList();
        //If the list of categories is not empty
        if(categoryList.size() > 0)
        {   //it sets the value of listOfCategory to the retrieved list using postValue()
            listOfCategory.postValue(categoryList);
        }else {  //Otherwise, it sets the value to null.
            listOfCategory.postValue(null);
        }
    }

    //This method inserts a new category into the database
    public void insertCategory(String catName) {
        // creates a new Category object
        Category category = new Category();
        category.categoryName = catName;
        // insert it into the database
        appDatabase.shoppingListDao().insertCategory(category);
        //update the list of categories
        getAllCategoryList();
    }

    //updates an existing category in the database
    public void updateCategory(Category category) {
        //update the category with the provided category object
        appDatabase.shoppingListDao().updateCategory(category);
        // update the list of categories.
        getAllCategoryList();
    }

    //This method deletes a category from the database
    public void deleteCategory(Category category) {
        appDatabase.shoppingListDao().deleteCategory(category);
        //After the deletion,update the list of categories.
        getAllCategoryList();
    }
}
    // It provides methods to retrieve, insert, update, and delete categories from the Room database
   //The MutableLiveData object listOfCategory is used to observe changes in the list of categories.