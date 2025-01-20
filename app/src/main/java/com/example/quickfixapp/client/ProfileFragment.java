package com.example.quickfixapp.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.quickfixapp.MainActivity;
import com.example.quickfixapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Parameters
    private String mParam1;
    private String mParam2;

    // UI Components
    private TextView tvRoleOfUser, tvFullName, tvIsVerified, tvEmail, tvAddress, tvPhoneNumber, tvDateRegistered;
    private Button verifyButton, logoutButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI components
        tvRoleOfUser = view.findViewById(R.id.roleOfUser);
        tvFullName = view.findViewById(R.id.fullName);
        tvIsVerified = view.findViewById(R.id.isVerified);
        tvEmail = view.findViewById(R.id.emailField);
        tvAddress = view.findViewById(R.id.addressField);
        tvPhoneNumber = view.findViewById(R.id.phoneField);
        tvDateRegistered = view.findViewById(R.id.dateField);
        verifyButton = view.findViewById(R.id.verifyButton);
        logoutButton = view.findViewById(R.id.logoutButton);

        // Set up click listeners
        verifyButton.setOnClickListener(v -> onVerifyButtonClick());
        logoutButton.setOnClickListener(v -> onLogoutButtonClick());

        // Display user details
        displayUserDetails();

        return view;
    }

    /**
     * Method to display user details in the ProfileFragment
     */
    private void displayUserDetails() {
        // Get the current user ID from Firebase Authentication
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid(); // Get the UID of the currently logged-in user

            // Reference the logged-in user's data in the database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child("clientAccount").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Retrieve user data from the database
                        String firstName = snapshot.child("firstName").getValue(String.class);
                        String middleName = snapshot.child("mIddleName").getValue(String.class);
                        String lastName = snapshot.child("lastName").getValue(String.class);
                        String fullName = (firstName != null ? firstName + " " : "") +
                                (middleName != null ? middleName + " " : "") +
                                (lastName != null ? lastName : "");

                        String purok = snapshot.child("purok").getValue(String.class);
                        String barangay = snapshot.child("barangay").getValue(String.class);
                        String town = snapshot.child("town").getValue(String.class);
                        String province = snapshot.child("province").getValue(String.class);

                        String address = (purok != null ? purok + ", " : "") +
                                (barangay != null ? barangay + ", " : "") +
                                (town != null ? town + ", " : "") +
                                (province != null ? province : "");

                        Integer roleValue = snapshot.child("role").getValue(Integer.class);
                        String roleOfUser;

                        if (roleValue != null) {
                            if (roleValue == 1) {
                                roleOfUser = "Motorcycle Owner";
                            } else if (roleValue == 2) {
                                roleOfUser = "Shop Owner";
                            } else if (roleValue == 3) {
                                roleOfUser = "Mechanic";
                            } else {
                                roleOfUser = "Unknown Role"; // Default for unexpected values
                            }
                        } else {
                            roleOfUser = "No Role Assigned"; // Handle null role values
                        }

                        String isVerified = snapshot.child("verified").getValue(Boolean.class) ? "Verified" : "Not Verified";
                        String email = snapshot.child("email").getValue(String.class);
                        String phoneNumber = snapshot.child("phoneNum").getValue(String.class); // Correct key used
                        String isOnline = snapshot.child("isOnline").getValue(Boolean.class) ? "Online" : "Offline";
                        String date = snapshot.child("dateRegistered").getValue(String.class);

                        // Set data to UI components
                        tvRoleOfUser.setText(roleOfUser);
                        tvFullName.setText(fullName);
                        tvIsVerified.setText(isVerified);
                        tvEmail.setText(email);
                        tvPhoneNumber.setText(phoneNumber);
                        tvDateRegistered.setText(date);

                        // Check if the address has any data
                        if (address.trim().isEmpty()) {
                            tvAddress.setText("Not verified yet. Finish your verification");
                        } else {
                            tvAddress.setText("Address: " + address);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
        } else {
            // User is not authenticated, redirect to ClientLogin
            Intent intent = new Intent(getActivity(), ClientLogin.class); // Use getActivity() to get the context
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
            startActivity(intent);
        }
    }

    // Blank method for Verify button
    private void onVerifyButtonClick() {
        // Add logic here for verification
    }

    // Logout button functionality
    private void onLogoutButtonClick() {
        // Get the currently logged-in user's ID
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child("clientAccount").child(userId);

            // Set isOnline to false in the database
            userRef.child("isOnline").setValue(false).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Sign out the user from FirebaseAuth
                    auth.signOut();

                    // Navigate to ClientLogin activity
                    Intent intent = new Intent(getActivity(), ClientLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    // Close the current fragment's parent activity
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                } else {
                    // Handle the case where updating isOnline fails
                    Toast.makeText(getActivity(), "Error logging out. Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // No user is logged in, navigate directly to ClientLogin
            Intent intent = new Intent(getActivity(), ClientLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            if (getActivity() != null) {
                getActivity().finish();
            }
        }
    }
}
