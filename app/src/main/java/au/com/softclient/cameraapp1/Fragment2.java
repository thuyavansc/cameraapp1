package au.com.softclient.cameraapp1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class Fragment2 extends Fragment {

    private ImageView imageView;
    private ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, container, false);

        // Find the camera button and image view in the fragment layout
        Button cameraButton = view.findViewById(R.id.btnCamera2);
        imageView = view.findViewById(R.id.imageView2);

        // Initialize the ActivityResultLauncher
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Get the captured image
                            Bundle extras = result.getData().getExtras();
                            if (extras != null) {
                                Bitmap imageBitmap = (Bitmap) extras.get("data");
                                // Set the captured image to the image view
                                imageView.setImageBitmap(imageBitmap);
                            }
                        }
                    }
                });

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
            // Use the ActivityResultLauncher to start the camera activity
            cameraLauncher.launch(takePictureIntent);
        }
    }
}

