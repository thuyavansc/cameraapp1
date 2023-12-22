package au.com.softclient.cameraapp1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fragment3 extends Fragment {

    private ImageView imageView;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private String currentPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        // Find the camera button and image view in the fragment layout
        Button cameraButton = view.findViewById(R.id.btnCamera3);
        imageView = view.findViewById(R.id.imageView3);

        // Initialize the ActivityResultLauncher
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Load the full-size image from the file
                            displayImage();
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
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Create a file to save the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Handle the error
                ex.printStackTrace();
            }

            // Continue only if the file was successfully created
            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(
                        requireContext(),
                        "au.com.softclient.cameraapp1.fileprovider", // replace with your app's package name
                        photoFile
                );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                cameraLauncher.launch(takePictureIntent);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    private void displayImage() {
        // Load the full-size image into the ImageView
        Bitmap imageBitmap = ImageUtils.decodeSampledBitmapFromFile(currentPhotoPath, 900, 900);
        imageView.setImageBitmap(imageBitmap);
    }
}

