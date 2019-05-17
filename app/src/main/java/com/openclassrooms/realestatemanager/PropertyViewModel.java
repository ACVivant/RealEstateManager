package com.openclassrooms.realestatemanager;

import android.app.Application;
import android.util.Log;

import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.AttractingPoint;
import com.openclassrooms.realestatemanager.models.AttractingPropertyJoin;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.openclassrooms.realestatemanager.repositories.AddressRepository;
import com.openclassrooms.realestatemanager.repositories.AgentRepository;
import com.openclassrooms.realestatemanager.repositories.AttractingPointRepository;
import com.openclassrooms.realestatemanager.repositories.AttractingPropertyJoinRepository;
import com.openclassrooms.realestatemanager.repositories.PhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.StatusRepository;
import com.openclassrooms.realestatemanager.repositories.TypeOfPropertyRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * Created by Anne-Charlotte Vivant on 16/05/2019.
 */
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
    }

    public void init(int propertyId) {
        if (this.currentProperty != null) {
            return;
        }
        currentProperty = propertyRepository.getPropertyFromId(propertyId);
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
    public Agent getAgentFromId(int agentId) {return agentRepository.getAgentFromId(agentId);}

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

    // For Status
    public void insertStatus(Status status) {statusRepository.insertStatus(status);}
    public void updateStatus(Status status) {statusRepository.updateStatus(status);}
    public Status getStatusFromId(int statusId) {return statusRepository.getStatusFromId(statusId);}

    // For TypeOfProperty
    public void insertTypeOfProperty(TypeOfProperty type) {typeOfPropertyRepository.insertTypeOfProperty(type);}
    public void updateTypeOfProperty(TypeOfProperty type) {typeOfPropertyRepository.updateTypeOfProperty(type);}
    public TypeOfProperty getTypeOfPropertyFromId(int typeId) {return typeOfPropertyRepository.getTypeFromId(typeId);}
    public TypeOfProperty getTypeOfPropertyFromName(String typeName) {return typeOfPropertyRepository.getTypeFromName(typeName);}

}
