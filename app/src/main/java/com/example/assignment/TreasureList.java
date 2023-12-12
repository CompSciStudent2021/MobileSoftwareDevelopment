package com.example.assignment;

import android.content.Intent;
import android.os.AsyncTask;
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
import androidx.room.Room;

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

    List<Treasure> treasureList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_list);

        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TreasureAdapter(this, treasureList);
        recyclerView.setAdapter(adapter);

        database = Room.databaseBuilder(getApplicationContext(), TreasureDatabase.class, "treasure_database").build();
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

        retrieveAndDisplayTreasures();
    }

    private void retrieveAndDisplayTreasures() {
        if (databaseDao != null) {
            new RetrieveTreasuresAsyncTask().execute();
        }
    }

    private class RetrieveTreasuresAsyncTask extends AsyncTask<Void, Void, List<Treasure>> {
        @Override
        protected List<Treasure> doInBackground(Void... voids) {
            return databaseDao.getAllTreasures();
        }

        @Override
        protected void onPostExecute(List<Treasure> treasures) {
            super.onPostExecute(treasures);
            if (treasures != null) {
                treasureList.clear();
                treasureList.addAll(treasures);
                adapter.notifyDataSetChanged();
            }
        }
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