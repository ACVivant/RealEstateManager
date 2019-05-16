package com.openclassrooms.realestatemanager.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.database.dao.AttractingPropertyJoinDao;
import com.openclassrooms.realestatemanager.models.AttractingPropertyJoin;

/**
 * Created by Anne-Charlotte Vivant on 14/05/2019.
 */
public class AttractingPropertyJoinRepository {
    private AttractingPropertyJoinDao attractingPropertyJoinDao;

    public AttractingPropertyJoinRepository(Application application) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(application);
        attractingPropertyJoinDao = database.attractingPropertyJoinDao();
    }

    public void insertAttractingPropertyJoin(AttractingPropertyJoin attractingPropertyJoin) {
        new InsertAttractingPropertyJoinAsyncTask(attractingPropertyJoinDao).execute(attractingPropertyJoin);
    }

    public void updateAttractingPropertyJoins(AttractingPropertyJoin attractingPropertyJoin) {
        new UpdateAttractingPropertyJoinAsyncTask(attractingPropertyJoinDao).execute(attractingPropertyJoin);
    }

    public void deleteAttractingPropertyJoins(AttractingPropertyJoin attractingPropertyJoin) {
        new DeleteAttractingPropertyJoinAsyncTask(attractingPropertyJoinDao).execute(attractingPropertyJoin);
    }

    private static class InsertAttractingPropertyJoinAsyncTask extends AsyncTask<AttractingPropertyJoin, Void, Void> {
        private AttractingPropertyJoinDao attractingPropertyJoinDao;

        private InsertAttractingPropertyJoinAsyncTask(AttractingPropertyJoinDao attractingPropertyJoinDao) {
            this.attractingPropertyJoinDao = attractingPropertyJoinDao;
        }

        @Override
        protected Void doInBackground(AttractingPropertyJoin... attractingPropertyJoins) {
            attractingPropertyJoinDao.insertAttractingPropertyJoin(attractingPropertyJoins[0]);
            return null;
        }
    }

    private static class UpdateAttractingPropertyJoinAsyncTask extends AsyncTask<AttractingPropertyJoin, Void, Void> {
        private AttractingPropertyJoinDao attractingPropertyJoinDao;

        private UpdateAttractingPropertyJoinAsyncTask(AttractingPropertyJoinDao attractingPropertyJoinDao) {
            this.attractingPropertyJoinDao = attractingPropertyJoinDao;
        }

        @Override
        protected Void doInBackground(AttractingPropertyJoin... attractingPropertyJoins) {
            attractingPropertyJoinDao.updateAttractingPropertyJoin(attractingPropertyJoins[0]);
            return null;
        }
    }

    private static class DeleteAttractingPropertyJoinAsyncTask extends AsyncTask<AttractingPropertyJoin, Void, Void> {
        private AttractingPropertyJoinDao attractingPropertyJoinDao;

        private DeleteAttractingPropertyJoinAsyncTask(AttractingPropertyJoinDao attractingPropertyJoinDao) {
            this.attractingPropertyJoinDao = attractingPropertyJoinDao;
        }

        @Override
        protected Void doInBackground(AttractingPropertyJoin... attractingPropertyJoins) {
            attractingPropertyJoinDao.deleteAttractingPropertyJoin(attractingPropertyJoins[0]);
            return null;
        }
    }
}