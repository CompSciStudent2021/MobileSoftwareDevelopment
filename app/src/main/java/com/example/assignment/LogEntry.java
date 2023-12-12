package com.example.assignment;

import static com.example.assignment.MainActivity.mMap;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class LogEntry extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private EditText editTextName;
    private EditText editTextLatitude;
    private EditText editTextLongitude;

    private TreasureDatabase database;
    private DatabaseDao databaseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_entry);

        editTextName = findViewById(R.id.editTextName);
        editTextLatitude = findViewById(R.id.editTextLatitude);
        editTextLongitude = findViewById(R.id.editTextLongitude);

        database = TreasureDatabase.getInstance(this);
        databaseDao = database.databaseDao();

        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

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

    public void saveToDatabase(View view) {
        String name = editTextName.getText().toString().trim();
        String latitudeStr = editTextLatitude.getText().toString().trim();
        String longitudeStr = editTextLongitude.getText().toString().trim();

        if (name.isEmpty() || latitudeStr.isEmpty() || longitudeStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double latitude = Double.parseDouble(latitudeStr);
            double longitude = Double.parseDouble(longitudeStr);

            Treasure treasure = new Treasure(name, latitude, longitude);

            InsertAsyncTask task = new InsertAsyncTask(databaseDao);
            task.execute(treasure);

            Toast.makeText(this, "Treasure saved to database", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid latitude or longitude format", Toast.LENGTH_SHORT).show();
        }

        refreshMapWithMarkers();
    }

    private void refreshMapWithMarkers() {
        new FetchDataAndDisplayMarkersTask().execute();
    }

    // AsyncTask to fetch data from the database and display markers on the map
    private class FetchDataAndDisplayMarkersTask extends AsyncTask<Void, Void, List<Treasure>> {

        @Override
        protected List<Treasure> doInBackground(Void... voids) {
            // Fetch data from the Room database
            return TreasureDatabase.databaseDao().getAllTreasures();
        }

        @Override
        protected void onPostExecute(List<Treasure> treasures) {
            super.onPostExecute(treasures);

            // Clear existing markers on the map
            mMap.clear();

            // Display markers for each treasure retrieved from the database
            for (Treasure treasure : treasures) {
                LatLng location = new LatLng(treasure.getLatitude(), treasure.getLongitude());
                mMap.addMarker(new MarkerOptions().position(location).title(treasure.getName()));
            }
        }
    }

    public void openTreasureList(MenuItem item) {
        Intent intent = new Intent(this, TreasureList.class);
        startActivity(intent);
    }

    public void openLogEntry(MenuItem item) {
        Intent intent = new Intent(this, LogEntry.class);
        startActivity(intent);
    }

    public void MainActivity(View clickedView) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onDrawerToggle(View view) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
