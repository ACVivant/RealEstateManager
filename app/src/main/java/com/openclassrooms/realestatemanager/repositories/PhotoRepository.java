package com.openclassrooms.realestatemanager.repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.database.dao.PhotoDao;
import com.openclassrooms.realestatemanager.models.Photo;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Created by Anne-Charlotte Vivant on 13/05/2019.
 */
public class PhotoRepository {
    private PhotoDao photoDao;
    private LiveData<List<Photo>> allPhoto;
    private LiveData<List<Photo>> allPhotoFromProperty;

    public PhotoRepository(Application application, int idProperty) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(application);
        photoDao = database.photoDao();
        allPhotoFromProperty = photoDao.getPhotoFromProperty(idProperty);
        allPhoto = photoDao.getAllPhoto();
    }
    public PhotoRepository(Application application) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(application);
        photoDao = database.photoDao();
        allPhoto = photoDao.getAllPhoto();
    }

    public void insertPhoto(Photo photo) {
        new InsertPhotoAsyncTask(photoDao).execute(photo);
    }

    public void updatePhoto(Photo photo) {
        new UpdatePhotoAsyncTask(photoDao).execute(photo);
    }

    public void deletePhoto(Photo photo) {
        new DeletePhotoAsyncTask(photoDao).execute(photo);
    }

    public LiveData<List<Photo>> getPhotoFromProperty(int propertyId) {
        return allPhotoFromProperty;
    }
    public LiveData<List<Photo>> getAllPhoto() {
        return allPhoto;
    }

    private static class InsertPhotoAsyncTask extends AsyncTask<Photo, Void, Void> {
        private PhotoDao photoDao;

        private InsertPhotoAsyncTask(PhotoDao photoDao){
            this.photoDao = photoDao;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            photoDao.insertPhoto(photos[0]);
            return null;
        }
    }

    private static class UpdatePhotoAsyncTask extends AsyncTask<Photo, Void, Void> {
        private PhotoDao photoDao;

        private UpdatePhotoAsyncTask(PhotoDao photoDao){
            this.photoDao = photoDao;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            photoDao.updatePhoto(photos[0]);
            return null;
        }
    }

    private static class DeletePhotoAsyncTask extends AsyncTask<Photo, Void, Void> {
        private PhotoDao photoDao;

        private DeletePhotoAsyncTask(PhotoDao photoDao){
            this.photoDao = photoDao;
        }

        @Override
        protected Void doInBackground(Photo... photos) {
            photoDao.deletePhoto(photos[0]);
            return null;
        }
    }


}
