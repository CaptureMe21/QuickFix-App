package com.example.quickfixapp.client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quickfixapp.R;
import com.example.quickfixapp.model.clientSignupModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ClientSignup extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private EditText etLastname, etFirstname, etMiddlename, etUsername, etPhoneNumber, etEmail, etPassword;
    private Button btnSignUp;
    private TextView loginNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_signup);

        // Adjust for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase instances
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Bind EditText and Button
        etLastname = findViewById(R.id.lname);
        etFirstname = findViewById(R.id.fname);
        etMiddlename = findViewById(R.id.middleInit);
        etUsername = findViewById(R.id.username);
        etPhoneNumber = findViewById(R.id.phoneNum);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.sign_up);
        loginNav = findViewById(R.id.logIn_btn);

        loginNav.setOnClickListener(v -> {
            loginUser();
        });

        // Set OnClickListener for the Sign Up button
        btnSignUp.setOnClickListener(v -> signUpUser());
    }

    private void signUpUser() {
        // Get values from EditText fields
        String lastName = etLastname.getText().toString().trim();
        String firstName = etFirstname.getText().toString().trim();
        String middleName = etMiddlename.getText().toString().trim();
        String userName = etUsername.getText().toString().trim();
        String phoneNum = etPhoneNumber.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate input fields
        if (lastName.isEmpty() || firstName.isEmpty() || phoneNum.isEmpty() ||
                userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Bind the checkbox
        CheckBox checkBox = findViewById(R.id.checkBox);

        // Validate checkbox
        if (!checkBox.isChecked()) {
            Toast.makeText(this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use Firebase Authentication to create the user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Firebase user creation was successful
                        String userId = task.getResult().getUser().getUid();

                        // Get current date and format it as a string
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String getTimestamp = dateFormat.format(new Date());

                        // Create a user object
                        clientSignupModel clientAccount = new clientSignupModel(userId, lastName, firstName, middleName, userName, phoneNum, email, 1, getTimestamp, false);

                        // Push the user data to Firebase Realtime Database
                        databaseReference.child("clientAccount").child(userId).setValue(clientAccount)
                                .addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful()) {
                                        Toast.makeText(this, "User signed up successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ClientSignup.this, ClientLogin.class);
                                        startActivity(intent);
                                        resetFields();
                                    } else {
                                        Toast.makeText(this, "Failed to save user data: " + dbTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resetFields() {
        etLastname.setText("");
        etFirstname.setText("");
        etMiddlename.setText("");
        etUsername.setText("");
        etPhoneNumber.setText("");
        etEmail.setText("");
        etPassword.setText("");
    }

    private void loginUser() {
        Intent intent = new Intent(ClientSignup.this, ClientLogin.class);
        startActivity(intent);
    }
}
