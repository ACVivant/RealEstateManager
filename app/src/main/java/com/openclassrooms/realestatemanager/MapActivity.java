package com.openclassrooms.realestatemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.openclassrooms.realestatemanager.utils.MapUrl;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 14f;

    //Variables
    private boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Marker myMarker;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    private double[] tab_latitude ;
    private double[] tab_longitude;
    private int[] tab_room ;
    private int[] tab_price;
    private int[] tab_id ;
    private String devise = "$";

    // Data
    private PropertyViewModel propertyViewModel;
    private int currentPropertyId;
    private double currentLat;
    private double currentLng;
    private int currentPrice;

    //clic
    private boolean displayDetail;
    private boolean internet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        configureToolbar();
        configureProgressBar();
        showProgressBar();
        getInternetConnexion();
        if (internet) {
            getLocationPermission();
        } else {
            Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    private void configureToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void configureProgressBar() {
        progressBar = findViewById(R.id.map_progress_bar);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
}

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    private boolean getInternetConnexion() {
        internet = Utils.isInternetAvailable(this);
        return  internet;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       Log.d(TAG, "onMapReady: Map is ready");
        mMap = googleMap;

        if (mLocationPermissionGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true); // icone GPS pour recentrer la carte
            mMap.getUiSettings().setZoomControlsEnabled(true); // zoom
        }
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);

    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (mLocationPermissionGranted) {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: location found");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),DEFAULT_ZOOM);

                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this, "Unable to get current location", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: Security Exception" + e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving the camera to lat: " + latLng.latitude +" lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        addAlMarkers();
    }

    //--------------------------------------------------------------------------------------------------------------------
    // Data
    //--------------------------------------------------------------------------------------------------------------------
    public void getData() throws IOException {
        Log.d(TAG, "getData");
        configureViewModel();
        this.getAllProperties();
    }

    private void configureViewModel(){
        Log.d(TAG, "configureViewModel");
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }

    private void getAllProperties(){
        this.propertyViewModel.getAllProperty().observe(this, this::updatePropertyList);
    }

    private void updatePropertyList(List<Property> properties){
        tab_id = new int[properties.size()];
        tab_room = new int[properties.size()];
        tab_price = new int[properties.size()];
        tab_latitude = new double[properties.size()];
        tab_longitude = new double[properties.size()];

        for (int i=0; i<properties.size(); i++) {
            loadMarkerInfos(properties.get(i), i);
        }

        hideProgressBar();
    }

    public void loadMarkerInfos(Property currentProperty, int i) {
        currentPrice = currentProperty.getPrice();
        tab_price[i] = currentPrice;

        tab_id[i] = currentProperty.getPropertyId();
        currentPropertyId = currentProperty.getPropertyId();

        tab_room[i] = currentProperty.getRooms();

        tab_latitude[i] = setPropertyLatLng(new Address(currentProperty.getNumberInStreet(), currentProperty.getStreet(), currentProperty.getStreet2(), currentProperty.getZipcode(), currentProperty.getTown(), currentProperty.getCountry())).latitude;
        tab_longitude[i] = setPropertyLatLng(new Address(currentProperty.getNumberInStreet(), currentProperty.getStreet(), currentProperty.getStreet2(), currentProperty.getZipcode(), currentProperty.getTown(), currentProperty.getCountry())).longitude;

        addMarker(new LatLng(tab_latitude[i], tab_longitude[i]), tab_room[i], tab_price[i], devise, tab_id[i]);
    }


    public LatLng setPropertyLatLng(Address address) {
        try {
            currentLat = geocoder(address).latitude;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            currentLng = geocoder(address).longitude;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LatLng(currentLat, currentLng);
    }

    //--------------------------------------------------------------------------------------------------------------------
    //manages Markers
    //--------------------------------------------------------------------------------------------------------------------

    private void addAlMarkers() {
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                launchDetail(marker);
            }
        });
    }

    private Marker addMarker(LatLng latLng, int room, int price, String devise, int propertyId) {

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(room + getResources().getString(R.string.room) + price + devise);

        myMarker = mMap.addMarker(options);
        myMarker.setTag(propertyId);
            return myMarker;
    }

    private void launchDetail(Marker marker ) {

        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            int ref = (int) marker.getTag();
            Intent WVIntent = new Intent(MapActivity.this, MainActivity.class);
            WVIntent.putExtra(ListHouseFragment.ID_PROPERTY, ref);
            startActivity(WVIntent);
        } else {
            int ref = (int) marker.getTag();
            displayDetail = true;
            Intent WVIntent = new Intent(MapActivity.this, DetailActivity.class);
            WVIntent.putExtra(ListHouseFragment.ID_PROPERTY, ref);
            WVIntent.putExtra(ListHouseFragment.DISPLAY_DETAIL, displayDetail);
            startActivity(WVIntent);
        }
    }

    //------------------------------------------------
    // Geocoder
    //------------------------------------------------

    public LatLng geocoder(Address currentAddress) throws IOException {
        MapUrl mapUrl = new MapUrl();
        String location =mapUrl.createGeocoderUrl(currentAddress.getNumberInStreet(), currentAddress.getStreet(),currentAddress.getZipcode(), currentAddress.getTown(), currentAddress.getCountry() );
        Geocoder gc = new Geocoder(this);
        List<android.location.Address> list = gc.getFromLocationName(location,1);
        android.location.Address add = list.get(0);
        String locality = add.getLocality();
        double lat = add.getLatitude();
        double lng = add.getLongitude();
        LatLng coord = new LatLng(lat, lng);
        return coord;
    }

    //-----------------------------------------------
    // Permissions
    //------------------------------------------------
    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: Called.");
        mLocationPermissionGranted = true;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length>0) {
                    for (int i=0; i<grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: Permission failed.");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: Permission granted.");
                    mLocationPermissionGranted = true;
                    // initialize our map
                    initMap();
                }
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
