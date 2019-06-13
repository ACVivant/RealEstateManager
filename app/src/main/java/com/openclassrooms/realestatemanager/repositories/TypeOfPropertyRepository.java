package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.TypeOfPropertyDao;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Created by Anne-Charlotte Vivant on 13/05/2019.
 */
public class TypeOfPropertyRepository {

        private TypeOfPropertyDao typeDao;

    public TypeOfPropertyRepository(TypeOfPropertyDao statusDao) {this.typeDao = statusDao;}

    public void insertTypeOfProperty(TypeOfProperty type) {typeDao.insertType(type);}

    public void updateTypeOfProperty(TypeOfProperty type) {typeDao.updateType(type);}

    public LiveData<TypeOfProperty> getTypeFromId(int typeId) { return typeDao.getTypeFromId(typeId);}

    public LiveData<TypeOfProperty> getTypeFromName(String typeName) { return typeDao.getTypeFromName(typeName);}

    public LiveData<List<TypeOfProperty>> getAllType() {
        return typeDao.getAllTypes();
    }

}
