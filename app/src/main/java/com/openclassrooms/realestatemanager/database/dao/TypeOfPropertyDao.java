package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
public interface TypeOfPropertyDao {
    @Insert
    long insertType(TypeOfProperty type);

    @Update
    int updateType(TypeOfProperty type);

}
