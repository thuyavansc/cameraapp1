package au.com.softclient.cameraapp1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Fragment4 extends Fragment {
    private static final String TAG = "Fragment4";
    private static final int CAMERA_REQUEST_CODE = 1;
    private ImageView imageView;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private String currentPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, container, false);

        // Find the camera button and image view in the fragment layout
        Button cameraButton = view.findViewById(R.id.btnCamera4);
        imageView = view.findViewById(R.id.imageView4);

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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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
                        "au.com.softclient.cameraapp1.fileprovider",
                        photoFile
                );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                cameraLauncher.launch(takePictureIntent);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = imageFile.getAbsolutePath();
        Log.d(TAG, "createImageFile: " + currentPhotoPath);
        return imageFile;
    }

    private void displayImage() {
        // Load the full-size image into the ImageView
        Bitmap imageBitmap = loadImageFromPath(currentPhotoPath);
        imageView.setImageBitmap(imageBitmap);
    }

    private Bitmap loadImageFromPath(String imagePath) {
        try {
            File file = new File(imagePath);
            if (file.exists()) {
                return MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), Uri.fromFile(file));
            } else {
                Toast.makeText(requireContext(), "Image file does not exist", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (IOException e) {
            Toast.makeText(requireContext(), "Error loading image", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return null;
        }
    }
}
