package com.example.myfoodapp.activities.admin.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//annotation marks this interface as a Data Access Object (DAO) for Room
@Dao
//DAOs provide the methods to interact with the database
public interface ShoppingListDao {

    // define a custom SQL query.
    @Query("Select * from Category")
    //retrieve all the records from the "Category" table. The return type is List<Category>
    List<Category> getAllCategoriesList();

    @Insert
    //this method is used to insert one or more Category objects into the database.
    void insertCategory(Category...categories);

    @Update
    //this method is used to update a specific Category object in the database.
    void updateCategory(Category category);

    @Delete
    //this method is used to delete a specific Category object from the database.
    void deleteCategory(Category category);

    @Query("Select * from Items where categoryId = :catId")  //retrieve all the items from the "Items" table with a specific category ID
    List<Items> getAllItemsList(int catId);

    @Insert
    //insert an Items object into the database.
    void insertItems(Items items);

    @Update
    //update a specific Items object in the database.
    void updateItems(Items items);

    @Delete
    //delete a specific Items object from the database.
    void deleteItem(Items items);

} //The interface serves as a contract for interacting with the database using these predefined methods.