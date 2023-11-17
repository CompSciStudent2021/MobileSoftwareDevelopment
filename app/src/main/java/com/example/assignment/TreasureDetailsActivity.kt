package com.example.assignment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TreasureDetailsActivity : AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_treasure_details)

        // Fetch the selected treasure details from your data source or map markers
        val treasureName = intent.getStringExtra("treasureName")
        val cluesText = intent.getStringExtra("cluesText")
        val descriptionText = intent.getStringExtra("descriptionText")

        // Set the retrieved details to respective TextViews or UI elements
        val nameTextView = findViewById<TextView>(R.id.treasure_name)
        nameTextView.text = treasureName

        val cluesTextView = findViewById<TextView>(R.id.clues_text)
        cluesTextView.text = cluesText

        val descriptionTextView = findViewById<TextView>(R.id.description_text)
        descriptionTextView.text = descriptionText

        // Implement gesture listeners for navigation (e.g., back to map or next screen)
        // For example, to navigate back when a user swipes
        val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val deltaX: Float = e2.x - e1.x
                if (deltaX > 0) {
                    // User swiped from left to right (navigate back)
                    onBackPressed()
                    return true
                }
                return super.onFling(e1, e2, velocityX, velocityY)
            }
        })

        val gestureListener = View.OnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }

        findViewById<View>(R.id.details_layout).setOnTouchListener(gestureListener)
    }
}
