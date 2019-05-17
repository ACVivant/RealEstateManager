package com.openclassrooms.realestatemanager.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.models.Property;

import java.security.Policy;
import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Created by Anne-Charlotte Vivant on 13/05/2019.
 */
public class PropertyRepository {
        private PropertyDao propertyDao;
        private LiveData<List<Property>> listProperty;

        public PropertyRepository(Application application) {
            RealEstateDatabase database = RealEstateDatabase.getInstance(application);
            propertyDao = database.propertyDao();
        }

        public void insertProperty(Property property) {
            new InsertPropertyAsyncTask(propertyDao).execute(property);
        }

        public void updateProperty(Property property) {
            new UpdatePropertyAsyncTask(propertyDao).execute(property);
        }

    public LiveData<List<Property>> getPropertyFromTown(String town) {
        return propertyDao.getPropertyFromTown(town);
    }

    public LiveData<List<Property>> getPropertyFromZipCode(String zipcode) {
        return propertyDao.getPropertyFromZipCode(zipcode);
    }

    public LiveData<List<Property>> getPropertyFromPrice(int minPrice, int maxPrice) {
        return propertyDao.getPropertyFromPrice(minPrice, maxPrice);
    }

    public LiveData<List<Property>> getPropertyFromPriceMin(int minPrice) {
        return propertyDao.getPropertyFromPriceMin(minPrice);
    }

    public LiveData<List<Property>> getPropertyFromPriceMax(int maxPrice) {
        return propertyDao.getPropertyFromPriceMax(maxPrice);
    }

    public LiveData<List<Property>> getPropertyFromSurface(int minSurf, int maxSurf) {
        return propertyDao.getPropertyFromSurface(minSurf, maxSurf);
    }

    public LiveData<List<Property>> getPropertyFromSurfaceMin(int minSurf) {
        return propertyDao.getPropertyFromSurfaceMin(minSurf);
    }

    public LiveData<List<Property>> getPropertyFromSurfMax(int maxSurf ) {
        return propertyDao.getPropertyFromSurfMax(maxSurf);
    }

    public LiveData<List<Property>> getPropertyFromRooms(int minRooms ) {
        return propertyDao.getPropertyFromRooms(minRooms);
    }

    public LiveData<List<Property>> getPropertyFromBedrooms(int minBedrooms ) {
        return propertyDao.getPropertyFromBedrooms(minBedrooms);
    }

    public LiveData<List<Property>> getPropertyFromBathrooms(int minBathrooms ) {
        return propertyDao.getPropertyFromBathrooms(minBathrooms);
    }

    public LiveData<List<Property>> getPropertyFromUpForSaleBetween(int minUpForSale, int maxUpForSale ) {
        return propertyDao.getPropertyFromUpForSaleBetween(minUpForSale,maxUpForSale);
    }

    public LiveData<List<Property>> getPropertyFromUpForSaleDateAfter(int upforsale ) {
        return propertyDao.getPropertyFromUpForSaleDateAfter(upforsale);
    }

    public LiveData<List<Property>> getPropertyFromUpForSaleDateBefore(int upforsale) {
        return propertyDao.getPropertyFromUpForSaleDateBefore(upforsale);
    }

    public LiveData<List<Property>> getPropertyFromSoldOnBetween(int minSoldOn, int maxSoldOn) {
        return propertyDao.getPropertyFromSoldOnBetween(minSoldOn,maxSoldOn);
    }

    public LiveData<List<Property>> getPropertyFromSoldOnDateAfter(int soldOn) {
        return propertyDao.getPropertyFromSoldOnDateAfter(soldOn);
    }

    public LiveData<List<Property>> getPropertyFromSoldOnDateBefore(int soldOn) {
        return propertyDao.getPropertyFromSoldOnDateBefore(soldOn);
    }

    public LiveData<List<Property>> getAllProperty() {
        return propertyDao.getAllProperty();
    }

    public Property getPropertyFromId(int propertyId) { return propertyDao.getPropertyFromId(propertyId);}

//----------------------------------------------------------------------------------------------------------
        private static class InsertPropertyAsyncTask extends AsyncTask<Property, Void, Void> {
            private PropertyDao propertyDao;

            private InsertPropertyAsyncTask(PropertyDao propertyDao){
                this.propertyDao = propertyDao;
            }

            @Override
            protected Void doInBackground(Property... properties) {
                propertyDao.insertProperty(properties[0]);
                return null;
            }
        }

        private static class UpdatePropertyAsyncTask extends AsyncTask<Property, Void, Void>{
            private PropertyDao propertyDao;

            private UpdatePropertyAsyncTask(PropertyDao propertyDao){
                this.propertyDao = propertyDao;
            }

            @Override
            protected Void doInBackground(Property... properties) {
                propertyDao.updateProperty(properties[0]);
                return null;
            }
        }
}
