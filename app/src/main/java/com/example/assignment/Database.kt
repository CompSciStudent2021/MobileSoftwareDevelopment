/*package com.example.assignment

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [LogEntry::class], version = 1)
abstract class LogEntryDatabase : RoomDatabase() {
    abstract fun logEntryDao(): DatabaseDao // Replace LogEntryDao with your actual DAO interface
}

class LogEntryActivity : AppCompatActivity() {
    private lateinit var db: LogEntryDatabase // Corrected the database type

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
            val treasureName = editTextTreasureName.text.toString()
            val descriptionText = editTextDescriptionText.text.toString()
            val cluesText = editTextCluesText.text.toString()

            // Save log entry to Room database
            val logEntry = LogEntry(treasureName = String, descriptionText = String, cluesText = String)
            saveLogEntry(logEntry)
        }
    }

    private fun saveLogEntry(logEntry: LogEntry) {
        CoroutineScope(Dispatchers.IO).launch {
            db.logEntryDao().insertLogEntry(logEntry)
        }
    }
}
*/