package com.openclassrooms.realestatemanager.models;

import java.util.jar.Attributes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
@Entity(tableName = "attracting_point")
public class AttractingPoint {

    @PrimaryKey(autoGenerate = true)
    private long attractId;
    private String attractType;

    public AttractingPoint() {}

    public AttractingPoint(String attractType) {
        this.attractType = attractType;
    }

    // --- GETTER ---
    public long getAttractId() {
        return attractId;
    }
    public String getAttractType() {
        return attractType;
    }

    // --- SETTER ---
    public void setAttractId(long attractId) {
        this.attractId = attractId;
    }
    public void setAttractType(String attractType) {
        this.attractType = attractType;
    }






}
