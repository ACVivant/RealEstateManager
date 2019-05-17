package com.openclassrooms.realestatemanager.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.database.dao.TypeOfPropertyDao;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Created by Anne-Charlotte Vivant on 13/05/2019.
 */
public class TypeOfPropertyRepository {

        private TypeOfPropertyDao typeDao;

        public TypeOfPropertyRepository(Application application) {
            RealEstateDatabase database = RealEstateDatabase.getInstance(application);
            typeDao = database.typeOfPropertyDao();
        }

        public void insertTypeOfProperty(TypeOfProperty type) {
            new InsertTypeOfPropertyAsyncTask(typeDao).execute(type);
        }

        public void updateTypeOfProperty(TypeOfProperty type) {
            new UpdateTypeOfPropertyAsyncTask(typeDao).execute(type);
        }

        public TypeOfProperty getTypeFromId(int typeId) { return typeDao.getTypeFromId(typeId);}

        public TypeOfProperty getTypeFromName(String typeName) { return typeDao.getTypeFromName(typeName);}

        private static class InsertTypeOfPropertyAsyncTask extends AsyncTask<TypeOfProperty, Void, Void> {
            private TypeOfPropertyDao typeDao;

            private InsertTypeOfPropertyAsyncTask(TypeOfPropertyDao typeDao){
                this.typeDao = typeDao;
            }

            @Override
            protected Void doInBackground(TypeOfProperty... typeOfProperties) {
                typeDao.insertType(typeOfProperties[0]);
                return null;
            }
        }

        private static class UpdateTypeOfPropertyAsyncTask extends AsyncTask<TypeOfProperty, Void, Void>{
            private TypeOfPropertyDao typeDao;

            private UpdateTypeOfPropertyAsyncTask(TypeOfPropertyDao typeDao){
                this.typeDao = typeDao;
            }

            @Override
            protected Void doInBackground(TypeOfProperty... typeOfProperties) {
                typeDao.updateType(typeOfProperties[0]);
                return null;
            }
        }

}
