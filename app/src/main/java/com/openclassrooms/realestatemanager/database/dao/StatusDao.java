package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Status;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
public interface StatusDao {
    @Insert
    long insertStatus(Status status);

    @Update
    int updateStatus(Status status);

}
