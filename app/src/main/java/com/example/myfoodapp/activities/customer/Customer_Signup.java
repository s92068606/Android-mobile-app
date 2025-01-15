package com.example.myfoodapp.activities.customer;

import static com.example.myfoodapp.activities.admin.Admin_Signup.isValidEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfoodapp.activities.admin.Admin_Login;
import com.example.myfoodapp.activities.admin.Admin_Signup;
import com.example.myfoodapp.helpers.DatabaseHelper;
import com.example.myfoodapp.R;

public class Customer_Signup extends AppCompatActivity {

    // Declare the EditText fields
    private EditText Password, Email, Confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_signup);

        // Initialize the EditText fields
        Email = findViewById(R.id.eMaic);
        Password = findViewById(R.id.pwdSignupc);
        Confirm = findViewById(R.id.cnfrmPwdc);

        // Initialize the signup button and set a click listener
        Button btnSignup = findViewById(R.id.btnSignupc);
        btnSignup.setOnClickListener(view -> register());

        // Initialize the "Login" TextView and set a click listener
        TextView viewLogin = findViewById(R.id.textView16);
        viewLogin.setOnClickListener(view -> startActivity(new Intent(Customer_Signup.this, Customer_Login.class)));
    }

    // Method to handle the registration process
    public void register() {
        // Get the input values from the EditText fields
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String confirm = Confirm.getText().toString();

        // Create an instance of the DatabaseHelper class
        DatabaseHelper db = new DatabaseHelper(getApplicationContext(), "myappc", null, 1);

        // Validate the input values
        if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_SHORT).show();
        }else if (!isValidEmail(email)) {
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
        }
        else {
            if (password.equals(confirm)) {
                if (isValidPassword(password)) {
                    // Register the user in the database
                    db.register(email, password);
                    Toast.makeText(getApplicationContext(), "Signup Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Customer_Signup.this, Customer_Login.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Password must contain at least 6 characters including letters, digits and special symbols", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to validate an email address using a regular expression
    public static boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    // Method to validate a password
    public static boolean isValidPassword(String password) {
        int f1 = 0, f2 = 0, f3 = 0;
        if (password.length() < 6) {
            return false;
        } else {
            for (int p = 0; p < password.length(); p++) {
                if (Character.isLetter(password.charAt(p))) {
                    f1 = 1;
                }
            }
            for (int r = 0; r < password.length(); r++) {
                if (Character.isDigit(password.charAt(r))) {
                    f2 = 1;
                }
            }
            for (int s = 0; s < password.length(); s++) {
                char c = password.charAt(s);
                if (c >= 33 && c <= 46 || c == 64) {
                    f3 = 1;
                }
            }
            if (f1 == 1 && f2 == 1 && f3 == 1)
                return true;
            return false;
        }
    }
}