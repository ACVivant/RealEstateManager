package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

import androidx.room.Dao;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

/**
 * Created by Anne-Charlotte Vivant on 29/05/2019.
 */
@Dao
public interface RawDao {
    @RawQuery
    List<Property> getFilteredProperties(SupportSQLiteQuery query);
}
