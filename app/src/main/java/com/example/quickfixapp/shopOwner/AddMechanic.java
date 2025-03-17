package com.example.quickfixapp.shopOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quickfixapp.R;
import com.example.quickfixapp.model.MechanicModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddMechanic extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText Mfname, Mlname, Mmname, Mspecialty, Memail, Mpassword;
    private Button submit_btn;
    private LinearLayout back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_mechanic);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI Elements
        Mfname = findViewById(R.id.MFname);
        Mlname = findViewById(R.id.MLname);
        Mmname = findViewById(R.id.MMname);
        Mspecialty = findViewById(R.id.Mspecialty);
        Memail = findViewById(R.id.Memail);
        Mpassword = findViewById(R.id.Mpassword);
        submit_btn = findViewById(R.id.submit_btn);
        back_btn = findViewById(R.id.back_btn);

        databaseReference = FirebaseDatabase.getInstance().getReference("mechanic");

        submit_btn.setOnClickListener(view -> saveData());
        back_btn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ShopMechanic.class));
            finish(); // Prevents activity stacking
        });
    }

    private void saveData() {
        String mFirstName = Mfname.getText().toString().trim();
        String mLastName = Mlname.getText().toString().trim();
        String mMiddleName = Mmname.getText().toString().trim();
        String mSpecialty = Mspecialty.getText().toString().trim();
        String mEmail = Memail.getText().toString().trim();
        String mPassword = Mpassword.getText().toString().trim();

        // Validate Input Fields
        if (mFirstName.isEmpty() || mLastName.isEmpty() || mMiddleName.isEmpty() ||
                mSpecialty.isEmpty() || mEmail.isEmpty() || mPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate Email Format
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            Memail.setError("Enter a valid email address!");
            Memail.requestFocus();
            return;
        }

        // Validate Password Strength
        if (mPassword.length() < 8) {
            Mpassword.setError("Password must be at least 8 characters long!");
            Mpassword.requestFocus();
            return;
        }

        // Generate Current Timestamp
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Save to Firebase
        String mId = databaseReference.push().getKey();
        if (mId != null) {
            MechanicModel mechanic = new MechanicModel(mFirstName, mLastName, mMiddleName, mSpecialty, mEmail, mPassword, currentDate);
            databaseReference.child(mId).setValue(mechanic).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Mechanic added successfully!", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(getApplicationContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void clearFields() {
        Mfname.setText("");
        Mlname.setText("");
        Mmname.setText("");
        Mspecialty.setText("");
        Memail.setText("");
        Mpassword.setText("");
    }
}
