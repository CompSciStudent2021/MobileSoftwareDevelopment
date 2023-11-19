package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.view.View

class LogEntry : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_entry)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        // Handle navigation item clicks
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.button_treasure_list -> startActivity(Intent(this, TreasureList::class.java))
                R.id.button_log_entry -> startActivity(Intent(this, LogEntry::class.java))
                R.id.MainActivity -> startActivity(Intent(this, MainActivity::class.java))
            }
            // Close the drawer when an item is selected
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    fun openTreasureList(item: MenuItem) {
        val intent = Intent(this, TreasureList::class.java)
        startActivity(intent)
    }

    fun openLogEntry(item: MenuItem) {
        val intent = Intent(this, LogEntry::class.java)
        startActivity(intent)
    }

    fun MainActivity(clickedView: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun onDrawerToggle(view: View) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}
