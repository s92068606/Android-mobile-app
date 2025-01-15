package com.example.myfoodapp.helpers;

// import necessary classes for working with SQLite database.
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    // The constructor of the DatabaseHelper class is defined here, which calls the constructor of the parent class
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //when 1st time create a database call oncreate
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //a SQL query is executed to create a table named "users" with two columns
        String qry1 = "create table users(email text,password text)";
        //this method is used to execute the SQL query.
        sqLiteDatabase.execSQL(qry1);
    }


    //The onUpgrade method is called when the database needs to be upgraded
    // here no specific actions are taken when upgrading the database.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    // used to register a user in the "users" table
    public void register(String email, String password) {
        //ContentValues object, which is used to store the column-value pairs for insertion
        ContentValues cv = new ContentValues();
        //values are put into the ContentValues object using the put method
        cv.put("email", email);
        cv.put("password", password);
        //method is called to get a writable database instance
        SQLiteDatabase db = getWritableDatabase();
        // method is used to insert the values into the "users" table.
        db.insert("users", null, cv);
        db.close();
    }

    //method is used to perform user login
    public int login(String email, String password) {
        // initializes a variable to 0
        int result = 0;
        //It creates a String array Str to store the email and password values.
        String Str[] = new String[2];
        Str[0] = email;
        Str[1] = password;
        //method is called to get a readable database instance.
        SQLiteDatabase db = getReadableDatabase();

        //method is used to execute a select query on the "users" table, with placeholders for the email and password values
        Cursor c = db.rawQuery("select * from users where email=? and password=?", Str);
        if (c.moveToFirst()) {
            result = 1;
        }
        return result;
    }
}