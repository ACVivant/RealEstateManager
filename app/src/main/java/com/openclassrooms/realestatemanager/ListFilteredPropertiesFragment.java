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
import androidx.sqlite.db.SimpleSQLiteQuery;

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
import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFilteredPropertiesFragment extends Fragment {

    // A mettre à jour avec les données de ResultSearchActivity passées par SearchActivity

    private static final String TAG = "ListFilteredPropertiesF";
    private static final String PLACE_ID = "id_of_place";
    private static final String ID_FRAGMENT = "fragment_to_expose";
    public static final String ID_PROPERTY = "property_selected";
    public static final String DISPLAY_DETAIL = "display_detail_after_clic";
    public static final String FROM_FILTER = "from_filtered_results";
    public static final String POSITON_IN_FILTER = "position_of_property_in_filtered_results";

    private PropertyViewModel propertyViewModel;
    private ListRecyclerViewAdapter adapter;

    private int propertyId = 0;
    public int propertySaved = 0;
    private boolean useTablet;
    private boolean displayDetail;
    private boolean filteredResults;
    private String searchQuery;
    private List<Property> listFilteredProperty = new ArrayList<>();

    private ArrayList<Integer> filteredResultsArray = new ArrayList<>();


    View v;

    public ListFilteredPropertiesFragment() {
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
        filteredResultsArray = bundle.getIntegerArrayList(SearchActivity.ID_FILTERED);
        // filteredResults = bundle.getBoolean(SearchActivity.RESULTS_FILTERED, false);
        // if (filteredResults) {searchQuery = bundle.getString(SearchActivity.SEARCH_QUERY);}

        initRecyclerView();
        configureViewModel();
        getFilteredProperties();
        getAllTypes();
        //setFilteredProperties();

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
                //intent.putExtra(ID_PROPERTY, position + 1);
                intent.putExtra(ID_PROPERTY, filteredResultsArray.get(position));
                intent.putExtra(DISPLAY_DETAIL, displayDetail);
                intent.putExtra(SearchActivity.ID_FILTERED, filteredResultsArray);
                intent.putExtra(FROM_FILTER, true);
                intent.putExtra(POSITON_IN_FILTER, position);
                Log.d(TAG, "OnItemClicked: position " + position);
                Log.d(TAG, "OnItemClicked: id " + filteredResultsArray.get(position));
                startActivity(intent);
            }
        });
    }

    private void configureViewModel(){
        Log.d(TAG, "configureViewModel");
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }

    private void getFilteredProperties(){
        for (int i=0; i<filteredResultsArray.size(); i++) {
            this.propertyViewModel.getPropertyFromId(filteredResultsArray.get(i)).observe(this, this::updatePropertyList);
            Log.d(TAG, "getFilteredProperties: property n°" + i);
        }
    }

    private void updatePropertyList(Property property) {
        listFilteredProperty.add(property);
        this.adapter.setProperties(listFilteredProperty);
    }

    private void getAllTypes() {
        this.propertyViewModel.getAllTypes().observe(this, this::updateTypes);
    }

    private void updateTypes(List<TypeOfProperty> types){
        this.adapter.setTypes(types);
    }

    private void setFilteredProperties() {
        Log.d(TAG, "setFilteredProperties");
        this.adapter.setProperties(listFilteredProperty);
    }
}


