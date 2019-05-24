package com.openclassrooms.realestatemanager;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.openclassrooms.realestatemanager.utils.MapUrl;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    //public class DetailFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "DetailFragment";
    private static final float DEFAULT_ZOOM = 13f;

    // Real data
    private int propertyId = 0;

    private View v;

    TextView status;
    TextView upForSaleDate;
    TextView soldOnDate;
    TextView description;
    TextView type;
    TextView surface;
    TextView rooms;
    TextView bedrooms;
    TextView bathrooms;
    CheckBox shops;
    CheckBox schools;
    CheckBox museum;
    CheckBox park;
    TextView numberInStreet;
    TextView street;
    TextView street2;
    TextView zipcode;
    TextView town;
    TextView country;
    TextView price;

    /* @BindView(R.id.map-detail)
ImageView mMapView;;*/
    private ImageView mMap;

    private List<Property> properties;
    private List<Photo> currentPhotos;
    private PropertyViewModel propertyViewModel;
    private Property currentProperty;
    private Address currentAddress;
    private TypeOfProperty currentType;
    private Status currentStatus;
    private Agent currentAgent;
    private int addressId;
    private int statusId;
    private int typeId;
    private int agentId;

    private String key;

    private PhotoRecyclerViewAdapter adapter;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_detail, container, false);
        key = getContext().getResources().getString(R.string.Google_Maps_API_Key);
        mMap = (ImageView) v.findViewById(R.id.map_detail);
        //ButterKnife.bind(v); // Ca ne marche pas...

        properties = new ArrayList<>();
        currentPhotos = new ArrayList<>();

        status = (TextView) v.findViewById(R.id.textView23);
        upForSaleDate = (TextView) v.findViewById(R.id.textView27);
        soldOnDate = (TextView) v.findViewById(R.id.textView28);
        description = (TextView) v.findViewById(R.id.editText);
        type = (TextView) v.findViewById(R.id.textView22);
        surface = (TextView) v.findViewById(R.id.textView4);
        rooms = (TextView) v.findViewById(R.id.textView6);
        bedrooms = (TextView) v.findViewById(R.id.textView8);
        bathrooms = (TextView) v.findViewById(R.id.textView10);
        shops = (CheckBox) v.findViewById(R.id.checkBox);
        schools = (CheckBox) v. findViewById(R.id.checkBox2);
        museum = (CheckBox) v.findViewById(R.id.checkBox3);
        park = (CheckBox) v.findViewById(R.id.checkBox4);
        numberInStreet = (TextView) v.findViewById(R.id.textView12);
        street = (TextView) v.findViewById(R.id.textView13);
        street2 = (TextView) v.findViewById(R.id.textView14);
        zipcode = (TextView) v.findViewById(R.id.textView15);
        town = (TextView) v.findViewById(R.id.textView16);
        country = (TextView) v.findViewById(R.id.textView17);
        price = (TextView) v.findViewById(R.id.textView21);

        Bundle bundle = getArguments();
        propertyId = bundle.getInt(ListHouseFragment.ID_PROPERTY,1);
        Log.d(TAG, "onCreateView: propertyId " + propertyId);

        configureViewModel();
        this.getProperty(propertyId);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    //------------------------------------------------------------------
    // Map
    //------------------------------------------------------------------

    public void initStaticMap(String number, String street, String zipcode, String town, String country, String key) {
        MapUrl mapUrl = new MapUrl();
        String srcMap = mapUrl.createUrl(number, street, zipcode, town, country, key);
        Log.d(TAG, "initStaticMap: " + srcMap);
        Picasso.get().load(srcMap).into(mMap);
    }

    //------------------------------------------------------------------
    // ViewModel
    //------------------------------------------------------------------


    private void configureViewModel(){
        Log.d(TAG, "configureViewModel");
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }

    private void getProperty(int id){
        this.propertyViewModel.getPropertyFromId(id).observe(this, this::updateProperty);
    }

    private void updateProperty(Property property) {
        currentProperty = property;
        upForSaleDate.setText(Utils.convertStringToDate(String.valueOf(currentProperty.getUpForSaleDate())));
        soldOnDate.setText(Utils.convertStringToDate(String.valueOf(currentProperty.getSoldOnDate())));
        description.setText(String.valueOf(currentProperty.getDescription()));
        surface.setText(String.valueOf(currentProperty.getSurface()));
        rooms.setText(String.valueOf(currentProperty.getRooms()));
        bedrooms.setText(String.valueOf(currentProperty.getBedrooms()));
        bathrooms.setText(String.valueOf(currentProperty.getBathroom()));
        shops.setChecked(currentProperty.getShop());
        schools.setChecked(currentProperty.getSchool());
        museum.setChecked(currentProperty.getMuseum());
        park.setChecked(currentProperty.getPark());
        price.setText(String.valueOf(currentProperty.getPrice()));

        agentId = currentProperty.getAgentId();
        addressId = currentProperty.getAddressId();
        typeId = currentProperty.getTypeId();
        statusId = currentProperty.getStatusId();

        Log.d(TAG, "updateProperty: id " +propertyId + " " +addressId+ " " +typeId+ " " + statusId);

        this.getAddress(addressId);
        this.getStatus(statusId);
        this.getType(typeId);

        this.initPhotosRecyclerView();
        this.getAllPhotosFromProperty(propertyId);
    }


    private void getAddress(int id) {
        this.propertyViewModel.getAddressFromId(id).observe(this, this::updateAddress);
    }

    private void updateAddress(Address address){
        Log.d(TAG, "updateAddress: addressId " +addressId);
        currentAddress = address;
        Log.d(TAG, "updateAddress: number " + currentAddress.getNumberInStreet());
        numberInStreet.setText(String.valueOf(currentAddress.getNumberInStreet()));
        street.setText(String.valueOf(currentAddress.getStreet()));
        if (currentAddress.getStreet2()!=null){
        street2.setText(String.valueOf(currentAddress.getStreet2()));
        } else {street2.setVisibility(View.GONE);}
        zipcode.setText(String.valueOf(currentAddress.getZipcode()));
        town.setText(String.valueOf(currentAddress.getTown()));
        country.setText(String.valueOf(currentAddress.getCountry()));

        initStaticMap(currentAddress.getNumberInStreet(), currentAddress.getStreet(), currentAddress.getZipcode(), currentAddress.getTown(), currentAddress.getCountry(), key );

    }

    private void getType(int id) {
        this.propertyViewModel.getTypeFromId(id).observe(this, this::updateType);
    }

    private void updateType(TypeOfProperty typeOfProperty){
        currentType = typeOfProperty;
        Log.d(TAG, "updateType: typeId " + typeId);
        type.setText(String.valueOf(currentType.getTypeText()));
    }

    private void getStatus(int id) {
        this.propertyViewModel.getStatusFromId(id).observe(this, this::updateStatus);
    }

    private void updateStatus(Status stat){
        currentStatus = stat;
        status.setText(currentStatus.getStatusText());
    }

    private void initPhotosRecyclerView() {
        Log.d(TAG, "initPhotosRecyclerView");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = v.findViewById(R.id.photo_recyclerview_container);
        adapter = new PhotoRecyclerViewAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void getAllPhotosFromProperty(int id){
        this.propertyViewModel.getPhotoFromProperty(id).observe(this, this::updatePhotoList);
    }

    private void updatePhotoList(List<Photo> photos){
        currentPhotos = photos;
        this.adapter.setPhotos(photos);
    }
}
