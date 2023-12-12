package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    private TreasureDatabase treasureDatabase;
    private DatabaseDao databaseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views and database
        initViews();
        initDatabase();

        // Fetch data from Room database
        fetchDataFromDatabase();

        Button toggleButton = findViewById(R.id.btnMenu);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDrawerToggle(v);
            }
        });
    }

    private void initViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void initDatabase() {
        treasureDatabase = Room.databaseBuilder(getApplicationContext(), TreasureDatabase.class, "treasure_database").build();
        databaseDao = treasureDatabase.databaseDao();
    }

    private void fetchDataFromDatabase() {
        RetrieveLocationsAsyncTask asyncTask = new RetrieveLocationsAsyncTask(treasureDatabase);
        asyncTask.execute();
    }

    // AsyncTask to retrieve locations from Room database
    private class RetrieveLocationsAsyncTask extends AsyncTask<Void, Void, List<Treasure>> {
        private TreasureDatabase database;

        public RetrieveLocationsAsyncTask(TreasureDatabase database) {
            this.database = database;
        }

        @Override
        protected List<Treasure> doInBackground(Void... voids) {
            if (database != null) {
                DatabaseDao dao = database.databaseDao();
                if (dao != null) {
                    return dao.getAllTreasures();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Treasure> treasures) {
            super.onPostExecute(treasures);

            if (treasures != null && mMap != null) {
                mMap.clear();

                for (Treasure treasure : treasures) {
                    LatLng location = new LatLng(treasure.getLatitude(), treasure.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(location).title(treasure.getName()));
                }
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Fetch data from Room database
        new RetrieveLocationsAsyncTask(treasureDatabase).execute();
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