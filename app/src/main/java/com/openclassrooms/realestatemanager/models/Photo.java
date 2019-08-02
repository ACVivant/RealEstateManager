package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Photo model
 */
@Entity(tableName = "photo",
        foreignKeys = @ForeignKey(entity = Property.class,
                parentColumns = "propertyId",
                childColumns = "propertyId"))
public class Photo {

    @PrimaryKey(autoGenerate = true)
    private long photoId;
    private String photoText;
    private String photoUri;
    private long propertyId;

    public Photo() {}

    public Photo (String uri, String text, long propertyId ) {
        this.photoUri = uri;
        this.photoText = text;
        this.propertyId = propertyId;
    }

    // ---- GETTER ---
    public long getPhotoId() {
        return photoId;
    }
    public String getPhotoText() {
        return photoText;
    }
    public String getPhotoUri() {
        return photoUri;
    }
    public long getPropertyId() {
        return propertyId;
    }

    // --- SETTER ---
    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }
    public void setPhotoText(String photoText) {
        this.photoText = photoText;
    }
    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }
}


