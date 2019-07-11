package com.openclassrooms.realestatemanager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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
import java.util.concurrent.Callable;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListHouseFragment extends Fragment  {

    private static final String TAG = "ListHouseFragment";
    public static final String ID_PROPERTY = "property_selected";
    public static final String DISPLAY_DETAIL = "display_detail_after_clic";
    public static final String POSITION_IN_RV = "position_in_recyclerview";

    private PropertyViewModel propertyViewModel;
    private ListRecyclerViewAdapter adapter;

    private int propertyId = 1;
    public int propertySaved = 1;
    private int positionRV;

    View v;

    private OnItemRVClickedListener mCallback;

    // Declare our interface that will be implemented by any container activity
    public interface OnItemRVClickedListener {
        void onItemRVClicked(int propertyId, int position);
    }

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
        propertyId = bundle.getInt(ListHouseFragment.ID_PROPERTY, 1);
        positionRV = bundle.getInt(POSITION_IN_RV, 1);

        configureViewModel();
        getAllTypes();
        getAllProperties();

        initRecyclerView();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Si on a un état sauvegardé on met a jour l'élément par défaut
        if (savedInstanceState != null) {
            propertySaved = savedInstanceState.getInt(ID_PROPERTY, 1);
        }
    }


    private void initRecyclerView(){
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            System.out.println("got interrupted!");
        }

        this.adapter = new ListRecyclerViewAdapter(propertyId);
        RecyclerView recyclerView = v.findViewById(R.id.list_recyclerview_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(positionRV);

        adapter.setRVBackgroudColor(propertyId);

            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickedListener(new ListRecyclerViewAdapter.OnItemClickedListener() {
                @Override
                public void OnItemClicked(int propertyId, int position) {
                    mCallback.onItemRVClicked(propertyId, position);
                    adapter.setRVBackgroudColor(propertyId);
                }
            });
    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }


    private void getAllProperties(){
        this.propertyViewModel.getAllProperty().observe(this, this::updatePropertyList);
    }

        private void updatePropertyList(List<Property> properties){
        this.adapter.setProperties(properties);
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

        // 4 - Call the method that creating callback after being attached to parent activity
        this.createCallbackToMainActivity();
    }

        // 3 - Create callback to parent activity
    private void createCallbackToMainActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnItemRVClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnItemClickedListener");
        }
    }
}

