package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Photo;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PhotoDao {

    @Query("SELECT * FROM photo WHERE propertyId = :property ORDER BY photoText ASC")
    LiveData<List<Photo>> getPhotoFromProperty(int property );

    @Query("SELECT * FROM photo ORDER BY propertyId ASC")
    LiveData<List<Photo>> getAllPhoto();

    @Insert
    void insertPhoto(Photo photo);

    @Update
    void updatePhoto(Photo photo);

    @Delete
    void deletePhoto(Photo photo);

    @Query("SELECT * FROM photo WHERE photoId = :photoId")
    LiveData<Photo> getPhotoFromId(long photoId);

}
