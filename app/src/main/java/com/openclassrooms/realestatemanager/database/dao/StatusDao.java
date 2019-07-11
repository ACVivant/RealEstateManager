package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Status;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface StatusDao {
    @Insert
    void insertStatus(Status status);

    @Update
    void updateStatus(Status status);

    @Query("SELECT * FROM status WHERE statusId = :statusId")
    LiveData<Status> getStatusFromId(int statusId);

    @Query("SELECT * FROM status WHERE statusText = :statusName")
    LiveData<Status> getStatusFromName(String statusName);
}
