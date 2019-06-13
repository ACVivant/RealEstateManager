package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.StatusDao;
import com.openclassrooms.realestatemanager.models.Status;

import androidx.lifecycle.LiveData;

/**
 * Created by Anne-Charlotte Vivant on 13/05/2019.
 */
public class StatusRepository {

    private StatusDao statusDao;

    public StatusRepository(StatusDao statusDao) {this.statusDao = statusDao;}

    public void insertStatus(Status status) {statusDao.insertStatus(status);}

    public void updateStatus(Status status) {statusDao.updateStatus(status);}

    public LiveData<Status> getStatusFromId(int statusId) { return statusDao.getStatusFromId(statusId);}

    public LiveData<Status> getStatusFromName(String statusName) { return statusDao.getStatusFromName(statusName);}

}
