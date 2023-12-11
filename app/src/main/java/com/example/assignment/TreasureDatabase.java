package com.example.assignment;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;

@Database(entities = {Treasure.class}, version = 1)
public abstract class TreasureDatabase extends RoomDatabase {
    private static TreasureDatabase INSTANCE;

    public abstract DatabaseDao databaseDao();

    public static synchronized TreasureDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TreasureDatabase.class, "treasure_database")
                    .build();
        }
        return INSTANCE;
    }
}
