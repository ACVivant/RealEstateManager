package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
public interface PhotoDao {

    @Query("SELECT * FROM Photo WHERE property > :property")
    LiveData<List<Property>> getPhotoFromProperty(int property );

    @Insert
    long insertPhoto(Photo photo);

    @Update
    int updatePhoto(Photo photo);

    @Query("DELETE FROM Photo WHERE photoId = :photoId")
    int deletePhoto(int photoId);
}
