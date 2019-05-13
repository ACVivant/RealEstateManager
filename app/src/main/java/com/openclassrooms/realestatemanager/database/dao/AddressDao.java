package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Address;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
@Dao
public interface AddressDao {

    @Insert
    void insertAddress(Address address);

    @Update
    void updateAddress(Address address);

    @Query("DELETE FROM address WHERE addressId = :addressId")
    int deleteAddress(int addressId);
}
