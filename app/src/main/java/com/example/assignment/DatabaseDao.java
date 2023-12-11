package com.example.assignment;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DatabaseDao {
    @Query("SELECT * FROM treasures")
    List<Treasure> getAllTreasures();

    @Insert
    void insert(Treasure treasure);
}