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


    DatabaseReference databaseReference;

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

        EditText Mfname = findViewById(R.id.MFname);
        EditText Mlname = findViewById(R.id.MLname);
        EditText Mmname = findViewById(R.id.MMname);
        EditText Mspecialty = findViewById(R.id.Mspecialty);
        EditText Memail = findViewById(R.id.Memail);
        EditText Mpassword = findViewById(R.id.Mpassword);
        Button submit_btn =findViewById(R.id.submit_btn);
        LinearLayout back_btn = findViewById(R.id.back_btn);

        databaseReference = FirebaseDatabase.getInstance().getReference("mechanic");

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }

            private void saveData() {

                String mFirstName = Mfname.getText().toString().trim();
                String mLastname = Mlname.getText().toString().trim();
                String mMiddleName = Mmname.getText().toString().trim();
                String mSpecialty = Mspecialty.getText().toString().trim();
                String mEmail = Memail.getText().toString().trim();
                String mPassword = Mpassword.getText().toString().trim();


                if (mFirstName.isEmpty() || mLastname.isEmpty() || mMiddleName.isEmpty() ||
                        mSpecialty.isEmpty() || mEmail.isEmpty() || mPassword.isEmpty()) {
                    Toast.makeText(AddMechanic.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                    Memail.setError("Enter a valid email");
                    return;
                }


                if (mPassword.length() < 8) {
                    Mpassword.setError("Password must be at least 8 characters");
                    return;
                }

                String currentDate;
                currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                String mId = databaseReference.push().getKey();
                    MechanicModel mModel = new MechanicModel(mFirstName, mLastname, mMiddleName, mSpecialty, mEmail, mPassword, currentDate);
                assert mId != null;
                databaseReference.child(mId).setValue(mModel);
                    Toast.makeText(AddMechanic.this, "Mechanic has been added", Toast.LENGTH_SHORT).show();

                Mfname.setText("");
                Mlname.setText("");
                Mmname.setText("");
                Mspecialty.setText("");
                Memail.setText("");
                Mpassword.setText("");

            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShopMechanic.class);
                startActivity(intent);
            }
        });

    }
}