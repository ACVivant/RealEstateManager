package com.openclassrooms.realestatemanager.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.database.dao.AddressDao;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Anne-Charlotte Vivant on 13/05/2019.
 */
public class AddressRepository {
    private AddressDao addressDao;

    public AddressRepository(Application application) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(application);
        addressDao = database.addressDao();
    }

    public void insertAddress(Address address) {
        new InsertAddressAsyncTask(addressDao).execute(address);
    }

    public void updateAddress(Address address) {
        new UpdateAddressAsyncTask(addressDao).execute(address);
    }

    public void deleteAddress(Address address) {
        new DeleteAddressAsyncTask(addressDao).execute(address);
    }

    public Address getAddressFromId(int addressId) { return addressDao.getAddressFromId(addressId);}

    public LiveData<List<Address>> getAllAddresses() {
        return addressDao.getAllAddresses();
    }

    private static class InsertAddressAsyncTask extends AsyncTask<Address, Void, Void>{
        private AddressDao addressDao;

        private InsertAddressAsyncTask(AddressDao addressDao){
            this.addressDao = addressDao;
        }

        @Override
        protected Void doInBackground(Address... addresses) {
            addressDao.insertAddress(addresses[0]);
            return null;
        }
    }

    private static class UpdateAddressAsyncTask extends AsyncTask<Address, Void, Void>{
        private AddressDao addressDao;

        private UpdateAddressAsyncTask(AddressDao addressDao){
            this.addressDao = addressDao;
        }

        @Override
        protected Void doInBackground(Address... addresses) {
            addressDao.updateAddress(addresses[0]);
            return null;
        }
    }

    private static class DeleteAddressAsyncTask extends AsyncTask<Address, Void, Void>{
        private AddressDao addressDao;

        private DeleteAddressAsyncTask(AddressDao addressDao){
            this.addressDao = addressDao;
        }

        @Override
        protected Void doInBackground(Address... addresses) {
            addressDao.deleteAddress(addresses[0]);
            return null;
        }
    }
}
