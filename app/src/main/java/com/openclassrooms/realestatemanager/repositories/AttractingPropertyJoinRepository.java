package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.nesertplusariennormalement.AttractingPropertyJoinDao;
import com.openclassrooms.realestatemanager.nesertplusariennormalement.AttractingPropertyJoin;

/**
 * Created by Anne-Charlotte Vivant on 14/05/2019.
 */
public class AttractingPropertyJoinRepository {
    private AttractingPropertyJoinDao attractingPropertyJoinDao;

    public AttractingPropertyJoinRepository(AttractingPropertyJoinDao attractingPropertyJoinDao) {this.attractingPropertyJoinDao = attractingPropertyJoinDao;}

    public void insertAttractingPropertyJoin(AttractingPropertyJoin attractingPropertyJoin) {attractingPropertyJoinDao.insertAttractingPropertyJoin(attractingPropertyJoin);}

    public void updateAttractingPropertyJoin(AttractingPropertyJoin attractingPropertyJoin) {attractingPropertyJoinDao.updateAttractingPropertyJoin(attractingPropertyJoin);}

    public void deleteAttractingPropertyJoin(AttractingPropertyJoin attractingPropertyJoin) {attractingPropertyJoinDao.deleteAttractingPropertyJoin(attractingPropertyJoin);}


   /* public AttractingPropertyJoinRepository(Application application) {
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
    }*/
}