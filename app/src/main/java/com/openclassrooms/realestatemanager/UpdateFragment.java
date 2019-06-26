package com.openclassrooms.realestatemanager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment implements AdapterView.OnItemSelectedListener, PhotoDialogFragment.DialogListener, PhotoRecyclerViewAdapter.DeletePhotoListener {

    private static final String TAG = "UpdateActivity";
    public static final String  FROM_UPDATE_REQUEST = "fromUpadateActivity";

    private PropertyViewModel propertyViewModel;

    private Spinner spinnerStatus;
    private Spinner spinnerType;
    private EditText descriptionText;
    private EditText price;
    private EditText surface;

    private LinearLayout photoLayout;
    private ImageButton updateMainPhoto;
    private TextView addMainPhotoText;
    private TextView addOthersPhotosText;
    private ImageButton addPhotos;
    private ImageView mainPhotoPreview;
    private ImageView photo1;
    private ImageView photo2;
    private ImageView photo3;
    private ImageView photo4;
    private TextView numberPhotos;

    private EditText rooms;
    private  EditText bedrooms;
    private EditText bathrooms;

    private EditText addressNumber;
    private EditText addressStreet;
    private EditText addressStreet2;
    private EditText addressZipcode;
    private EditText addressTown;
    private EditText addressCountry;

    private CheckBox checkboxSchool;
    private CheckBox checkboxShop;
    private CheckBox checkboxPark;
    private CheckBox checkboxMuseum;

    private EditText dateUpForSale;
    private EditText dateSoldOn;
    private Spinner spinnerAgent;

    private Button saveProperty;
    private Button resetProperty;

    private String newStatus;
    private String newDescription;
    private int newPrice;
    private String newType;
    private int newSurface;
    private int newRooms;
    private int newBedrooms;
    private int newBathrooms;

    private  String newAddressNumber;
    private String newAddressStreet;
    private String newAddressStreet2;
    private String newZipcode;
    private String newTown;
    private String newCountry;

    private Boolean nearSchool;
    private Boolean nearShop;
    private Boolean nearPark;
    private Boolean nearMuseum;

    private String newUpForSale;
    private String newSoldOn;
    private String newAgent;

    private int intUpForSale;
    private int intSoldOn;

    private String newPhotoUrl;

    private Property currentProperty;
    private TypeOfProperty currentType;
    private Status currentStatus;
    private Agent currentAgent;
    private int statusId;
    private int typeId;
    private int agentId;
    private int numberOfProperties;
    private int propertyId;
    private List<Photo> currentPhotos;
    private List<Long> photoToDeleteList = new ArrayList<>();
    private Photo myPhotoToDelete;

    private ArrayList<String> newPhotosList = new ArrayList<>();
    private ArrayList<String> newLegendList = new ArrayList<>();

    private PhotoRecyclerViewAdapter adapter;
    private View v;

    private String newMainPhotoUri;
    private String newMainLegend;
    private int nbrePhotos;

    private Context mContext;

    public UpdateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.activity_create_home, container, false);
        mContext = getContext();
        Bundle bundle = getArguments();
        propertyId = bundle.getInt(ListHouseFragment.ID_PROPERTY,1);

        spinnerStatus = v.findViewById(R.id.create_spinner_status);
        spinnerType= v.findViewById(R.id.create_spinner_type);
        descriptionText= v.findViewById(R.id.create_description_text);
        price= v.findViewById(R.id.create_insert_price_text);
        surface= v.findViewById(R.id.create_insert_surface);

        photoLayout= v.findViewById(R.id.create_all_others_photos);
        updateMainPhoto= v.findViewById(R.id.add_main_photo);
        addMainPhotoText= v.findViewById(R.id.create_add_main_photo_text);
        addOthersPhotosText= v.findViewById(R.id.create_add_others_photo_text);
        addPhotos= v.findViewById(R.id.add_more_photo);
        mainPhotoPreview= v.findViewById(R.id.create_main_photo_preview);
        photo1= v.findViewById(R.id.create_photo_more_preview1);
        photo2= v.findViewById(R.id.create_photo_more_preview2);
        photo3= v.findViewById(R.id.create_photo_more_preview3);
        photo4= v.findViewById(R.id.create_photo_more_preview4);
        numberPhotos= v.findViewById(R.id.number_photo_more);

        rooms= v.findViewById(R.id.create_insert_rooms);
        bedrooms= v.findViewById(R.id.create_insert_bedrooms);
        bathrooms= v.findViewById(R.id.create_insert_bathrooms);

        addressNumber= v.findViewById(R.id.create_insert_address_number);
        addressStreet= v.findViewById(R.id.create_insert_address_street);
        addressStreet2= v.findViewById(R.id.create_insert_address_street2);
        addressZipcode= v.findViewById(R.id.create_insert_address_zipcode);
        addressTown= v.findViewById(R.id.create_insert_address_town);
        addressCountry= v.findViewById(R.id.create_insert_address_country);

        checkboxSchool= v.findViewById(R.id.create_checkbox_school);
        checkboxShop= v.findViewById(R.id.create_checkbox_shop);
        checkboxPark= v.findViewById(R.id.create_checkbox_park);
        checkboxMuseum= v.findViewById(R.id.create_checkbox_museum);

        dateUpForSale= v.findViewById(R.id.create_insert_upforsale);
        dateSoldOn= v.findViewById(R.id.create_insert_soldon);
        spinnerAgent= v.findViewById(R.id.create_spinner_agent);

        saveProperty= v.findViewById(R.id.save_new_property);
        resetProperty= v.findViewById(R.id.reset_new_property);

        addMainPhotoText.setText(R.string.modify_main_photo);
        addOthersPhotosText.setText(R.string.modify_others_photo);

        // spinnerStatus = (Spinner) findViewById(R.id.create_spinner_status);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.create_status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
        spinnerStatus.setOnItemSelectedListener(this);

        //spinnerType = (Spinner) findViewById(R.id.create_spinner_type);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(mContext,
                R.array.search_type_answer, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter1);
        spinnerType.setOnItemSelectedListener(this);

        //spinnerAgent = (Spinner) findViewById(R.id.create_spinner_agent);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(mContext,
                R.array.create_agent_name, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgent.setAdapter(adapter2);
        spinnerAgent.setOnItemSelectedListener(this);

        addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(CreateHomeActivity.OTHERS_PHOTO_REQUEST);
            }
        });

        updateMainPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(CreateHomeActivity.MAIN_PHOTO_REQUEST);
            }
        });

        resetProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UpdateActivity.this.finish();
                if (v.findViewById(R.id.textView20) != null) {
                    getActivity().finish();
                } else {
                    Bundle args = new Bundle();
                    args.putInt(ListHouseFragment.ID_PROPERTY, propertyId);

                    DetailFragment detail =new DetailFragment();
                    detail.setArguments(args);
                    final FragmentManager fm = getFragmentManager();

                    fm.beginTransaction().replace(R.id.frame_layout_detail, detail, "2").commit();
                }
            }
        });

        saveProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: valider");
                //configureViewModel();
                savePropertyData();
                updateProperty();
                launchMainActivity();
            }
        });

        configureViewModel();
        launchSavedData();

        return v;
    }

    private void initPhotosRecyclerView() {
        Log.d(TAG, "initPhotosRecyclerView");

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = v.findViewById(R.id.update_photo_recyclerview_container);
        adapter = new PhotoRecyclerViewAdapter(mContext, FROM_UPDATE_REQUEST);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void savePropertyData() {
        newStatus = spinnerStatus.getSelectedItem().toString();
        statusId = spinnerStatus.getSelectedItemPosition()+1;

        if(addressNumber.getText().toString().trim().isEmpty() ||
                addressStreet.getText().toString().trim().isEmpty() ||
                addressZipcode.getText().toString().trim().isEmpty() ||
                addressTown.getText().toString().trim().isEmpty()||
                addressCountry.getText().toString().trim().isEmpty()) {
            Toast.makeText(mContext, "Il faut entrer l'adrese du bien", Toast.LENGTH_LONG).show();
        } else {

            if (!descriptionText.getText().toString().isEmpty()) {
                newDescription = descriptionText.getText().toString();
            } else {
                newDescription = "N/A";
            }

            if (!price.getText().toString().isEmpty()) {
                newPrice = Integer.parseInt(price.getText().toString());
            } else {
                newPrice = 0;
            }

            newType = spinnerType.getSelectedItem().toString();
            typeId = spinnerType.getSelectedItemPosition()+1;

            if (!surface.getText().toString().isEmpty()) {
                newSurface = Integer.parseInt(surface.getText().toString());
            } else {
                newSurface = 0;
            }

            if (!rooms.getText().toString().isEmpty()) {
                newRooms = Integer.parseInt(rooms.getText().toString());
            } else {
                newRooms = 999;
            }

            if (!bedrooms.getText().toString().isEmpty()) {
                newBedrooms = Integer.parseInt(bedrooms.getText().toString());
            } else {
                newBedrooms = 999;
            }

            if (!bathrooms.getText().toString().isEmpty()) {
                newBathrooms = Integer.parseInt(bathrooms.getText().toString());
            } else {
                newBathrooms = 999;
            }

            newAddressNumber = addressNumber.getText().toString();
            Log.d(TAG, "savePropertyData: number " + newAddressNumber);

            if (!addressStreet.getText().toString().isEmpty()) {
                newAddressStreet = addressStreet.getText().toString();
            } else {
                newAddressStreet = " ";
            }
            newAddressStreet2 = addressStreet2.getText().toString();
            newZipcode = addressZipcode.getText().toString();
            newTown = addressTown.getText().toString();
            newCountry = addressCountry.getText().toString();

            nearSchool = checkboxSchool.isChecked();
            nearShop = checkboxShop.isChecked();
            nearPark = checkboxPark.isChecked();
            nearMuseum = checkboxMuseum.isChecked();

            if (dateUpForSale.getText().toString().length()==10) {
                newUpForSale = dateUpForSale.getText().toString();
                intUpForSale = Utils.convertStringDateToIntDate(newUpForSale);
                Log.d(TAG, "savePropertyData: enteredDate "+ newUpForSale);
                Log.d(TAG, "savePropertyData: formattedDate " + intUpForSale);
            } else {
                newUpForSale = "99/99/9999";
                intUpForSale = Utils.convertStringDateToIntDate(newUpForSale);
            }

            Log.d(TAG, "savePropertyData: length date " + dateSoldOn.getText().toString().length());
            if (dateSoldOn.getText().toString().length()==10) {
                newSoldOn = dateSoldOn.getText().toString();
                intSoldOn = Utils.convertStringDateToIntDate(newSoldOn);
            } else {
                newSoldOn = "99/99/9999";
                intSoldOn = Utils.convertStringDateToIntDate(newSoldOn);
            }

            newAgent = spinnerAgent.getSelectedItem().toString();
            agentId = spinnerAgent.getSelectedItemPosition()+1;
            Log.d(TAG, "savePropertyData: agentId " +agentId);

        }
    }

    private void launchSavedData() {
        this.getProperty(propertyId);
    }

    private void getProperty(int id){
        this.propertyViewModel.getPropertyFromId(id).observe(this, this::loadProperty);
    }

    private void loadProperty(Property property) {
        currentProperty = property;

        nbrePhotos = currentProperty.getNbrePhotos();

        String upForSaleDateText = Utils.convertStringToDate(String.valueOf(currentProperty.getSoldOnDate()));
        if (upForSaleDateText.equals("99/99/9999")) {
            dateUpForSale.setText("");
        } else {
            dateUpForSale.setText(upForSaleDateText);
        }

        String soldOnDateText = Utils.convertStringToDate(String.valueOf(currentProperty.getSoldOnDate()));
        if (soldOnDateText.equals("99/99/9999")) {
            dateSoldOn.setText("");
        }else{
            dateSoldOn.setText(soldOnDateText);
        }

        if (!String.valueOf(currentProperty.getDescription()).equals("")) {
            descriptionText.setText(String.valueOf(currentProperty.getDescription()));
        } else {
            descriptionText.setText("");
        }

        if (!String.valueOf(currentProperty.getSurface()).equals("")) {
            surface.setText(String.valueOf(currentProperty.getSurface()));
        } else {
            surface.setText("");
        }

        if (!String.valueOf(currentProperty.getRooms()).equals("")) {
            rooms.setText(String.valueOf(currentProperty.getRooms()));
        } else {
            rooms.setText("");
        }

        if (!String.valueOf(currentProperty.getBedrooms()).equals("")) {
            bedrooms.setText(String.valueOf(currentProperty.getBedrooms()));
        } else {
            bedrooms.setText("");
        }

        if (!String.valueOf(currentProperty.getBathroom()).equals("")) {
            bathrooms.setText(String.valueOf(currentProperty.getBathroom()));
        } else {
            bathrooms.setText("");
        }

        checkboxShop.setChecked(currentProperty.getShop());
        checkboxSchool.setChecked(currentProperty.getSchool());
        checkboxMuseum.setChecked(currentProperty.getMuseum());
        checkboxPark.setChecked(currentProperty.getPark());

        if (!String.valueOf(currentProperty.getPrice()).equals("")) {
            price.setText(String.valueOf(currentProperty.getPrice()));
        } else {
            price.setText("");
        }

        agentId = currentProperty.getAgentId();
        typeId = currentProperty.getTypeId();
        statusId = currentProperty.getStatusId();

        //En attendant de gérer les images
        newPhotoUrl = currentProperty.getMainPhoto();

        Log.d(TAG, "updateProperty: id " +propertyId + " " +typeId+ " " + statusId);

        Glide.with(this) //SHOWING PREVIEW OF IMAGE
                .load(this.currentProperty.getMainPhoto())
                .apply(RequestOptions.circleCropTransform())
                .into(this.mainPhotoPreview);

        setAddress();
        this.getStatus(statusId);
        this.getType(typeId);
        this.getAgent(agentId);
        this.initPhotosRecyclerView();
        this.getAllPhotosFromProperty(propertyId);
    }

    private void setAddress(){
        addressNumber.setText(String.valueOf(currentProperty.getNumberInStreet()));
        Log.d(TAG, "setAddress: number " + String.valueOf(currentProperty.getNumberInStreet()));
        addressStreet.setText(String.valueOf(currentProperty.getStreet()));
        if(currentProperty.getStreet2()!= null){
            addressStreet2.setText(String.valueOf(currentProperty.getStreet2()));
        } else {addressStreet2.setText("");}
        addressZipcode.setText(String.valueOf(currentProperty.getZipcode()));
        addressTown.setText(String.valueOf(currentProperty.getTown()));
        addressCountry.setText(String.valueOf(currentProperty.getCountry()));
    }

    private void getType(int id) {
        this.propertyViewModel.getTypeFromId(id).observe(this, this::updateType);
    }

    private void updateType(TypeOfProperty typeOfProperty){
        currentType = typeOfProperty;
        Log.d(TAG, "updateType: typeId " + typeId);
        spinnerType.setSelection(typeId-1);
    }

    private void getStatus(int id) {
        this.propertyViewModel.getStatusFromId(id).observe(this, this::updateStatus);
    }

    private void updateStatus(Status stat){
        currentStatus = stat;
        spinnerStatus.setSelection(statusId-1);
    }

    private void getAgent(int id) {
        this.propertyViewModel.getAgentFromId(id).observe(this, this::updateAgent);
    }

    private void updateAgent(Agent age){
        currentAgent = age;
        spinnerAgent.setSelection(agentId-1);
    }

    private void updateProperty(){
        Log.d(TAG, "updateProperty: id " + propertyId);
        Log.d(TAG, "createProperty: statusId " +statusId);
        Log.d(TAG, "createProperty: typeId " + typeId);
        Log.d(TAG, "createProperty: agentId " +agentId);
        Log.d(TAG, "updateProperty: number " +newAddressNumber);

        if (newMainPhotoUri!=null) {
            newPhotoUrl = newMainPhotoUri;
        }

        Property myProperty = new Property(newPrice, newRooms, newBedrooms, newBathrooms, newDescription, intUpForSale, intSoldOn, newSurface, nearShop, nearSchool, nearMuseum, nearPark, typeId, agentId, statusId,newPhotoUrl, nbrePhotos,newAddressNumber, newAddressStreet, newAddressStreet2, newZipcode, newTown, newCountry );
        myProperty.setPropertyId(propertyId);

        propertyViewModel.updateProperty(myProperty);
        Toast.makeText(mContext, "Le bien a été mis à jour", Toast.LENGTH_LONG).show();

        for (int i=0; i<newPhotosList.size(); i++) {
            Log.d(TAG, "updateProperty: legend " + newLegendList.get(i));
            propertyViewModel.insertPhoto(new Photo(newPhotosList.get(i), newLegendList.get(i), propertyId));
        }

        if (photoToDeleteList!=null) {
            for (int i = 0; i < photoToDeleteList.size(); i++) {

                Log.d(TAG, "updateProperty: boucle delete photos");
                Log.d(TAG, "updateProperty: id to delete " + photoToDeleteList.get(i));
                getPhotosToDelete(photoToDeleteList.get(i));
            }
            nbrePhotos-= photoToDeleteList.size();
        }

    }

    private void getAllPhotosFromProperty(int id){
        this.propertyViewModel.getPhotoFromProperty(id).observe(this, this::updatePhotoList);
    }

    private void updatePhotoList(List<Photo> photos){
        currentPhotos = photos;
        this.adapter.setPhotos(photos);
    }

    private void getPhotosToDelete(long id){
        this.propertyViewModel.getPhotoFromId(id).observe(this, this::updatePhotoDeleteList);
    }

    private void updatePhotoDeleteList(Photo photos){
        myPhotoToDelete = photos;
        propertyViewModel.deletePhoto(myPhotoToDelete);
        Log.d(TAG, "updatePhotoDeleteList: delete photo");
    }


    private void configureViewModel(){
        Log.d(TAG, "configureViewModel");
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(mContext);
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }

    private void launchMainActivity() {
        Boolean displayDetail = false;
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(ListHouseFragment.DISPLAY_DETAIL, displayDetail);
        intent.putExtra(ListHouseFragment.ID_PROPERTY, propertyId);
        startActivity(intent);
    }

    private void openDialog(String which) {
        PhotoDialogFragment dialog = new PhotoDialogFragment();
        Bundle args = new Bundle();
        args.putString(CreateHomeActivity.WHICH_REQUEST, which);
        dialog.setArguments(args);
        dialog.show(getFragmentManager(), "dialog");
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void applyOthersPhoto(String photoUri, String photoLegend) {
        newPhotosList.add(photoUri);
        newLegendList.add(photoLegend);
        nbrePhotos+=1;
        setPreviewPhotos();
    }

    @Override
    public void applyMainPhoto(String photoUri, String photoLegend, boolean main) {
        newMainPhotoUri= photoUri;
        newMainLegend = photoLegend;
        setMainPhoto();
    }

    private void setMainPhoto() {
        Glide.with(this) //SHOWING PREVIEW OF IMAGE
                .load(this.newMainPhotoUri)
                .apply(RequestOptions.circleCropTransform())
                .into(this.mainPhotoPreview);
    }

    private void setPreviewPhotos() {
        numberPhotos.setText(String.valueOf(newPhotosList.size()));

        if (newPhotosList.size() > 0) {
            Glide.with(this) //SHOWING PREVIEW OF IMAGE
                    .load(this.newPhotosList.get(0))
                    .apply(RequestOptions.circleCropTransform())
                    .into(this.photo1);

            if (newPhotosList.size() > 1) {
                Glide.with(this) //SHOWING PREVIEW OF IMAGE
                        .load(this.newPhotosList.get(1))
                        .apply(RequestOptions.circleCropTransform())
                        .into(this.photo2);

                if (newPhotosList.size() > 2) {
                    Glide.with(this) //SHOWING PREVIEW OF IMAGE
                            .load(this.newPhotosList.get(2))
                            .apply(RequestOptions.circleCropTransform())
                            .into(this.photo3);

                    if (newPhotosList.size() > 3) {
                        Glide.with(this) //SHOWING PREVIEW OF IMAGE
                                .load(this.newPhotosList.get(3))
                                .apply(RequestOptions.circleCropTransform())
                                .into(this.photo4);
                    }
                }
            }
        }
    }

    @Override
    public void photoToDelete(long photoId) {
        photoToDeleteList.add(photoId);
    }
}
