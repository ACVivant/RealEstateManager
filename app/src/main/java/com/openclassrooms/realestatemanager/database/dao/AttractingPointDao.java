package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.AttractingPoint;
import com.openclassrooms.realestatemanager.models.Status;

import org.w3c.dom.Attr;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
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
