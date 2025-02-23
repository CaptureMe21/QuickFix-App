package com.example.quickfixapp.client;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quickfixapp.R;

public class Verify_2Fragment extends Fragment {

    private static final int PICK_IMAGE_FRONT_ID = 1;
    private static final int PICK_IMAGE_BACK_ID = 2;

    private Button frontIdButton, backIdButton;
    private TextView frontIdText, backIdText;
    private Uri frontIdUri, backIdUri; // Stores selected images

    public Verify_2Fragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        return inflater.inflate(R.layout.fragment_verify_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        frontIdButton = view.findViewById(R.id.front_id);
        backIdButton = view.findViewById(R.id.back_id);
        frontIdText = view.findViewById(R.id.frontId_name);
        backIdText = view.findViewById(R.id.backID_name);

        // Set button listeners
        frontIdButton.setOnClickListener(v -> selectImage(PICK_IMAGE_FRONT_ID));
        backIdButton.setOnClickListener(v -> selectImage(PICK_IMAGE_BACK_ID));
    }

    // Function to open the gallery
    private void selectImage(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }

    // Handle the selected image result
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                if (requestCode == PICK_IMAGE_FRONT_ID) {
                    frontIdUri = selectedImageUri;
                    frontIdText.setText(getFileName(selectedImageUri));
                } else if (requestCode == PICK_IMAGE_BACK_ID) {
                    backIdUri = selectedImageUri;
                    backIdText.setText(getFileName(selectedImageUri));
                }
            }
        }
    }

    // Helper function to extract file name from Uri
    private String getFileName(Uri uri) {
        String path = uri.getPath();
        if (path != null) {
            int lastSlashIndex = path.lastIndexOf('/');
            return lastSlashIndex != -1 ? path.substring(lastSlashIndex + 1) : "Unknown File";
        }
        return "Unknown File";
    }
}
