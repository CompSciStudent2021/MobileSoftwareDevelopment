package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TreasureList extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private RecyclerView recyclerView;
    private TreasureAdapter adapter;
    private TreasureDatabase database;
    private DatabaseDao databaseDao;
    private ExecutorService executorService;

    private List<Treasure> treasureList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_treasure);

        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        // Correct RecyclerView initialization and setting LayoutManager
        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = TreasureDatabase.getInstance(this);
        databaseDao = database.databaseDao();
        executorService = Executors.newSingleThreadExecutor();

        navView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.button_treasure_list) {
                startActivity(new Intent(getApplicationContext(), TreasureList.class));
            } else if (id == R.id.button_log_entry) {
                startActivity(new Intent(getApplicationContext(), LogEntry.class));
            } else if (id == R.id.MainActivity) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            return true;
        });

        executorService.execute(() -> {
            treasureList = databaseDao.getAllTreasures();

            runOnUiThread(() -> {
                // Initialize adapter and set it to RecyclerView
                adapter = new TreasureAdapter(this, treasureList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
