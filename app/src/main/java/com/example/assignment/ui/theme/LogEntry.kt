package com.example.assignment.ui.theme

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "log_entries")
data class LogEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val treasureName: String.Companion,
    val descriptionText: String.Companion,
    val cluesText: String.Companion,)