package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.nesertplusariennormalement.AttractingPoint;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.openclassrooms.realestatemanager.repositories.AddressRepository;
import com.openclassrooms.realestatemanager.repositories.AgentRepository;
import com.openclassrooms.realestatemanager.repositories.AttractingPointRepository;
import com.openclassrooms.realestatemanager.repositories.PhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.StatusRepository;
import com.openclassrooms.realestatemanager.repositories.TypeOfPropertyRepository;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Anne-Charlotte Vivant on 16/05/2019.
 */
public class PropertyViewModel extends ViewModel {

    // REPOSITORIES
    private PropertyRepository propertyRepository;
    private AddressRepository addressRepository;
    private AgentRepository agentRepository;
    private AttractingPointRepository attractingPointRepository;
    //private AttractingPropertyJoinRepository attractingPropertyJoinRepository;
    private PhotoRepository photoRepository;
    private StatusRepository statusRepository;
    private TypeOfPropertyRepository typeOfPropertyRepository;
    private final Executor executor;

    // DATA
    @Nullable
    private LiveData<Property> currentProperty;
    @Nullable
    private LiveData<Address> currentAddress;
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

    public PropertyViewModel(PropertyRepository propertyRepository, AddressRepository addressRepository, AgentRepository agentRepository, PhotoRepository photoRepository, StatusRepository statusRepository, TypeOfPropertyRepository typeOfPropertyRepository, Executor executor) {
        this.propertyRepository = propertyRepository;
        this.addressRepository = addressRepository;
        this.agentRepository = agentRepository;
        //this.attractingPointRepository = attractingPointRepository;
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

    public void initAddressFromId(int addressId) {
    if (this.currentAddress != null) {
        return;
    }
    currentAddress = addressRepository.getAddressFromId(addressId);
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
   // public LiveData<Property> getPropertyFromId(int propertyId) { return this.currentProperty;  }
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

    // For Address
    public LiveData<List<Address>> getAllAddress() {return addressRepository.getAllAddresses();}
    // public LiveData<Address> getAddressFromId(int addressID){ return this.currentAddress; }
    public LiveData<Address> getAddressFromId(int addressId){ return addressRepository.getAddressFromId(addressId); }

    public void insertAddress(Address address) {
        executor.execute(() -> {
            addressRepository.insertAddress(address);
        });
    }

    public void updateAddress(Address address) {
        executor.execute(() -> {
            addressRepository.updateAddress(address);
        });
    }

    // For Agent
    //public LiveData<Agent> getAgentFromId(int agentId) {return this.currentAgent;}
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

/*    // For AttractingPoint
    public void insertAttractingPoint(AttractingPoint attractingPoint) {
        executor.execute(() -> {
            attractingPointRepository.insertAttractingPoint(attractingPoint);
        });
    }

    public void updateAttractingPoint(AttractingPoint attractingPoint) {
        executor.execute(() -> {
            attractingPointRepository.updateAttractingPoint(attractingPoint);
        });
    }*/

    // For Photo
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
    // public LiveData<Status> getStatusFromId(int statusId) {return this.currentStatus;}
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
    //public LiveData<TypeOfProperty> getTypeFromId(int typeId) {return this.currentType;}
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

/*
public class PropertyViewModel extends AndroidViewModel {

    private static final String TAG = "PropertyViewModel";

    private PropertyRepository propertyRepository;
    private AddressRepository addressRepository;
    private AgentRepository agentRepository;
    private AttractingPointRepository attractingPointRepository;
    private AttractingPropertyJoinRepository attractingPropertyJoinRepository;
    private PhotoRepository photoRepository;
    private StatusRepository statusRepository;
    private TypeOfPropertyRepository typeOfPropertyRepository;

    private LiveData<List<Property>> allProperties;
    private LiveData<List<Address>>  allAddresses;
    private LiveData<List<Photo>> allPhotos;
    private LiveData<List<Photo>> allPhotosFromProperty;

    private Property currentProperty;

    public PropertyViewModel(@NonNull Application application) {
        super(application);
        propertyRepository = new PropertyRepository(application);
        addressRepository = new AddressRepository(application);
        agentRepository = new AgentRepository(application);
        attractingPointRepository = new AttractingPointRepository(application);
        attractingPropertyJoinRepository = new AttractingPropertyJoinRepository(application);
        photoRepository = new PhotoRepository(application);
        statusRepository = new StatusRepository(application);
        typeOfPropertyRepository = new TypeOfPropertyRepository(application);

        allProperties = propertyRepository.getAllProperty();
        allAddresses = addressRepository.getAllAddresses();
        allPhotos = photoRepository.getAllPhoto();
    }

    // For Property
    public void insertProperty(Property property){
        propertyRepository.insertProperty(property);
    }
    public void updateProperty(Property property) {
        propertyRepository.updateProperty(property);
    }

    public LiveData<List<Property>> getAllProperty(){
        return allProperties;
    }

    // For Address
    public void insertAddress(Address address) { addressRepository.insertAddress(address);}
    public void updateAddress(Address address) { addressRepository.updateAddress(address);}
    public void getAddressFromId(int addressId) { addressRepository.getAddressFromId(addressId);}

    public LiveData<List<Address>> getAllAddress() {return allAddresses;}

    // For Agent
    public void insertAgent(Agent agent) { agentRepository.insertAgent(agent);}
    public void updateAgent(Agent agent) { agentRepository.insertAgent(agent);}
    public LiveData<Agent> getAgentFromId(int agentId) {return agentRepository.getAgentFromId(agentId);}
    public LiveData<Agent> getAgentFromName(String agentName) {return agentRepository.getAgentFromName(agentName);}


    // For AttractingPoint
    public void insertAttractingPoint(AttractingPoint attractingPoint) { attractingPointRepository.insertAttractingPoint(attractingPoint);}
    public void updateAttractingPoint(AttractingPoint attractingPoint) { attractingPointRepository.updateAttractingPoint(attractingPoint);}

    // For AttractingPropertyJoin
    public void insertAttractingPropertyJoin(AttractingPropertyJoin attractingPropertyJoin) { attractingPropertyJoinRepository.insertAttractingPropertyJoin(attractingPropertyJoin);}
    public void updateAttracingPropertyJoin(AttractingPropertyJoin attractingPropertyJoin) { attractingPropertyJoinRepository.updateAttractingPropertyJoins(attractingPropertyJoin);}

    // For Photo
    public void insertPhoto(Photo photo) { photoRepository.insertPhoto(photo);}
    public void updatePhoto(Photo photo) { photoRepository.updatePhoto(photo);}
    public void deletePhoto(Photo photo) { photoRepository.deletePhoto(photo);}

    public LiveData<List<Photo>> getPhotoFromProperty(int propertyId) {return photoRepository.getPhotoFromProperty(propertyId);}
    public LiveData<List<Photo>> getAllPhotos() { return photoRepository.getAllPhoto();}

    // For Status
    public void insertStatus(Status status) {statusRepository.insertStatus(status);}
    public void updateStatus(Status status) {statusRepository.updateStatus(status);}
    public LiveData<Status> getStatusFromId(int statusId) {return statusRepository.getStatusFromId(statusId);}
    public LiveData<Status> getStatusFromName(String statusName) {return statusRepository.getStatusFromName(statusName);}

    // For TypeOfProperty
    public void insertTypeOfProperty(TypeOfProperty type) {typeOfPropertyRepository.insertTypeOfProperty(type);}
    public void updateTypeOfProperty(TypeOfProperty type) {typeOfPropertyRepository.updateTypeOfProperty(type);}
    public LiveData<TypeOfProperty> getTypeOfPropertyFromId(int typeId) {return typeOfPropertyRepository.getTypeFromId(typeId);}
    public LiveData<TypeOfProperty> getTypeOfPropertyFromName(String typeName) {return typeOfPropertyRepository.getTypeFromName(typeName);}
*/

}
