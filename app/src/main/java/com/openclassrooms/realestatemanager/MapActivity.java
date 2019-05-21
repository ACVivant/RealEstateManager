package com.openclassrooms.realestatemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import okhttp3.internal.Util;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
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
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.utils.MapUrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.openclassrooms.realestatemanager.ListHouseFragment.DISPLAY_DETAIL;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 14f;
    public static final String ID_PROPERTY = "property_selected";
    private static final String ID_FRAGMENT = "fragment_to_expose";

    //Variables
    private boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Marker myMarker;

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
    /*private double[] tab_latitude ;
    private double[] tab_longitude ;
    private String[] tab_type ;
    private int[] tab_price ;
    private long[] tab_id ;
    private String devise = "€";*/

    //clic
    private boolean displayDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getLocationPermission();
        try {
            getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

            addAllMarkers();
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

    }

    //--------------------------------------------------------------------------------------------------------------------
    // Data
    //--------------------------------------------------------------------------------------------------------------------
    public void getData() throws IOException {
        Log.d(TAG, "getData");
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        final Observer<List<Property>> dataObserver = new Observer<List<Property>>() {
            @Override
            public void onChanged(List<Property> properties) {   // OnChnaged n'est jamais appelé... Pourquoi????
                Log.d(TAG, "onChanged properties.size " + properties.size());

                tab_id = new int[properties.size()];
                tab_type = new String[properties.size()];
                tab_price = new int[properties.size()];
                tab_latitude = new double[properties.size()];
                tab_longitude = new double[properties.size()];

                for (int i=0; i<properties.size(); i++) {
                    currentProperty = properties.get(i);
                    tab_price[i] = currentProperty.getPrice();
                    tab_type[i] = currentProperty.getType().getTypeText();
                    tab_id[i] = currentProperty.getPropertyId();

                    com.openclassrooms.realestatemanager.models.Address currentAddress = currentProperty.getAddress();
                    try {
                        tab_latitude[i] = geocoder(currentAddress.getNumberInStreet(), currentAddress.getStreet(), currentAddress.getZipcode(), currentAddress.getTown(), currentAddress.getCountry()).latitude;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        tab_longitude[i] = geocoder(currentAddress.getNumberInStreet(), currentAddress.getStreet(), currentAddress.getZipcode(), currentAddress.getTown(), currentAddress.getCountry()).longitude;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        propertyViewModel.getAllProperty().observe(this, dataObserver);

        // Pour pouvoir continuer tant que le pb de OnChanged n'est pas réglé
        tab_latitude[0] = geocoder("6", "rue Alexandre Dumas",  "60800", "Crépy-en-Valois", "France").latitude;
        tab_latitude[1] = geocoder("10", "rue de Soissons",  "60800", "Crépy-en-Valois", "France").latitude;
        tab_latitude[2] = geocoder("12", "rue Charles de Gaulle", "60800", "Crépy-en-Valois", "France").latitude;
        tab_latitude[3] = geocoder("5", "rue Saint Denis",  "60800", "Crépy-en-Valois", "France").latitude;
        tab_latitude[4] = geocoder("13", "rue Saint Denis",  "60800", "Crépy-en-Valois", "France").latitude;
        tab_latitude[5] = geocoder("13", "rue de Vez",  "60800", "Crépy-en-Valois", "France").latitude;
        tab_latitude[6] = geocoder("19", "rue de Vez",  "60800", "Crépy-en-Valois", "France").latitude;

        tab_longitude[0] = geocoder("6", "rue Alexandre Dumas",  "60800", "Crépy-en-Valois", "France").longitude;
        tab_longitude[1] = geocoder("10", "rue de Soissons",  "60800", "Crépy-en-Valois", "France").longitude;
        tab_longitude[2] = geocoder("12", "rue Charles de Gaulle", "60800", "Crépy-en-Valois", "France").longitude;
        tab_longitude[3] = geocoder("5", "rue Saint Denis",  "60800", "Crépy-en-Valois", "France").longitude;
        tab_longitude[4] = geocoder("13", "rue Saint Denis",  "60800", "Crépy-en-Valois", "France").longitude;
        tab_longitude[5] = geocoder("13", "rue de Vez",  "60800", "Crépy-en-Valois", "France").longitude;
        tab_longitude[6] = geocoder("19", "rue de Vez",  "60800", "Crépy-en-Valois", "France").longitude;

    }

    //--------------------------------------------------------------------------------------------------------------------
    //manages Markers
    //--------------------------------------------------------------------------------------------------------------------

    private void addAllMarkers() {
        Log.d(TAG, "addAllMarkers");


        for (int i=0; i< tab_latitude.length; i++) {
            addMarkers(new LatLng(tab_latitude[i], tab_longitude[i]), tab_type[i], tab_price[i], devise, tab_id[i]);
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                launchDetail(marker);
            }
        });
    }

    private Marker addMarkers(LatLng latLng, String type, int price, String devise, int propertyId) {
        Log.d(TAG, "addMarkers");
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(type + " " + price + devise);

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

    public LatLng geocoder(String number, String street, String zipcode, String town, String country ) throws IOException {
        MapUrl mapUrl = new MapUrl();
        //String location =mapUrl.createGeocoderUrl("6", "rue Alexandre Dumas","60800", "Crépy-en-Valois", "France" );
        String location =mapUrl.createGeocoderUrl(number, street,zipcode, town, country );
        Geocoder gc = new Geocoder(this);
        List<Address> list = gc.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();
        double lat = add.getLatitude();
        double lng = add.getLongitude();

        Log.d(TAG, "geocoder: locality " + locality);
        Log.d(TAG, "geocoder: latitude " + lat);
        Log.d(TAG, "geocoder: longitude " + lng);

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

}
