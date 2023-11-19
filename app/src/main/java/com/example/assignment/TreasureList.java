package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class TreasureList extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_list);

        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.button_treasure_list) {
                    startActivity(new Intent(getApplicationContext(), TreasureList.class));
                } else if (id == R.id.button_log_entry) {
                    startActivity(new Intent(getApplicationContext(), LogEntry.class));
                } else if (id == R.id.MainActivity) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                return true;
            }
        });
    }

    public void onDrawerToggle(View view) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
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

}
