package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.AttractingPoint;
import com.openclassrooms.realestatemanager.models.AttractingPropertyJoin;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

/**
 * Created by Anne-Charlotte Vivant on 14/05/2019.
 */

@Dao
public interface AttractingPropertyJoinDao {
    @Insert
    void insertAttractingPropertyJoin(AttractingPropertyJoin join);

    @Update
    void updateAttractingPropertyJoin(AttractingPropertyJoin join);

    @Delete
    void deleteAttractingPropertyJoin(AttractingPropertyJoin join);
}