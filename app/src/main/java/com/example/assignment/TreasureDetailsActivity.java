package com.example.assignment;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;



public class TreasureDetailsActivity extends AppCompatActivity {

    private TreasureDatabase database;
    private DatabaseDao databaseDao;

    private List<Treasure> treasureList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_details);

        // Initialize the database and dao
        database = TreasureDatabase.getInstance(getApplicationContext());
        databaseDao = database.databaseDao();

        // Corrected: Use a List<Treasure> for the adapter
        treasureList = databaseDao.getAllTreasures();
        List<Treasure> treasureList = databaseDao.getAllTreasures();
        for (Treasure retrievedTreasure : treasureList) {
            Log.d("TreasureData", "Name: " + retrievedTreasure.getName() +
                    ", Latitude: " + retrievedTreasure.getLatitude() +
                    ", Longitude: " + retrievedTreasure.getLongitude());
        }

        // Fetch the selected treasure details from your data source or map markers
        String treasureName = getIntent().getStringExtra("treasureName");
        String cluesText = getIntent().getStringExtra("cluesText");
        String descriptionText = getIntent().getStringExtra("descriptionText");

        // Set the retrieved details to respective TextViews or UI elements
        TextView nameTextView = findViewById(R.id.treasure_name);
        nameTextView.setText(treasureName);

        TextView cluesTextView = findViewById(R.id.clues_text);
        cluesTextView.setText(cluesText);

        TextView descriptionTextView = findViewById(R.id.description_text);
        descriptionTextView.setText(descriptionText);

        // Implement gesture listeners for navigation (e.g., back to map or next screen)
        // For example, to navigate back when a user swipes
        GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float deltaX = e2.getX() - e1.getX();
                if (deltaX > 0) {
                    // User swiped from left to right (navigate back)
                    onBackPressed();
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        View.OnTouchListener gestureListener = (v, event) -> gestureDetector.onTouchEvent(event);

        findViewById(R.id.details_layout).setOnTouchListener(gestureListener);
    }
}
