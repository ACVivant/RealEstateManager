package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

/**
 * Property Repository
 */

public class PropertyRepository {
    private PropertyDao propertyDao;

    public PropertyRepository(PropertyDao propertyDao) {this.propertyDao = propertyDao;}

    public long insertProperty(Property property) {return propertyDao.insertProperty(property); }

    public void updateProperty(Property property) {propertyDao.updateProperty(property);}

    // --- GET ---

    public LiveData<List<Property>> getAllProperty() {
        return propertyDao.getAllProperty();
    }

    public LiveData<Property> getPropertyFromId(int propertyId) { return propertyDao.getPropertyFromId(propertyId);}

    public LiveData<List<Property>> getFilteredProperties(SupportSQLiteQuery query) {return propertyDao.getFilteredProperties(query);}

}
