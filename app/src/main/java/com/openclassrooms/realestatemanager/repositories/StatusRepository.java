package com.openclassrooms.realestatemanager.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.database.dao.StatusDao;
import com.openclassrooms.realestatemanager.models.Status;

/**
 * Created by Anne-Charlotte Vivant on 13/05/2019.
 */
public class StatusRepository {

        private StatusDao statusDao;

        public StatusRepository(Application application) {
            RealEstateDatabase database = RealEstateDatabase.getInstance(application);
            statusDao = database.statusDao();
        }

        public void insertStatus(Status status) {
            new InsertStatusAsyncTask(statusDao).execute(status);
        }

        public void updateStatus(Status status) {
            new UpdateStatusAsyncTask(statusDao).execute(status);
        }

        public void getStatus(int statusId) { statusDao.getStatusFromId(statusId);}

        private static class InsertStatusAsyncTask extends AsyncTask<Status, Void, Void> {
            private StatusDao statusDao;

            private InsertStatusAsyncTask(StatusDao statusDao){
                this.statusDao = statusDao;
            }

            @Override
            protected Void doInBackground(com.openclassrooms.realestatemanager.models.Status... statuses) {
                statusDao.insertStatus(statuses[0]);
                return null;
            }
        }

        private static class UpdateStatusAsyncTask extends AsyncTask<Status, Void, Void>{
            private StatusDao statusDao;

            private UpdateStatusAsyncTask(StatusDao statusDao){
                this.statusDao = statusDao;
            }

            @Override
            protected Void doInBackground(com.openclassrooms.realestatemanager.models.Status... statuses) {
                statusDao.updateStatus(statuses[0]);
                return null;
            }
        }
}
