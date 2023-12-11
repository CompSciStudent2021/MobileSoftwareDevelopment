package com.example.assignment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    private TreasureDatabase treasureDatabase;
    private DatabaseDao databaseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing the map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        // Initialize treasureDatabase
        treasureDatabase = Room.databaseBuilder(getApplicationContext(), TreasureDatabase.class, "treasure_database").build();


        // Handle navigation item clicks
        navView.setNavigationItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();

            if (itemId == R.id.button_treasure_list) {
                startActivity(new Intent(this, TreasureList.class));
            } else if (itemId == R.id.button_log_entry) {
                startActivity(new Intent(this, LogEntry.class));
            } else if (itemId == R.id.MainActivity) {
                startActivity(new Intent(this, MainActivity.class));
            }

            // Close the drawer when an item is selected
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try (InputStream inputStream = getResources().openRawResource(R.raw.wonders);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            // Read the header line (skip it)
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                String name = tokens[0];
                double latitude = Double.parseDouble(tokens[1]);
                double longitude = Double.parseDouble(tokens[2]);

                Treasure treasure = new Treasure();
                treasure.setName(name);
                treasure.setLatitude(latitude);
                treasure.setLongitude(longitude);

                LatLng location = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(location).title(name));
                
                new InsertAsyncTask(treasureDatabase).execute(treasure);

                // Enable user interaction like zoom controls, etc.
                mMap.getUiSettings().setZoomControlsEnabled(true);
            }

            // Set camera to show all markers
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0.0, 0.0), 2f));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to handle drawer toggle button clicks
    public void onDrawerToggle(View view) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void openTreasureList(MenuItem item) {
        startActivity(new Intent(this, TreasureList.class));
    }

    public void openLogEntry(MenuItem item) {
        startActivity(new Intent(this, LogEntry.class));
    }

    public void MainActivity(View clickedView) {
        startActivity(new Intent(this, LogEntry.class));
    }

    // AsyncTask to perform database insert operation in the background
    private static class InsertAsyncTask extends AsyncTask<Treasure, Void, Void> {
        private TreasureDatabase database;

        // Constructor to receive the database instance
        public InsertAsyncTask(TreasureDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(Treasure... treasures) {
            database.databaseDao().insert(treasures[0]);
            return null;
        }
    }
}
