package com.openclassrooms.realestatemanager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListHouseFragment extends Fragment {

    private static final String TAG = "ListHouseFragment";
    private static final String PLACE_ID = "id_of_place";
    private static final String ID_FRAGMENT = "fragment_to_expose";
    public static final String ID_PROPERTY = "property_selected";
    public static final String DISPLAY_DETAIL = "display_detail_after_clic";

    private PropertyViewModel propertyViewModel;
    private ListRecyclerViewAdapter adapter;

    private int propertyId = 0;
    public int propertySaved = 0;
    private boolean useTablet;
    private boolean displayDetail;


    View v;

    public ListHouseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
       v= inflater.inflate(R.layout.fragment_list_house, container, false);
        Bundle bundle = getArguments();
        propertyId = bundle.getInt(ListHouseFragment.ID_PROPERTY, 0);
        useTablet = bundle.getBoolean(MainActivity.USE_TABLET, false);

        initRecyclerView();
        configureViewModel();
        getAllProperties();
        getAllAddresses();
        getAllTypes();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Si on a un état sauvegardé on met a jour l'élément par défaut
        if (savedInstanceState != null) {
            propertySaved = savedInstanceState.getInt(ID_PROPERTY, 0);
        }
    }


    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView");

        this.adapter = new ListRecyclerViewAdapter(propertyId, useTablet);
        RecyclerView recyclerView = v.findViewById(R.id.list_recyclerview_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickedListener(new ListRecyclerViewAdapter.OnItemClickedListener() {
            @Override
            public void OnItemClicked(int position) {
                displayDetail = true;

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra(ID_PROPERTY, position+1);
                intent.putExtra(DISPLAY_DETAIL, displayDetail);
                startActivity(intent);
            }
        });
    }

    private void configureViewModel(){
        Log.d(TAG, "configureViewModel");
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }

    private void getAllProperties(){
        this.propertyViewModel.getAllProperty().observe(this, this::updatePropertyList);
    }

        private void updatePropertyList(List<Property> properties){
        this.adapter.setProperties(properties);
    }

    private void getAllAddresses() {
        this.propertyViewModel.getAllAddress().observe(this, this::updateAddresses);
    }

    private void updateAddresses(List<Address> addresses){
        this.adapter.setAddresses(addresses);
    }

    private void getAllTypes() {
        this.propertyViewModel.getAllTypes().observe(this, this::updateTypes);
    }

    private void updateTypes(List<TypeOfProperty> types){
        this.adapter.setTypes(types);
    }
}

