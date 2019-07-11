package com.openclassrooms.realestatemanager.repositories;

import com.openclassrooms.realestatemanager.database.dao.PhotoDao;
import com.openclassrooms.realestatemanager.models.Photo;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Photo Repository
 */

public class PhotoRepository {
    private PhotoDao photoDao;

    public PhotoRepository(PhotoDao photoDao) {this.photoDao = photoDao;}

    public void insertPhoto(Photo photo) {photoDao.insertPhoto(photo);}

    public void updatePhoto(Photo photo) {photoDao.updatePhoto(photo);}

    public void deletePhoto(Photo photo) {photoDao.deletePhoto(photo);}

    // --- GET ---

    public LiveData<List<Photo>> getPhotoFromPropertyId(int propertyId) { return this.photoDao.getPhotoFromProperty(propertyId);}

    public LiveData<Photo> getPhotoFromId(long photoId) { return photoDao.getPhotoFromId(photoId);}

    public LiveData<List<Photo>> getAllPhoto() {
        return this.photoDao.getAllPhoto();
    }

}
