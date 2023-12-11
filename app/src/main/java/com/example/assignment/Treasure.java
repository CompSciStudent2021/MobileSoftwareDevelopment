package com.example.assignment;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "treasures")
public class Treasure{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private static String name;
    private static double latitude;
    private static double longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public static double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}


