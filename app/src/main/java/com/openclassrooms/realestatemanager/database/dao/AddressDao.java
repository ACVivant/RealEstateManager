package com.openclassrooms.realestatemanager.database.dao;

import com.openclassrooms.realestatemanager.models.Address;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
public interface AddressDao {

    @Insert
    long insertAddress(Address address);

    @Update
    int updateAddress(Address address);

    @Query("DELETE FROM Address WHERE addressId = :addressId")
    int deleteAddress(int addressId);
}
