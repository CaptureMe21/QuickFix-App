package com.example.quickfixapp.shopOwner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickfixapp.Adapter.MechanicAdapter;
import com.example.quickfixapp.R;
import com.example.quickfixapp.model.MechanicModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopMechanic extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MechanicAdapter mechanicAdapter;
    private ArrayList<MechanicModel> mechanicList;
    private DatabaseReference databaseReference;
    private static final String TAG = "ShopMechanic"; // For logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_mechanic);

        // Apply window insets for modern UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI components
        LinearLayout backBTN = findViewById(R.id.back_btn);
        LinearLayout addMechanic = findViewById(R.id.addMechanic);

        // Back button action
        backBTN.setOnClickListener(view -> {
            finish();
        });

        // Add Mechanic button action
        addMechanic.setOnClickListener(view -> {
            startActivity(new Intent(ShopMechanic.this, AddMechanic.class));
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.mechanicViewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList and Adapter
        mechanicList = new ArrayList<>();
        mechanicAdapter = new MechanicAdapter(this, mechanicList);
        recyclerView.setAdapter(mechanicAdapter);

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("mechanic");

        // Fetch data from Firebase
        fetchMechanics();
    }

    private void fetchMechanics() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mechanicList.clear(); // Clear old data to prevent duplicates
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MechanicModel mechanic = dataSnapshot.getValue(MechanicModel.class);
                    if (mechanic != null) {
                        mechanicList.add(mechanic);
                    }
                }
                mechanicAdapter.notifyDataSetChanged(); // Update RecyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShopMechanic.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
