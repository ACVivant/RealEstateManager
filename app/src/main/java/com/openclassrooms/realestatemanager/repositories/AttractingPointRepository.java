package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.nesertplusariennormalement.AttractingPointDao;
import com.openclassrooms.realestatemanager.nesertplusariennormalement.AttractingPoint;

/**
 * Created by Anne-Charlotte Vivant on 13/05/2019.
 */

public class AttractingPointRepository {
    private AttractingPointDao attractingPointDao;

    public AttractingPointRepository(AttractingPointDao attractingPointDao) {this.attractingPointDao = attractingPointDao;}

    public void insertAttractingPoint(AttractingPoint attractingPoint) {attractingPointDao.insertAttractingPoint(attractingPoint);}

    public void updateAttractingPoint(AttractingPoint attractingPoint) {attractingPointDao.updateAttractingPoint(attractingPoint);}


    /*public AttractingPointRepository(Application application) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(application);
        attractingPointDao = database.attractingPointDao();
    }

    public void insertAttractingPoint(AttractingPoint attractingPoint) {
        new InsertAttractingPointAsyncTask(attractingPointDao).execute(attractingPoint);
    }

    public void updateAttractingPoint(AttractingPoint attractingPoint) {
        new UpdateAttractingPointAsyncTask(attractingPointDao).execute(attractingPoint);
    }

    private static class InsertAttractingPointAsyncTask extends AsyncTask<AttractingPoint, Void, Void> {
        private AttractingPointDao attractingPointDao;

        private InsertAttractingPointAsyncTask(AttractingPointDao attractingPointDao) {
            this.attractingPointDao = attractingPointDao;
        }

        @Override
        protected Void doInBackground(AttractingPoint... attractingPoints) {
            attractingPointDao.insertAttractingPoint(attractingPoints[0]);
            return null;
        }
    }

    private static class UpdateAttractingPointAsyncTask extends AsyncTask<AttractingPoint, Void, Void> {
        private AttractingPointDao attractingPointDao;

        private UpdateAttractingPointAsyncTask(AttractingPointDao attractingPointDao) {
            this.attractingPointDao = attractingPointDao;
        }

        @Override
        protected Void doInBackground(AttractingPoint... attractingPoints) {
            attractingPointDao.updateAttractingPoint(attractingPoints[0]);
            return null;
        }
    }*/
}