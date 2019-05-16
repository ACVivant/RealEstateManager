package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
@Dao
public interface TypeOfPropertyDao {
    @Insert
    void insertType(TypeOfProperty type);

    @Update
    void updateType(TypeOfProperty type);

    @Query("SELECT * FROM type_of_property WHERE typeId = :typeId")
    TypeOfProperty getTypeFromId(int typeId);

}
