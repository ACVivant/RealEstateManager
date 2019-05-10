package com.openclassrooms.realestatemanager.database.dao;

import android.content.ClipData;

import com.openclassrooms.realestatemanager.models.Property;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */

// Comment rédiger les Query pour les AttractingPoint
@Dao
public interface PropertyDao {

    @Query("SELECT * FROM Property WHERE agentName = :agentName")
    LiveData<List<Property>> getPropertyFromAgent(String agentName);

    @Query("SELECT * FROM Property WHERE typeText = :type")
    LiveData<List<Property>> getPropertyFromType(String type);

    @Query("SELECT * FROM Property WHERE town = :town")
    LiveData<List<Property>> getPropertyFromTown(String town);

    @Query("SELECT * FROM Property WHERE zipcode = :zipcode")
    LiveData<List<Property>> getPropertyFromZipCode(String zipcode);

    @Query("SELECT * FROM Property WHERE price BETWEEN  :minPrice AND :maxPrice")
    LiveData<List<Property>> getPropertyFromPrice(int minPrice, int maxPrice );

    @Query("SELECT * FROM Property WHERE price > :minPrice")
    LiveData<List<Property>> getPropertyFromPriceMin(int minPrice );

    @Query("SELECT * FROM Property WHERE price < :maxPrice")
    LiveData<List<Property>> getPropertyFromPriceMax(int maxPrice );

    @Query("SELECT * FROM Property WHERE surface BETWEEN  :minSurf AND :maxSurf")
    LiveData<List<Property>> getPropertyFromSurface(int minSurf, int maxSurf );

    @Query("SELECT * FROM Property WHERE surface > :minSurf")
    LiveData<List<Property>> getPropertyFromSurfaceMin(int minSurf );

    @Query("SELECT * FROM Property WHERE surface < :maxSurf")
    LiveData<List<Property>> getPropertyFromSurfMax(int maxSurf );

    @Query("SELECT * FROM Property WHERE rooms > :minRooms")
    LiveData<List<Property>> getPropertyFromRooms(int minRooms );

    @Query("SELECT * FROM Property WHERE bedrooms > :minBedrooms")
    LiveData<List<Property>> getPropertyFromBedrooms(int minBedrooms );

    @Query("SELECT * FROM Property WHERE bathroom > :minBathrooms")
    LiveData<List<Property>> getPropertyFromBathrooms(int minBathrooms );

    @Query("SELECT * FROM Property WHERE upForSaleDate BETWEEN  :minUpForSale AND :maxUpForSale")
    LiveData<List<Property>> getPropertyFromUpForSaleBetween(int minUpForSale, int maxUpForSale );

    @Query("SELECT * FROM Property WHERE upForSaleDate > :upforsale")
    LiveData<List<Property>> getPropertyFromUpForSaleDateAfter(Date upforsale );

    @Query("SELECT * FROM Property WHERE upForSaleDate < :upforsale")
    LiveData<List<Property>> getPropertyFromUpForSaleDateBefore(Date upforsale );

    @Query("SELECT * FROM Property WHERE soldOnDate BETWEEN  :minSoldOn AND :maxSoldOn")
    LiveData<List<Property>> getPropertyFromSoldOnBetween(int minSoldOn, int maxSoldOn );

    @Query("SELECT * FROM Property WHERE soldOnDate > :soldOn")
    LiveData<List<Property>> getPropertyFromSoldOnDateAfter(Date soldOn );

    @Query("SELECT * FROM Property WHERE soldOnDate < :soldOn")
    LiveData<List<Property>> getPropertyFromSoldOnDateBefore(Date soldOn );

    @Insert
    long insertProperty(Property property);

    @Update
    int updateProperty(Property property);
}
