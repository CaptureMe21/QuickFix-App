package com.example.quickfixapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickfixapp.client.ClientSignup;
import com.example.quickfixapp.shopOwner.ShopMainActivity;
import com.google.android.material.button.MaterialButton;

public class LandingActivity extends AppCompatActivity {

    private MaterialButton clientSignup, ownerSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landing);

        ownerSignup = findViewById(R.id.ShopOwnerSignUp);
        clientSignup = findViewById(R.id.CustomerSignUp);

        clientSignup.setOnClickListener(view -> {
            Intent intent = new Intent(LandingActivity.this, ClientSignup.class);
            startActivity(intent);
        });

        ownerSignup.setOnClickListener(view -> {
            Intent intent = new Intent(LandingActivity.this, ShopMainActivity.class);
            startActivity(intent);
        });

    }
}