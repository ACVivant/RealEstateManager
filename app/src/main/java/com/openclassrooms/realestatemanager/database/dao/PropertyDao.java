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

    @Query("SELECT * FROM property WHERE agentName = :agentName ORDER BY soldOnDate DESC")
    LiveData<List<Property>> getPropertyFromAgent(String agentName);

    @Query("SELECT * FROM property WHERE typeText = :type ORDER BY soldOnDate DESC")
    LiveData<List<Property>> getPropertyFromType(String type);

    @Query("SELECT * FROM property WHERE town = :town ORDER BY soldOnDate DESC")
    LiveData<List<Property>> getPropertyFromTown(String town);

    @Query("SELECT * FROM property WHERE zipcode = :zipcode ORDER BY soldOnDate DESC")
    LiveData<List<Property>> getPropertyFromZipCode(String zipcode);

    @Query("SELECT * FROM property WHERE price BETWEEN  :minPrice AND :maxPrice ORDER BY price ASC")
    LiveData<List<Property>> getPropertyFromPrice(int minPrice, int maxPrice );

    @Query("SELECT * FROM property WHERE price > :minPrice ORDER BY price ASC")
    LiveData<List<Property>> getPropertyFromPriceMin(int minPrice );

    @Query("SELECT * FROM property WHERE price < :maxPrice ORDER BY price ASC")
    LiveData<List<Property>> getPropertyFromPriceMax(int maxPrice );

    @Query("SELECT * FROM property WHERE surface BETWEEN  :minSurf AND :maxSurf ORDER BY surface DESC")
    LiveData<List<Property>> getPropertyFromSurface(int minSurf, int maxSurf );

    @Query("SELECT * FROM property WHERE surface > :minSurf ORDER BY surface DESC")
    LiveData<List<Property>> getPropertyFromSurfaceMin(int minSurf );

    @Query("SELECT * FROM property WHERE surface < :maxSurf ORDER BY surface DESC")
    LiveData<List<Property>> getPropertyFromSurfMax(int maxSurf );

    @Query("SELECT * FROM property WHERE rooms > :minRooms ORDER BY rooms DESC")
    LiveData<List<Property>> getPropertyFromRooms(int minRooms );

    @Query("SELECT * FROM property WHERE bedrooms > :minBedrooms ORDER BY bedrooms DESC")
    LiveData<List<Property>> getPropertyFromBedrooms(int minBedrooms );

    @Query("SELECT * FROM property WHERE bathroom > :minBathrooms ORDER BY bathroom DESC")
    LiveData<List<Property>> getPropertyFromBathrooms(int minBathrooms );

    @Query("SELECT * FROM property WHERE upForSaleDate BETWEEN  :minUpForSale AND :maxUpForSale ORDER BY soldOnDate DESC")
    LiveData<List<Property>> getPropertyFromUpForSaleBetween(int minUpForSale, int maxUpForSale );

    @Query("SELECT * FROM property WHERE upForSaleDate > :upforsale ORDER BY soldOnDate DESC")
    LiveData<List<Property>> getPropertyFromUpForSaleDateAfter(int upforsale );

    @Query("SELECT * FROM property WHERE upForSaleDate < :upforsale ORDER BY soldOnDate DESC")
    LiveData<List<Property>> getPropertyFromUpForSaleDateBefore(int upforsale );

    @Query("SELECT * FROM property WHERE soldOnDate BETWEEN  :minSoldOn AND :maxSoldOn ORDER BY soldOnDate DESC")
    LiveData<List<Property>> getPropertyFromSoldOnBetween(int minSoldOn, int maxSoldOn );

    @Query("SELECT * FROM property WHERE soldOnDate > :soldOn ORDER BY soldOnDate DESC")
    LiveData<List<Property>> getPropertyFromSoldOnDateAfter(int soldOn );

    @Query("SELECT * FROM property WHERE soldOnDate < :soldOn ORDER BY soldOnDate DESC")
    LiveData<List<Property>> getPropertyFromSoldOnDateBefore(int soldOn );

    @Insert
    void insertProperty(Property property);

    @Update
    void updateProperty(Property property);
}