package com.openclassrooms.realestatemanager;

import android.util.Log;

import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.openclassrooms.realestatemanager.repositories.AgentRepository;
import com.openclassrooms.realestatemanager.repositories.PhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.StatusRepository;
import com.openclassrooms.realestatemanager.repositories.TypeOfPropertyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.sqlite.db.SupportSQLiteQuery;

/**
 * Created by Anne-Charlotte Vivant on 16/05/2019.
 */
public class PropertyViewModel extends ViewModel {

    private static final String TAG = "PropertyViewModel";

    // REPOSITORIES
    private PropertyRepository propertyRepository;
    private AgentRepository agentRepository;
    private PhotoRepository photoRepository;
    private StatusRepository statusRepository;
    private TypeOfPropertyRepository typeOfPropertyRepository;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<Property> currentProperty;
    @Nullable
    private LiveData<Agent> currentAgent;
    @Nullable
    private LiveData<Status> currentStatus;
    @Nullable
    private LiveData<TypeOfProperty> currentType;
    @Nullable
    private LiveData<List<Property>> allProperties;
    @Nullable
    private LiveData<List<Address>> allAddresses;
    @Nullable
    private LiveData<List<Photo>> allPhotos;
    @Nullable
    private LiveData<List<Photo>> allPhotosFromProperty;
    @Nullable
    private LiveData<List<TypeOfProperty>> allTypes;

    public PropertyViewModel(PropertyRepository propertyRepository, AgentRepository agentRepository, PhotoRepository photoRepository, StatusRepository statusRepository, TypeOfPropertyRepository typeOfPropertyRepository, Executor executor) {
        this.propertyRepository = propertyRepository;
        this.agentRepository = agentRepository;
        this.photoRepository = photoRepository;
        this.statusRepository = statusRepository;
        this.typeOfPropertyRepository = typeOfPropertyRepository;
        this.executor = executor;
    }

    public void initPropertyFromId(int propertyId) {
        if (this.currentProperty != null) {
            return;
        }
        currentProperty = propertyRepository.getPropertyFromId(propertyId);
    }

    public void initAgentFromId(int agentId) {
        if (this.currentAgent != null) {
            return;
        }
        currentAgent = agentRepository.getAgentFromId(agentId);
    }

    public void initAgentFromName(String agentName) {
        if (this.currentAgent != null) {
            return;
        }
        currentAgent = agentRepository.getAgentFromName(agentName);
    }

    public void initStatusFromId(int statusId) {
        if (this.currentStatus != null) {
            return;
        }
        currentStatus = statusRepository.getStatusFromId(statusId);
    }

    public void initStatusFromName(String statusName) {
        if (this.currentStatus != null) {
            return;
        }
        currentStatus = statusRepository.getStatusFromName(statusName);
    }

    public void initTypeFromId(int typeId) {
        if (this.currentType != null) {
            return;
        }
        currentType = typeOfPropertyRepository.getTypeFromId(typeId);
    }

    public void initTypeFromName(String typeName) {
        if (this.currentType != null) {
            return;
        }
        currentType = typeOfPropertyRepository.getTypeFromName(typeName);
    }

    // For Property
    public LiveData<Property> getPropertyFromId(int propertyId) { return propertyRepository.getPropertyFromId(propertyId);  }
    public LiveData<List<Property>> getAllProperty(){
        return propertyRepository.getAllProperty();
    }

    public void insertProperty(Property property) {
        executor.execute(() -> {
            propertyRepository.insertProperty(property);
        });
    }

    public void updateProperty(Property property) {
        executor.execute(() -> {
            propertyRepository.updateProperty(property);
        });
    }

    public LiveData<List<Property>> getFilteredProperties(SupportSQLiteQuery query) { return propertyRepository.getFilteredProperties(query);}

    public void createProperty (Property myProperty,  ArrayList<String> photosList,  ArrayList<String> legendList, String mainPhotoUri, String mainPhotoLegend) {
        long propertyId = propertyRepository.insertProperty(myProperty);

                    createPhotos(propertyId, photosList, legendList, mainPhotoUri, mainPhotoLegend);
    }

    private void createPhotos(long propertyId, ArrayList<String> photosList,  ArrayList<String> legendList, String mainPhotoUri, String mainPhotoLegend) {
        this.insertPhoto(new Photo(mainPhotoUri, mainPhotoLegend, propertyId));

        for (int i=0; i<photosList.size(); i++) {
            this.insertPhoto(new Photo(photosList.get(i), legendList.get(i), propertyId));
        }
    }

    // For Agent
    public LiveData<Agent> getAgentFromId(int agentId) {
        return agentRepository.getAgentFromId(agentId);
    }

    public LiveData<Agent> getAgentFromName() {
        return this.currentAgent;
    }

    public void insertAgent(Agent agent) {
        executor.execute(() -> {
            agentRepository.insertAgent(agent);
        });
    }

    public void updateAgent(Agent agent) {
        executor.execute(() -> {
            agentRepository.updateAgent(agent);
        });
    }

    // For Photo
    public LiveData<Photo> getPhotoFromId(long photoId) { return photoRepository.getPhotoFromId(photoId);  }
    public LiveData<List<Photo>> getPhotoFromProperty(int propertyId) {return this.photoRepository.getPhotoFromPropertyId(propertyId);}
    public LiveData<List<Photo>> getAllPhotos() { return this.getAllPhotos();}

    public void insertPhoto(Photo photo) {
        executor.execute(() -> {
            photoRepository.insertPhoto(photo);
        });
    }

    public void updatePhoto(Photo photo) {
        executor.execute(() -> {
            photoRepository.updatePhoto(photo);
        });
    }

    public void deletePhoto(Photo photo) {
        executor.execute(() -> {
            photoRepository.deletePhoto(photo);
        });
    }

    // For Status
    public LiveData<Status> getStatusFromId(int statusId) {return statusRepository.getStatusFromId(statusId);}
    public LiveData<Status> getStatusFromName(String statusName) {return this.currentStatus;}

    public void insertStatus(Status status) {
        executor.execute(() -> {
            statusRepository.insertStatus(status);
        });
    }

    public void updateStatus(Status status) {
        executor.execute(() -> {
            statusRepository.updateStatus(status);
        });
    }

    // For TypeOfProperty
    public LiveData<List<TypeOfProperty>> getAllTypes() {return typeOfPropertyRepository.getAllType();}
    public LiveData<TypeOfProperty> getTypeFromId(int typeId) {return typeOfPropertyRepository.getTypeFromId(typeId);}
    public LiveData<TypeOfProperty> getTypeFromName(String typeName) {return this.currentType;}

    public void insertTypeOfProperty(TypeOfProperty type) {
        executor.execute(() -> {
            typeOfPropertyRepository.insertTypeOfProperty(type);
        });
    }

    public void updateTypeOfProperty(TypeOfProperty type) {
        executor.execute(() -> {
            typeOfPropertyRepository.updateTypeOfProperty(type);
        });
    }

}
