package com.openclassrooms.realestatemanager.injections;

import com.openclassrooms.realestatemanager.PropertyViewModel;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.openclassrooms.realestatemanager.repositories.AddressRepository;
import com.openclassrooms.realestatemanager.repositories.AgentRepository;
import com.openclassrooms.realestatemanager.repositories.AttractingPointRepository;
import com.openclassrooms.realestatemanager.repositories.PhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.StatusRepository;
import com.openclassrooms.realestatemanager.repositories.TypeOfPropertyRepository;

import java.util.concurrent.Executor;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Anne-Charlotte Vivant on 21/05/2019.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final AddressRepository addressRepository;
    private final AgentRepository agentRepository;
    private final PhotoRepository photoRepository;
    private final PropertyRepository propertyRepository;
    private final StatusRepository statusRepository;
    private final TypeOfPropertyRepository typeOfPropertyRepository;
    private final Executor executor;

    public ViewModelFactory(PropertyRepository propertyRepository, AddressRepository addressRepository, AgentRepository agentRepository, PhotoRepository photoRepository,  StatusRepository statusRepository, TypeOfPropertyRepository typeOfPropertyRepository, Executor executor) {
        this.addressRepository = addressRepository;
        this.agentRepository = agentRepository;
        this.photoRepository = photoRepository;
        this.propertyRepository = propertyRepository;
        this.statusRepository = statusRepository;
        this.typeOfPropertyRepository = typeOfPropertyRepository;
        this.executor = executor;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PropertyViewModel.class)) {
            return (T) new PropertyViewModel(propertyRepository, agentRepository, photoRepository, statusRepository, typeOfPropertyRepository, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
