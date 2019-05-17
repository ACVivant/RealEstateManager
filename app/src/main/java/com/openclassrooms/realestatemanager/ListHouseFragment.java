package com.openclassrooms.realestatemanager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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

import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListHouseFragment extends Fragment {

    private static final String TAG = "ListHouseFragment";
    private static final String PLACE_ID = "id_of_place";
    private static final String ID_FRAGMENT = "fragment_to_expose";

    private ArrayList<String> picturesUrlList = new ArrayList<>();
    private ArrayList<String> typeList = new ArrayList<>();
    private ArrayList<String> townList = new ArrayList<>();
    private ArrayList<String> priceList = new ArrayList<>();
    private ArrayList<Integer> idList = new ArrayList<>();

    private PropertyViewModel propertyViewModel;


    View v;
    public ListHouseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v= inflater.inflate(R.layout.fragment_list_house, container, false);

       //initDemoData();

        initRecyclerView();


        return v;
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView");
        RecyclerView recyclerView = v.findViewById(R.id.list_recyclerview_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        ListRecyclerViewAdapter adapter = new ListRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        propertyViewModel.getAllProperty().observe(this, new Observer<List<Property>>() {
            @Override
            public void onChanged(List<Property> properties) {
                Log.d(TAG, "onChanged");
                adapter.setProperties(properties);
                Log.d(TAG, "onChanged: size " + properties.size());
            }
        });

    }
}
