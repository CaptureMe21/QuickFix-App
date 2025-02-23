package com.example.quickfixapp.client;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quickfixapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VerifyActivity extends AppCompatActivity {

    private EditText birthMonth, birthday, birthYear, streetPurok, barangay, municipality, province;
    private TextView lName, fName, mName;
    private Button nextBtn;
    private RadioGroup genderGroup;
    private RadioButton maleBtn, femaleBtn;
    private String userId;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verify);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");

        if (userId == null) {
            Toast.makeText(this, "User ID is missing", Toast.LENGTH_SHORT).show();
        }

        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        // Initialize views
        lName = findViewById(R.id.lName);
        fName = findViewById(R.id.fName);
        mName = findViewById(R.id.mName);
        birthMonth = findViewById(R.id.birthMonth);
        birthday = findViewById(R.id.birthDay);
        birthYear = findViewById(R.id.birthYear);
        streetPurok = findViewById(R.id.street_purok);
        barangay = findViewById(R.id.barangay);
        municipality = findViewById(R.id.municipality);
        province = findViewById(R.id.province);
        genderGroup = findViewById(R.id.genderGroup);
        maleBtn = findViewById(R.id.MaleBtn);
        femaleBtn = findViewById(R.id.FemaleBtn);

        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(view -> {

            openVerify2Fragment(userId);

            /*if (areFieldsFilled()) {
                openVerify2Fragment();
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }*/
        });

        fetchUserData(userId);
    }

    private void fetchUserData(String userId) {
        dbRef.child("clientAccount").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            lName.setText(snapshot.child("lastName").getValue(String.class));
                            fName.setText(snapshot.child("firstName").getValue(String.class));
                            mName.setText(snapshot.child("mIddleName").getValue(String.class));
                        } else {
                            Toast.makeText(VerifyActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(VerifyActivity.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean areFieldsFilled() {
        boolean areTextFieldsFilled =
                !birthMonth.getText().toString().trim().isEmpty() &&
                        !birthday.getText().toString().trim().isEmpty() &&
                        !birthYear.getText().toString().trim().isEmpty() &&
                        !streetPurok.getText().toString().trim().isEmpty() &&
                        !barangay.getText().toString().trim().isEmpty() &&
                        !municipality.getText().toString().trim().isEmpty() &&
                        !province.getText().toString().trim().isEmpty();

        int selectedRadioButtonId = genderGroup.getCheckedRadioButtonId();
        boolean isRadioButtonSelected = selectedRadioButtonId != -1;

        return areTextFieldsFilled && isRadioButtonSelected;
    }

    private void openVerify2Fragment(String userId) {
        Verify_2Fragment fragments = new Verify_2Fragment();

        Bundle bundle = new Bundle();
        bundle.putString("userId", userId);
        fragments.setArguments(bundle);

        // Open fragment using FragmentTransaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragments);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}