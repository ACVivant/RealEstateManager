package com.openclassrooms.realestatemanager;

import android.app.Application;
import android.util.Log;

import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.AddressRepository;
import com.openclassrooms.realestatemanager.repositories.AgentRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;

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
    private LiveData<List<Property>> allProperties;

    private AgentRepository agentRepository;

    public PropertyViewModel(@NonNull Application application) {
        super(application);
        propertyRepository = new PropertyRepository(application);
        allProperties = propertyRepository.getAllProperty();
    }

    public void insertProperty(Property property){
        propertyRepository.insertProperty(property);
    }

    public void updateProperty(Property property) {
        propertyRepository.updateProperty(property);
    }

    public LiveData<List<Property>> getAllProperty(){
        return allProperties;
    }

}
