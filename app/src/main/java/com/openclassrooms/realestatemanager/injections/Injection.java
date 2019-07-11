package com.openclassrooms.realestatemanager.injections;

import android.content.Context;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.repositories.AgentRepository;
import com.openclassrooms.realestatemanager.repositories.PhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.StatusRepository;
import com.openclassrooms.realestatemanager.repositories.TypeOfPropertyRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    private static AgentRepository provideAgentRepository(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new AgentRepository(database.agentDao());
    }

    private static PhotoRepository providePhotoRepository(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new PhotoRepository(database.photoDao());
    }

    private static PropertyRepository providePropertyRepository(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new PropertyRepository(database.propertyDao());
    }

    private static StatusRepository provideStatusRepository(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new StatusRepository(database.statusDao());
    }

    private static TypeOfPropertyRepository provideTypeOfPropertyRepository(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new TypeOfPropertyRepository(database.typeOfPropertyDao());
    }

    private static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        AgentRepository agentRepository = provideAgentRepository(context);
        PhotoRepository photoRepository = providePhotoRepository(context);
        PropertyRepository propertyRepository = providePropertyRepository(context);
        StatusRepository statusRepository = provideStatusRepository(context);
        TypeOfPropertyRepository typeOfPropertyRepository = provideTypeOfPropertyRepository(context);

        Executor executor = provideExecutor();
        return new ViewModelFactory(propertyRepository, agentRepository, photoRepository, statusRepository, typeOfPropertyRepository, executor);
    }
}
