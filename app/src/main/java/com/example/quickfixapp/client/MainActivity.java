package com.example.quickfixapp.client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quickfixapp.LandingPage;
import com.example.quickfixapp.R;
import com.example.quickfixapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private DatabaseReference usersRef;
    private volatile boolean isVerified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setBackground(null);
        checkAuthentication();

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void checkAuthentication() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            // User is not authenticated, redirect to ClientLogin
            Intent intent = new Intent(MainActivity.this, LandingPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
            startActivity(intent);
            finish();
        } else {
            checkUserVerification(currentUser.getUid());  // Call to check verification
        }
    }

    private void checkUserVerification(String userId) {
        // Initialize the users reference
        usersRef = FirebaseDatabase.getInstance().getReference("Users")
                .child("clientAccount")
                .child(userId);

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get the 'verified' status from the database
                    Boolean verified = dataSnapshot.child("verified").getValue(Boolean.class);

                    // Update the verification status
                    setVerificationStatus(verified != null && verified);
                } else {
                    // Handle case when user data does not exist
                    setVerificationStatus(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential database error here
                Log.e("Firebase", "Database error: " + databaseError.getMessage());
                setVerificationStatus(false);
            }
        });
    }

    private void setVerificationStatus(boolean verified) {
        isVerified = verified;

        if (verified) {
            enableNavigation();
            replaceFragment(new MapFragment());
        } else {
            disableNavigation();
            replaceFragment(new ProfileFragment());
        }
    }

    private void enableNavigation() {
        if (binding.bottomNavigationView != null) {
            // Enable all menu items
            Menu menu = binding.bottomNavigationView.getMenu();
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setEnabled(true);
            }

            binding.bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (isVerified) {
                    // Allow navigation to other fragments if verified
                    if (itemId == R.id.explore_map) {
                        replaceFragment(new MapFragment());
                    } else if (itemId == R.id.notif) {
                        replaceFragment(new NotificationFragment());
                    } else if (itemId == R.id.profile) {
                        replaceFragment(new ProfileFragment());
                    }
                } else {
                    // If not verified, navigate to ProfileFragment and disable navigation
                    replaceFragment(new ProfileFragment());
                    disableNavigation();
                }
                return true;
            });
        }
    }

    private void disableNavigation() {
        if (binding.bottomNavigationView != null) {
            // Disable all menu items except profile
            Menu menu = binding.bottomNavigationView.getMenu();
            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.getItem(i);
                item.setEnabled(item.getItemId() == R.id.profile);
            }

            binding.bottomNavigationView.setOnItemSelectedListener(item -> {
                if (item.getItemId() == R.id.profile) {
                    replaceFragment(new ProfileFragment());
                    return true;
                }
                return false;
            });
        }
    }

    // Add getter method if needed
    public boolean isUserVerified() {
        return isVerified;
    }
}
