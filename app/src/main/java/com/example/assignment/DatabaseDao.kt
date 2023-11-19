/*package com.example.assignment

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLogEntry(Treasure: LogEntry)

    @Query("SELECT * FROM Treasure")
    fun getAll(): LiveData<List<Treasure>>

    @Query("DELETE FROM Treasure WHERE Treasure = :selected")
    fun deleteMovies(watched: Boolean)

    @Update
    fun updateMovie(Treasure: Treasure)

    @Query("DELETE FROM Treasure WHERE id = :id")
    fun delete(id: Int?)
}*/