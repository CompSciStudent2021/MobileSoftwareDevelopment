package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class LogEntry extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_entry);

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
