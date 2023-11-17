package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.view.View
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

class MainActivity<Treasure> : AppCompatActivity(), OnMapReadyCallback {

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
                R.id.nav_item1 -> startActivity(Intent(this, ActivityOne::class.java))
                R.id.nav_item2 -> startActivity(Intent(this, ActivityTwo::class.java))
                R.id.nav_item3 -> startActivity(Intent(this, ActivityThree::class.java))
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

    // Function to start TreasureDetailsActivity with data...
    fun startTreasureDetails(selectedTreasure: Treasure) {
        val intent = Intent(this, TreasureDetailsActivity::class.java)
        intent.putExtra("treasureName", selectedTreasure.name)
        intent.putExtra("descriptionText", selectedTreasure.description)
        intent.putExtra("cluesText", selectedTreasure.clues)
        startActivity(intent)
}

class ActivityThree {

}

class ActivityTwo {

}

class ActivityOne {

}
}
