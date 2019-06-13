package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.AddressDao;
import com.openclassrooms.realestatemanager.models.Address;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Created by Anne-Charlotte Vivant on 13/05/2019.
 */
public class AddressRepository {
    private final AddressDao addressDao;

    public AddressRepository(AddressDao addressDao) {this.addressDao = addressDao;}

    public void insertAddress(Address address) {addressDao.insertAddress(address);}

    public void updateAddress(Address address) {addressDao.updateAddress(address);}

    public void deleteAddress(Address address) {addressDao.deleteAddress(address);}

    // --- GET ---

    public LiveData<Address> getAddressFromId(int addressId) { return addressDao.getAddressFromId(addressId);}

    public LiveData<List<Address>> getAllAddresses() {
        return addressDao.getAllAddresses();

    }
}
