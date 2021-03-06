package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Status model
 */
@Entity(tableName = "status")
public class Status {

    @PrimaryKey(autoGenerate = true)
    private long statusId;
    private String statusText;

    public Status() {}

    public Status(String text) {
        this.statusText = text;
    }

    // --- GETTER ---
    public long getStatusId() {
        return statusId;
    }
    public String getStatusText() {
        return statusText;
    }

    // --- SETTER ---
    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }


}
