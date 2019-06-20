package com.openclassrooms.realestatemanager.database.dao;

import android.content.ClipData;

import com.openclassrooms.realestatemanager.models.Property;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */

// Comment rédiger les Query pour les AttractingPoint
@Dao
public interface PropertyDao {

    @RawQuery (observedEntities = Property.class)
    LiveData<List<Property>> getFilteredProperties(SupportSQLiteQuery query);

    @Query("SELECT * FROM property ORDER BY propertyId ASC")
    LiveData<List<Property>> getAllProperty();

    @Query("SELECT * FROM property WHERE propertyId = :propertyId")
    LiveData<Property> getPropertyFromId(int propertyId);

    @Insert
    long insertProperty(Property property);

    @Update
    int updateProperty(Property property);
}
