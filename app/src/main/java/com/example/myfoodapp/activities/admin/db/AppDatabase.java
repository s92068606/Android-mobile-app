package com.example.myfoodapp.activities.admin.db;

//for accessing the application context
import android.content.Context;
//room annotation for creating and managing the database
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
//for working with collections
import java.util.List;

// In this case, Category.class and Items.class are the entity classes representing the tables.
//version = 1 indicates the initial version of the database.
@Database(entities = {Category.class, Items.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

        //defines the data access object (DAO) for the shopping list items
    //DAO is responsible for defining the methods to interact with the corresponding database table.
    // responsible for performing CRUD
        public abstract  ShoppingListDao shoppingListDao();
        //It will hold the singleton instance of the AppDatabase class.
        public static AppDatabase INSTANCE;
        // method that returns the singleton instance
    // para create the database instance if it's null
        public static AppDatabase getDBinstance(Context context) {
            if(INSTANCE == null ) {
                //this method is used to build the database using the provided context, database class (AppDatabase), and database name ("AppDB")
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "AppDB")
                        //this method allows executing database queries on the main thread
                        .allowMainThreadQueries()
                        .build();
            }
            return INSTANCE;
        }
        // data access object (DAO)
    //for managing the shopping list items in the database.
        @Dao
    public interface CategoryDao {
            //annotation is used to specify a SQL query for retrieving all categories
        @Query("SELECT * FROM Category")
        //The getAllCategories() method returns a List<Category> containing all the categories retrieved from the table.
        List<Category> getAllCategories();
    }
    /*
     this code sets up a Room database (AppDatabase)
     with two entity classes (Category and Items).
     It provides a DAO interface (ShoppingListDao)
     for managing shopping list items and another DAO interface
     (CategoryDao) for retrieving categories from the database.
     The getDBinstance() method ensures that only one instance of the
      database is created and accessed throughout the application. */
}