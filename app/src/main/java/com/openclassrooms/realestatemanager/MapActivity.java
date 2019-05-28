package com.openclassrooms.realestatemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import okhttp3.internal.Util;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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
import com.openclassrooms.realestatemanager.database.dao.AddressDao;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.openclassrooms.realestatemanager.utils.MapUrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.openclassrooms.realestatemanager.ListHouseFragment.DISPLAY_DETAIL;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 14f;
    public static final String ID_PROPERTY = "property_selected";
    public static final String ID_FRAGMENT = "fragment_to_expose";

    //Variables
    private boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Marker myMarker;
    private Toolbar toolbar;
    private NavigationView  navigationView;

    //Data (for test)
    private double[] test_latitude = {49.23, 49.25, 49.23};
    private double[] test_longitude = {2.88, 2.9, 2.91};
    private String[] test_type = {"appartement", "manoir", "palace"};
    private double[] test_price = {25000, 145000, 875400};
    private int[] test_id = {100, 101, 102};
    //private String devise = "€";

    private double[] tab_latitude = {1,2,3,4,5,6,7} ;
    private double[] tab_longitude = {1,2,3,4,5,6,7} ;
    private String[] tab_type = {"maison", "appartement", "manoir", "appartement", "palace", "appartement", "maison"} ;
    private int[] tab_price = {450000, 578230, 125480, 56230500, 153450, 275490, 562300} ;
    private int[] tab_id = {1,2,3,4,5,6,7} ;
    private String devise = "€";

    // Data
    private List<Property> properties = new ArrayList<>();
    private Property currentProperty;
    private PropertyViewModel propertyViewModel;
    private TypeOfProperty currentType;
    private Address currentAddress;
    private List<TypeOfProperty> allTypes;
    private List<Address> allAddresses;
    private int currentPropertyId;
    private String currentTypeText;
    private double currentLat;
    private double currentLng;
    private int currentPrice;
    private int typeIndex;
    private int addressIndex;
    /*private double[] tab_latitude ;
    private double[] tab_longitude ;
    private String[] tab_type ;
    private int[] tab_price ;
    private long[] tab_id ;
    private String devise = "€";*/

    //clic
    private boolean displayDetail;
    Handler handlerUI = new Handler();
    int compteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        configureToolbar();
        getLocationPermission();

        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //allAddresses = new ArrayList<>();
        //allTypes = new ArrayList<>();
    }

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_LONG).show();
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
                            addAlMarkers();

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
        tab_type = new String[properties.size()];
        tab_price = new int[properties.size()];
        tab_latitude = new double[properties.size()];
        tab_longitude = new double[properties.size()];

        for (int i=0; i<properties.size(); i++) {
            //int i=2;
            currentProperty = properties.get(i);

            currentPrice = currentProperty.getPrice();
            tab_price[i] = currentPrice;
            Log.d(TAG, "updatePropertyList: currentPrice " + currentPrice);

            tab_id[i] = currentProperty.getPropertyId();
            currentPropertyId = currentProperty.getPropertyId();
            Log.d(TAG, "updatePropertyList: currentPropertyId " + currentPropertyId);

            tab_latitude[i] = setPropertyLatLng(new Address(currentProperty.getNumberInStreet(), currentProperty.getStreet(), currentProperty.getStreet2(), currentProperty.getZipcode(), currentProperty.getTown(), currentProperty.getCountry())).latitude;
            tab_longitude[i] = setPropertyLatLng(new Address(currentProperty.getNumberInStreet(), currentProperty.getStreet(), currentProperty.getStreet2(), currentProperty.getZipcode(), currentProperty.getTown(), currentProperty.getCountry())).longitude;

            // Comment faire pour que la boucle attende les résultats de getType avant de boucler?
            getType(currentProperty.getTypeId());
            tab_type[i]=currentTypeText;

/*            getAddress(currentProperty.getAddressId());
            tab_latitude[i]=currentLat;
            tab_longitude[i]=currentLng;*/
        }
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

   /* private void getAddress(int id) {
        this.propertyViewModel.getAddressFromId(id).observe(this, this::updateAddress);
    }

    private void updateAddress(Address address){
        this.currentAddress = address;
        try {
            currentLat = geocoder(currentAddress).latitude;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            currentLng = geocoder(currentAddress).longitude;
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "updateAddress: addressIndex " + addressIndex);
    }*/

    private void getType(int id) {
        Log.d(TAG, "getType");
        this.propertyViewModel.getTypeFromId(id).observe(this, this::updateType);
    }

    private void updateType(TypeOfProperty typeOfProperty){
        this.currentType = typeOfProperty;
        this.currentTypeText = currentType.getTypeText();
    }

    //--------------------------------------------------------------------------------------------------------------------
    //manages Markers
    //--------------------------------------------------------------------------------------------------------------------

    private void addAlMarkers() {
        Log.d(TAG, "addAllMarkers");

        // le type n'est pas le bon à cause du pb de synchronisation
        for (int i=0; i< tab_latitude.length; i++) {
            addMarker(new LatLng(tab_latitude[i], tab_longitude[i]), tab_type[i], tab_price[i], devise, tab_id[i]);
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                launchDetail(marker);
            }
        });
    }

    private Marker addMarker(LatLng latLng, String type, int price, String devise, int propertyId) {
        Log.d(TAG, "addMarkers");
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(type + " " + price + devise + " " + propertyId);

        myMarker = mMap.addMarker(options);
        myMarker.setTag(propertyId);
            return myMarker;
    }

    private void launchDetail(Marker marker ) {
        int ref = (int) marker.getTag();
        Log.d(TAG, "launchDetail: ref " +ref);
        displayDetail = true;
        Intent WVIntent = new Intent(MapActivity.this, MainActivity.class);
        WVIntent.putExtra(ListHouseFragment.ID_PROPERTY, ref);
        WVIntent.putExtra(ID_FRAGMENT, "2");
        WVIntent.putExtra(ListHouseFragment.DISPLAY_DETAIL, displayDetail);
        startActivity(WVIntent);

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
