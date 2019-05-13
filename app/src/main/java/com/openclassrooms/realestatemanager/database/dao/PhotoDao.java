package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
@Dao
public interface PhotoDao {

    @Query("SELECT * FROM photo WHERE property = :property ORDER BY photoText ASC")
    LiveData<List<Photo>> getPhotoFromProperty(int property );

    @Query("DELETE FROM photo WHERE photoId = :photoId")
    void deletePhoto(int photoId);

    @Insert
    void insertPhoto(Photo photo);

    @Update
    void updatePhoto(Photo photo);

}
