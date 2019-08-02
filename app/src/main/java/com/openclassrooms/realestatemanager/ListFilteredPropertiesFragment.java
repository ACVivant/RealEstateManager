package com.openclassrooms.realestatemanager;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.adapters.ListRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.openclassrooms.realestatemanager.viewmodels.PropertyViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFilteredPropertiesFragment extends Fragment {

    private static final String TAG = "ListFilteredPropertiesF";
    public static final String ID_PROPERTY = "property_selected";
    public static final String FROM_FILTER = "from_filtered_results";
    public static final String POSITON_IN_FILTER = "position_of_property_in_filtered_results";

    private PropertyViewModel propertyViewModel;
    private ListRecyclerViewAdapter adapter;

    private int propertyId = 0;
    public int propertySaved = 0;

    private List<Property> listFilteredProperty = new ArrayList<>();

    private ArrayList<Integer> filteredResultsArray = new ArrayList<>();
    private int positionRV;

    View v;

    private OnItemFilteredRVClickedListener mCallback;

    // Declare our interface that will be implemented by any container activity
    public interface OnItemFilteredRVClickedListener {
        void onFilteredItemRVClicked(int propertyId, int position);
    }

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
        filteredResultsArray = bundle.getIntegerArrayList(SearchActivity.ID_FILTERED);
        positionRV = bundle.getInt(ListHouseFragment.POSITION_IN_RV, 1);

        Log.d(TAG, "onCreateView: filteredResultsArray " + filteredResultsArray);
        configureViewModel();
        getAllTypes();
        getFilteredProperties();
        initRecyclerView();

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

        this.adapter = new ListRecyclerViewAdapter(propertyId);
        RecyclerView recyclerView = v.findViewById(R.id.list_recyclerview_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(positionRV);
        recyclerView.setAdapter(adapter);

        adapter.setRVBackgroudColor(propertyId);

        adapter.setOnItemClickedListener(new ListRecyclerViewAdapter.OnItemClickedListener() {
            @Override
            public void OnItemClicked(int propertyId, int position) {
                mCallback.onFilteredItemRVClicked(propertyId, position);
                adapter.setRVBackgroudColor(propertyId);
            }
        });
    }

    private void configureViewModel(){

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Call the method that creating callback after being attached to parent activity
        this.createCallbackToMainActivity();
    }

    // Create callback to parent activity
    private void createCallbackToMainActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnItemFilteredRVClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnItemFilteredClickedListener");
        }
    }

}


