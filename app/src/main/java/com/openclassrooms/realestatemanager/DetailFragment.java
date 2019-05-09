package com.openclassrooms.realestatemanager;


import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "DetailFragment";
    private static final float DEFAULT_ZOOM = 13f;

    private ArrayList<String> photoUrlList = new ArrayList<>();
    private ArrayList<String> textList = new ArrayList<>();
    private ArrayList<Double> latitudeList = new ArrayList<>();
    private ArrayList<Double> longitudeList = new ArrayList<>();

    private View v;
    private GoogleMap mMap;
    private Marker myHomeMarker;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_detail, container, false);

        initDemoData();
        initMap();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initDemoData() {
        photoUrlList.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        textList.add("Façade");
        latitudeList.add(49.23);
        longitudeList.add(2.92);

        photoUrlList.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        textList.add("Cuisine");
        latitudeList.add(49.2);
        longitudeList.add(2.88);

        photoUrlList.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        textList.add("Chambre 1");
        latitudeList.add(49.28);
        longitudeList.add(2.91);

        photoUrlList.add("https://i.redd.it/j6myfqglup501.jpg");
        textList.add("Chambre 2");
        latitudeList.add(49.3);
        longitudeList.add(2.9);

        photoUrlList.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        textList.add("Salon");
        latitudeList.add(49.23);
        longitudeList.add(3.0);

        photoUrlList.add("https://i.redd.it/k98uzl68eh501.jpg");
        textList.add("Salle de bain");
        latitudeList.add(49.35);
        longitudeList.add(2.91);

        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = v.findViewById(R.id.photo_recyclerview_container);
        PhotoRecyclerViewAdapter adapter = new PhotoRecyclerViewAdapter(getContext(), photoUrlList, textList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    //------------------------------------------------------------------
    // Map
    //------------------------------------------------------------------

    //initializing map
    private void initMap() {
        MapView mMapView;
        mMapView = v.findViewById(R.id.map_detail);

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitudeList.get(0), longitudeList.get(0)), DEFAULT_ZOOM));
        addHomemarker();
    }

    private Marker addHomemarker() {
        MarkerOptions options = new MarkerOptions()
                .position(new LatLng(latitudeList.get(0), longitudeList.get(0)));

        myHomeMarker = mMap.addMarker(options);
        return myHomeMarker;
    }

}
