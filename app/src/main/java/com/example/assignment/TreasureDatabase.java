package com.example.assignment;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Treasure.class}, version = 2)
public abstract class TreasureDatabase extends RoomDatabase {
    private static TreasureDatabase INSTANCE;

    public static DatabaseDao databaseDao() // Change 'Database' to 'DatabaseDao'
    {
        return null;
    }

    // Define your migrations
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };

    public static synchronized TreasureDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TreasureDatabase.class, "treasure_database")
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
