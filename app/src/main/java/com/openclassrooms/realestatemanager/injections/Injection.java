package com.openclassrooms.realestatemanager.injections;

import android.content.Context;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.repositories.AddressRepository;
import com.openclassrooms.realestatemanager.repositories.AgentRepository;
import com.openclassrooms.realestatemanager.repositories.AttractingPointRepository;
import com.openclassrooms.realestatemanager.repositories.PhotoRepository;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.StatusRepository;
import com.openclassrooms.realestatemanager.repositories.TypeOfPropertyRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Anne-Charlotte Vivant on 21/05/2019.
 */
public class Injection {

    public static AddressRepository provideAddressRepository(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new AddressRepository(database.addressDao());
    }

    public static AgentRepository provideAgentRepository(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new AgentRepository(database.agentDao());
    }

/*    public static AttractingPointRepository provideAttractingPointRepository(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new AttractingPointRepository(database.attractingPointDao());
    }*/

    public static PhotoRepository providePhotoRepository(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new PhotoRepository(database.photoDao());
    }

    public static PropertyRepository providePropertyRepository(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new PropertyRepository(database.propertyDao());
    }

    public static StatusRepository provideStatusRepository(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new StatusRepository(database.statusDao());
    }

    public static TypeOfPropertyRepository provideTypeOfPropertyRepository(Context context) {
        RealEstateDatabase database = RealEstateDatabase.getInstance(context);
        return new TypeOfPropertyRepository(database.typeOfPropertyDao());
    }

    public static Executor provideExecutor(){ return Executors.newSingleThreadExecutor(); }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        AddressRepository addressRepository = provideAddressRepository(context);
        AgentRepository agentRepository = provideAgentRepository(context);
        //AttractingPointRepository attractingPointRepository = provideAttractingPointRepository(context);
        PhotoRepository photoRepository = providePhotoRepository(context);
        PropertyRepository propertyRepository = providePropertyRepository(context);
        StatusRepository statusRepository = provideStatusRepository(context);
        TypeOfPropertyRepository typeOfPropertyRepository = provideTypeOfPropertyRepository(context);

        Executor executor = provideExecutor();
        return new ViewModelFactory(propertyRepository, addressRepository, agentRepository, photoRepository, statusRepository, typeOfPropertyRepository, executor);
    }
}
