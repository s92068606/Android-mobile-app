package com.example.myfoodapp.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfoodapp.activities.customer.Dashboard;
import com.example.myfoodapp.helpers.DatabaseHelper;
import com.example.myfoodapp.activities.customer.MainActivity;
import com.example.myfoodapp.R;

public class Admin_Login extends AppCompatActivity {

    //Declare variables
    EditText Username, Password;

    //this method is the entry point for the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the layout for the activity
        setContentView(R.layout.activity_admin_login);
        //initialize the variables using by their id in the layout
        Username =findViewById(R.id.username);
        Password =findViewById(R.id.password);

        //login button function find the button id from the layout and sets onclicklistner
        Button buttonLogin = findViewById(R.id.btnLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this method called when button clicked
                opensignin();
            }
        });

        //signup button function
        Button newUser = findViewById(R.id.newUser);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this method called when button clicked
                opensignup();
            }
        });

        //It retrieves the username and password values entered by the admin
        buttonLogin.setOnClickListener(view -> {

            String username= Admin_Login.this.Username.getText().toString();
            String password= Admin_Login.this.Password.getText().toString();
            //creates an instance of the DatabaseHelper class to interact with the database
            DatabaseHelper db=new DatabaseHelper(getApplicationContext(),"myapp",null,1);

            //Checks if the username or password fields are empty
            if (username.length()==0 || password.length()==0){
                //it displays a toast message
                Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
            }
            else { //it calls the login method of the DatabaseHelper instance to validate the login credentials
                if(db.login(username,password)==1){
                    // If the login is successful, a toast message is displayed
                    Toast.makeText(getApplicationContext(), "LogIn Success", Toast.LENGTH_SHORT).show();
                    // Save the authentication status as true in shared preferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isAdminLoggedIn", true);
                    editor.apply();
                    startActivity(new Intent(Admin_Login.this, CategoryActivity.class));
                }else { //if any input values doesn't match with it
                    Toast.makeText(getApplicationContext(), "Invalid username password", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    //Defines the opensignin method, which creates an intent to open the Dashboard activity and starts the activity
    public void opensignin() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    //Defines the opensignup method, which creates an intent to open the customer_Signup activity and starts the activity.
    public void opensignup() {
        Intent intent = new Intent(this, Admin_Signup.class);
        startActivity(intent);
    }

}