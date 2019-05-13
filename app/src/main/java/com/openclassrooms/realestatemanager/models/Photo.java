package com.openclassrooms.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
@Entity(tableName = "photo",
        foreignKeys = @ForeignKey(entity = Property.class,
        parentColumns = "propertyId",
        childColumns = "property"))
public class Photo {

    @PrimaryKey(autoGenerate = true)
    private long photoId;
    private String photoText;
    private String photoUri;
    private long property;

    public Photo (String uri, String text, long property ) {
        this.photoUri = uri;
        this.photoText = text;
        this.property = property;
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
    public long getProperty() {
        return property;
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
    public void setProperty(long property) {
        this.property = property;
    }
}
