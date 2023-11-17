package com.example.assignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing the map fragment
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
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
}
