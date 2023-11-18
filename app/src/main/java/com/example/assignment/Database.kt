package com.example.assignment

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Database
import androidx.room.Room
import com.example.assignment.ui.theme.LogEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogEntryActivity : AppCompatActivity() {
    private lateinit var db: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_entry)

        // Initialize Room database
        db = Room.databaseBuilder(
            applicationContext,
            LogEntryDatabase::class.java, "log-entry-db"
        ).build()

        // Retrieve UI elements
        val editTextTreasureName: EditText = findViewById(R.id.editTextTreasureName)

        val editTextDescriptionText: EditText = findViewById(R.id.editTextDescriptionText)

        val editTextCluesText: EditText = findViewById(R.id.editTextCluesText)

        val buttonSaveLog: Button = findViewById(R.id.buttonSaveLog)
        buttonSaveLog.setOnClickListener {
            val treasureNameFound = editTextTreasureName.text.toString()
            val treasureDescriptionFound = editTextDescriptionText.text.toString()
            val treasureCluesFound = editTextCluesText.text.toString()

            // Save log entry to Room database
            saveLogEntry(LogEntry(treasureName = String, descriptionText = String, cluesText = String))
        }
    }

    private fun saveLogEntry(logEntry: LogEntry) {
        CoroutineScope(Dispatchers.IO).launch {
            db.logEntryDao().insertLogEntry(logEntry)
        }
    }
}
