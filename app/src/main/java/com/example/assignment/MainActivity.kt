package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import java.io.BufferedReader
import java.io.InputStreamReader
import com.example.assignment.Treasure
import android.view.View



class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing the map fragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val inputStream = resources.openRawResource(R.raw.wonders)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        reader.readLine() // Read the header line (skip it)

        while (reader.readLine().also { line = it } != null) {
            val tokens = line!!.split(",")
            val name = tokens[0]
            val latitude = tokens[1].toDouble()
            val longitude = tokens[2].toDouble()

            val location = LatLng(latitude, longitude)
            mMap.addMarker(MarkerOptions().position(location).title(name))

            // Enable user interaction like zoom controls, etc.
            mMap.uiSettings.isZoomControlsEnabled = true
        }

        // Set camera to show all markers
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 2f))
    }

    // Function to handle drawer toggle button clicks
    fun onDrawerToggle(view: View) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    // Function to start TreasureDetailsActivity with data
    /*fun startTreasureDetails(selectedTreasure : Treasure) {
        val intent = Intent(this, TreasureDetailsActivity::class.java)
        intent.putExtra("treasureName", selectedTreasure.treasureName)
        intent.putExtra("descriptionText", selectedTreasure.descriptionText)
        intent.putExtra("cluesText", selectedTreasure.cluesText)
        startActivity(intent)
    }*/

    fun openTreasureList(item: MenuItem) {
        val intent = Intent(this, TreasureList::class.java)
        startActivity(intent)
    }

    fun openLogEntry(item: MenuItem) {
        val intent = Intent(this, LogEntry::class.java)
        startActivity(intent)
    }

    fun MainActivity(clickedView: View) {
        val intent = Intent(this, LogEntry::class.java)
        startActivity(intent)
    }

}
