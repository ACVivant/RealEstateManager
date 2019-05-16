package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Status;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
@Dao
public interface StatusDao {
    @Insert
    void insertStatus(Status status);

    @Update
    void updateStatus(Status status);

    @Query("SELECT * FROM status WHERE statusId = :statusId")
    Status getStatusFromId(int statusId);

}
