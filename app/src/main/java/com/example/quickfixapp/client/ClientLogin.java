package com.example.quickfixapp.client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickfixapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientLogin extends AppCompatActivity {

    private TextView navSignup;
    private Button fbLogin, googleLogin, loginBtn;
    private EditText etEmail, etPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_login);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Bind Views
        navSignup = findViewById(R.id.signUp);
        fbLogin = findViewById(R.id.fb_login);
        googleLogin = findViewById(R.id.google_login);
        loginBtn = findViewById(R.id.sign_In);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);

        // Navigate to SignUp
        navSignup.setOnClickListener(view -> {
            Intent intent = new Intent(ClientLogin.this, ClientSignup.class);
            startActivity(intent);
        });

        // Set OnClickListener for Login Button
        loginBtn.setOnClickListener(view -> loginUser());
    }

    private void loginUser() {
        // Get user inputs
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validate inputs
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Perform Firebase Authentication login
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                        // Get the currently authenticated user
                        String userId = firebaseAuth.getCurrentUser().getUid();

                        // Update the user's status in the database
                        updateAuthenticationStatus(userId, true);

                        // Navigate to the next activity
                        Intent intent = new Intent(ClientLogin.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Login failed, display error message
                        Toast.makeText(this, "Wrong email or password!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Method to update authentication status
    private void updateAuthenticationStatus(String userId, boolean isOnline) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child("clientAccount").child(userId);

        // Update the isOnline field
        databaseReference.child("isOnline").setValue(isOnline);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child("clientAccount").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        Boolean isOnline = snapshot.child("isOnline").getValue(Boolean.class);
                        if (isOnline != null && isOnline) {
                            navigateToMainActivity();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error (optional)
                    Toast.makeText(ClientLogin.this, "Error checking user status", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(ClientLogin.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
