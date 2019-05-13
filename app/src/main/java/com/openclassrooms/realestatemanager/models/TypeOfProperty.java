package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
@Entity(tableName = "type_of_property")
public class TypeOfProperty {

    @PrimaryKey(autoGenerate = true)
    private long typeId;
    private String typeText;

    public TypeOfProperty(String text) {
        this.typeText = text;
    }

    // --- GETTER ---
    public long getTypeId() {
        return typeId;
    }
    public String getTypeText() {
        return typeText;
    }

    // --- SETTER ---
    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }
    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }





}
