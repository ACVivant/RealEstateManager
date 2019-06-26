package com.openclassrooms.realestatemanager;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.openclassrooms.realestatemanager.utils.MapUrl;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private static final String TAG = "DetailFragment";
    public static final String FROM_DETAIL_REQUEST = "fromDetailActivity";

    // Real data
    private int propertyId = 0;

    private View v;

    private TextView status;
    private TextView upForSaleDate;
    private TextView soldOnDate;
    private TextView description;
    private TextView surface;
    private TextView rooms;
    private TextView bedrooms;
    private TextView bathrooms;
    private TextView price;
    private TextView type;
    private CheckBox shops;
    private CheckBox schools;
    private CheckBox museum;
    private CheckBox park;
    private TextView numberInStreet;
    private TextView street;
    private TextView street2;
    private TextView zipcode;
    private TextView town;
    private TextView country;
    //private Button update;
    private Button before;
    private Button after;

    private ImageView mMap;

    private List<Property> properties;
    private List<Photo> currentPhotos;
    private PropertyViewModel propertyViewModel;
    private Property currentProperty;
    private TypeOfProperty currentType;
    private Status currentStatus;
    private int statusId;
    private int typeId;
    private int agentId;
    private int numberOfProperties;
    private boolean filteredResults;
    private ArrayList<Integer> filteredResultsArray = new ArrayList<>();
    private int position;
    private int newPosition;
    private  boolean tabletSize;

    private String key;

    private PhotoRecyclerViewAdapter adapter;
    private boolean tablet;

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
        tabletSize = getResources().getBoolean(R.bool.isTablet);

        properties = new ArrayList<>();
        currentPhotos = new ArrayList<>();

        status = (TextView) v.findViewById(R.id.textView23);
        upForSaleDate = (TextView) v.findViewById(R.id.textView27);
        soldOnDate = (TextView) v.findViewById(R.id.textView28);
        description = (TextView) v.findViewById(R.id.editText);
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



        if (container == null) {
            // Si le ViewGroup n'est pas renseigné on ne cherche pas à en créer un car sur cet écran l'appelant doit passer par newInstance...
            return null;
        }

        Bundle bundle = getArguments();
        propertyId = bundle.getInt(ListHouseFragment.ID_PROPERTY,1);
        filteredResults = bundle.getBoolean(SearchActivity.RESULTS_FILTERED, false);

        if (filteredResults) {
            filteredResultsArray = bundle.getIntegerArrayList(SearchActivity.ID_FILTERED);
            position = bundle.getInt(ListFilteredPropertiesFragment.POSITON_IN_FILTER, 1);
        }

        if (!tabletSize) { // cas des téléphones
            type = (TextView) v.findViewById(R.id.textView22);
            price = (TextView) v.findViewById(R.id.textView21);
            before = (Button) v.findViewById(R.id.before_detail_btn);
            after = (Button) v.findViewById(R.id.after_detail_btn);

            before.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!filteredResults) {
                        if (propertyId == 1) {
                            Toast.makeText(getContext(), R.string.before_not_allowed, Toast.LENGTH_LONG).show();
                        } else {
                            launchMainActivityDetail(propertyId - 1);
                        }
                    } else {
                        if (propertyId == filteredResultsArray.get(0)) {
                            Toast.makeText(getContext(), R.string.before_not_allowed, Toast.LENGTH_LONG).show();
                        } else {
                            newPosition = position - 1;
                            launchResultSearchActivityDetail(filteredResultsArray.get(position - 1));
                        }
                    }
                }
            });

            after.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!filteredResults) {
                        if (propertyId == numberOfProperties) {
                            Toast.makeText(getContext(), R.string.after_not_allowed, Toast.LENGTH_LONG).show();
                        } else {
                            launchMainActivityDetail(propertyId + 1);
                        }
                    } else {
                        if (position == filteredResultsArray.size()-1) {
                            Toast.makeText(getContext(), R.string.after_not_allowed, Toast.LENGTH_LONG).show();
                        } else {
                            newPosition = position + 1;
                            launchResultSearchActivityDetail(filteredResultsArray.get(newPosition));
                        }
                    }
                }
            });
        }

        configureViewModel();
        this.getAllProperties();
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

        Glide.with(getContext())
                .load(srcMap)
                .into(mMap);
    }

    //------------------------------------------------------------------
    // ViewModel
    //------------------------------------------------------------------


    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(getContext());
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }

    private void getProperty(int id){
        this.propertyViewModel.getPropertyFromId(id).observe(this, this::updateProperty);
    }

    private void updateProperty(Property property) {
        currentProperty = property;
        upForSaleDate.setText(Utils.convertStringToDate(String.valueOf(currentProperty.getUpForSaleDate())));
        String soldOnDateText = Utils.convertStringToDate(String.valueOf(currentProperty.getSoldOnDate()));
        if (soldOnDateText.equals("99/99/9999")) {
            soldOnDate.setText("N/A");
        }else{
        soldOnDate.setText(soldOnDateText);
        }
        description.setText(String.valueOf(currentProperty.getDescription()));
        surface.setText(String.valueOf(currentProperty.getSurface()));
        rooms.setText(String.valueOf(currentProperty.getRooms()));
        bedrooms.setText(String.valueOf(currentProperty.getBedrooms()));
        bathrooms.setText(String.valueOf(currentProperty.getBathroom()));
        shops.setChecked(currentProperty.getShop());
        schools.setChecked(currentProperty.getSchool());
        museum.setChecked(currentProperty.getMuseum());
        park.setChecked(currentProperty.getPark());

        agentId = currentProperty.getAgentId();
        statusId = currentProperty.getStatusId();

        setAddress();
        this.getStatus(statusId);

        if (!tabletSize) { // cas des téléphones
            price.setText(String.valueOf(currentProperty.getPrice()));
            typeId = currentProperty.getTypeId();
            this.getType(typeId);
        }

        this.initPhotosRecyclerView();
        this.getAllPhotosFromProperty(propertyId);
    }

    private void setAddress(){
        numberInStreet.setText(String.valueOf(currentProperty.getNumberInStreet()));
        street.setText(String.valueOf(currentProperty.getStreet()));
        if(currentProperty.getStreet2()!= null && !currentProperty.getStreet2().isEmpty()){
            street2.setText(String.valueOf(currentProperty.getStreet2()));
        } else {street2.setVisibility(View.GONE);}
        zipcode.setText(String.valueOf(currentProperty.getZipcode()));
        town.setText(String.valueOf(currentProperty.getTown()));
        country.setText(String.valueOf(currentProperty.getCountry()));

        initStaticMap(currentProperty.getNumberInStreet(), currentProperty.getStreet(), currentProperty.getZipcode(), currentProperty.getTown(), currentProperty.getCountry(), key );
    }

    private void getType(int id) {
        this.propertyViewModel.getTypeFromId(id).observe(this, this::updateType);
    }

    private void updateType(TypeOfProperty typeOfProperty){
        currentType = typeOfProperty;
        if (!tabletSize) {  // cas des téléphones
            type.setText(String.valueOf(currentType.getTypeText()));
        }
    }

    private void getStatus(int id) {
        this.propertyViewModel.getStatusFromId(id).observe(this, this::updateStatus);
    }

    private void updateStatus(Status stat){
        currentStatus = stat;
        status.setText(currentStatus.getStatusText());
    }

    private void initPhotosRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = v.findViewById(R.id.photo_recyclerview_container);
        adapter = new PhotoRecyclerViewAdapter(getContext(), FROM_DETAIL_REQUEST);
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

    private void getAllProperties(){
        this.propertyViewModel.getAllProperty().observe(this, this::updatePropertyList);
    }

    private void updatePropertyList(List<Property> properties){
        numberOfProperties = properties.size();
    }

    //-----------------------------------------------------------------------------------

    private void launchMainActivityDetail(int id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(ListHouseFragment.ID_PROPERTY, id);
        startActivity(intent);
    }

    private void launchResultSearchActivityDetail(int id) {
        Boolean displayDetail = true;

        Intent intent = new Intent(getActivity(), ResultSearchActivity.class);
        intent.putExtra(ListHouseFragment.ID_PROPERTY, id);
        intent.putExtra(ListHouseFragment.DISPLAY_DETAIL, displayDetail);
            intent.putExtra(ListFilteredPropertiesFragment.FROM_FILTER, true);
            intent.putExtra(SearchActivity.RESULTS_FILTERED, true);
            intent.putExtra(ListFilteredPropertiesFragment.POSITON_IN_FILTER, newPosition);
            intent.putExtra(SearchActivity.ID_FILTERED, filteredResultsArray);

        startActivity(intent);
    }

    public static DetailFragment newInstance(int propertyId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ListHouseFragment.ID_PROPERTY, propertyId);
        fragment.setArguments(args);

        return fragment;
    }
}
