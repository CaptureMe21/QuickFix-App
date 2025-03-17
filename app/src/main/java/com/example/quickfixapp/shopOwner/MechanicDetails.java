package com.example.quickfixapp.shopOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quickfixapp.R;
import com.example.quickfixapp.model.MechanicModel;

import org.w3c.dom.Text;

public class MechanicDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mechanic_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView MecEmail = findViewById(R.id.mechanicEmail);
        TextView MecName = findViewById(R.id.mechanicName);
        TextView dateHired = findViewById(R.id.dateHired);
        TextView MecSpecialty = findViewById(R.id.specialtyList);
        ListView recentFixes = findViewById(R.id.fixesList);
        LinearLayout back_btn = findViewById(R.id.back_btn);

        MechanicModel mechanicModel = (MechanicModel)getIntent().getSerializableExtra("mechanic");

        if (mechanicModel != null){
            MecEmail.setText(mechanicModel.getmEmail());
            MecName.setText(mechanicModel.getmFirstName() + " " + mechanicModel.getmMiddleName() + " " + mechanicModel.getmLastName());
            dateHired.setText(mechanicModel.getDateAdded());
            MecSpecialty.setText(mechanicModel.getmSpecialty());
        }

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}