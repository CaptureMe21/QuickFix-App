package com.example.quickfixapp.shopOwner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
import java.util.List;

public class ShopMechanic extends AppCompatActivity {


    RecyclerView recyclerView;
    MechanicAdapter mechanicAdapter;
    ArrayList<MechanicModel> mechanicList;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shop_mechanic);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.mechanicViewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mechanicList = new ArrayList<>();
        mechanicAdapter = new MechanicAdapter(mechanicList);
        recyclerView.setAdapter(mechanicAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("mechanic");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mechanicList.clear(); // Clear list to avoid duplication
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MechanicModel mechanicModel = dataSnapshot.getValue(MechanicModel.class);
                    if (mechanicModel != null) {
                        mechanicList.add(mechanicModel);
                    }
                }
                mechanicAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ShopMechanic.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                Log.e("FirebaseError", error.getMessage());
            }
        });



        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mechanicList.clear(); // Clear list to avoid duplication
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MechanicModel mechanic = dataSnapshot.getValue(MechanicModel.class);
                    if (mechanic != null) {
                        mechanicList.add(mechanic);
                    }
                }
                mechanicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MechanicListActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                Log.e("FirebaseError", error.getMessage());
            }
        });*/

        LinearLayout backBTN = findViewById(R.id.back_btn);
        LinearLayout addMechanic = findViewById(R.id.addMechanic);

        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShopHomepage.class);
                startActivity(intent);
            }
        });

        addMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddMechanic.class);
                startActivity(intent);
            }
        });
    }


}

