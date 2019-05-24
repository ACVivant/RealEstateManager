package com.openclassrooms.realestatemanager.nesertplusariennormalement;

import com.openclassrooms.realestatemanager.nesertplusariennormalement.AttractingPoint;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
@Dao
public interface AttractingPointDao {
    @Insert
    void insertAttractingPoint(AttractingPoint point);

    @Update
    void updateAttractingPoint(AttractingPoint point);
}
