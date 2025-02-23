package com.example.quickfixapp.shopOwner;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quickfixapp.R;
import com.example.quickfixapp.client.NotificationFragment;
import com.example.quickfixapp.databinding.ActivityShopMainBinding;

public class ShopMainActivity extends AppCompatActivity {

    private ActivityShopMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        binding = ActivityShopMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setBackground(null);
        replaceFragment(new ShopHomepage());

        // Set navigation item selection listener
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            Fragment selectedFragment = null;
            if (itemId == R.id.home) {
                selectedFragment = new ShopHomepage();
            } else if (itemId == R.id.booking) {
                selectedFragment = new ShopBookingFragment();
            } else if (itemId == R.id.shopNotif) {
                selectedFragment = new NotificationFragment();
            } else if (itemId == R.id.shopProfile) {
                selectedFragment = new ShopProfileFragment();
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }

            return true;
        });
    }

    // Replace the current fragment with a new fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
