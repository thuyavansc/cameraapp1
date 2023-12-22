package au.com.softclient.cameraapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        findViewById(R.id.btnLoadFragment1).setOnClickListener(v -> {
//            // Load Fragment1 when the button is clicked
//            loadFragment1();
//        });
//    }
//
//    private void loadFragment1() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//
//        // Replace the container with Fragment1
//        transaction.replace(R.id.fragmentContainer, new Fragment1());
//
//        transaction.addToBackStack(null); // Add to back stack for back navigation
//        transaction.commit();
//    }
//}

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnLoadFragment1).setOnClickListener(v -> {
            // Load Fragment1 when the button is clicked
            loadFragment(new Fragment1());
        });

        findViewById(R.id.btnLoadFragment2).setOnClickListener(v -> {
            // Load Fragment2 when the button is clicked
            loadFragment(new Fragment2());
        });

        findViewById(R.id.btnLoadFragment3).setOnClickListener(v -> {
            // Load Fragment3 when the button is clicked
            loadFragment(new Fragment3());
        });

        findViewById(R.id.btnLoadFragment4).setOnClickListener(v -> {
            // Load Fragment3 when the button is clicked
            loadFragment(new Fragment4());
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace the container with the specified fragment
        transaction.replace(R.id.fragmentContainer, fragment);

        transaction.addToBackStack(null); // Add to back stack for back navigation
        transaction.commit();
    }
}