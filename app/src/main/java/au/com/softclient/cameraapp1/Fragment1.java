package au.com.softclient.cameraapp1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class Fragment1 extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container, false);

        // Find the camera button and image view in the fragment layout
        Button cameraButton = view.findViewById(R.id.btnCamera);
        imageView = view.findViewById(R.id.imageView);

        // Set click listener for the camera button
        cameraButton.setOnClickListener(v -> {
            // Open the camera when the button is clicked
            dispatchTakePictureIntent();
        });

        return view;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                // Get the captured image
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                // Set the captured image to the image view
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }
}