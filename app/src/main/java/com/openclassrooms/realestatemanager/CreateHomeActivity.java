package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.openclassrooms.realestatemanager.utils.Utils;

public class CreateHomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private PropertyViewModel propertyViewModel;

    private static final String TAG = "CreateHomeActivity";

    public static final String EXTRA_TYPE =
            "com.openclassrooms.realestatemanager.EXTRA_TYPE";
    public static final String EXTRA_DESCRIPTION =
            "com.openclassrooms.realestatemanager.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRICE =
            "com.openclassrooms.realestatemanager.EXTRA_PRICE";
    public static final String EXTRA_SURFACE =
            "com.openclassrooms.realestatemanager.EXTRA_SURFACE";
    public static final String EXTRA_ROOMS =
            "com.openclassrooms.realestatemanager.EXTRA_ROOMS";
    public static final String EXTRA_BEDROOMS =
            "com.openclassrooms.realestatemanager.EXTRA_BEDROOMS";
    public static final String EXTRA_BATHROOMS =
            "com.openclassrooms.realestatemanager.EXTRA_BATHROOMS";
    public static final String EXTRA_ADDRESS_NUMBER =
            "com.openclassrooms.realestatemanager.EXTRA_ADDRESS_NUMBER";
    public static final String EXTRA_STREET =
            "com.openclassrooms.realestatemanager.EXTRA_STREET";
    public static final String EXTRA_STREET2 =
            "com.openclassrooms.realestatemanager.EXTRA_STREET2";
    public static final String EXTRA_ZIPCODE =
            "com.openclassrooms.realestatemanager.EXTRA_ZIPCODE";
    public static final String EXTRA_TOWN =
            "com.openclassrooms.realestatemanager.EXTRA_TOWN";
    public static final String EXTRA_COUNTRY =
            "com.openclassrooms.realestatemanager.EXTRA_COUNTRY";
    public static final String EXTRA_SCHOOL =
            "com.openclassrooms.realestatemanager.EXTRA_SCHOOL";
    public static final String EXTRA_SHOP =
            "com.openclassrooms.realestatemanager.EXTRA_SHOP";
    public static final String EXTRA_PARK =
            "com.openclassrooms.realestatemanager.EXTRA_PARK";
    public static final String EXTRA_MUSEUM =
            "com.openclassrooms.realestatemanager.EXTRA_MUSEUM";
    public static final String EXTRA_UPFORSALE =
            "com.openclassrooms.realestatemanager.EXTRA_UPFORSALE";
    public static final String EXTRA_SOLDON =
            "com.openclassrooms.realestatemanager.EXTRA_SOLDON";
    public static final String EXTRA_AGENT =
            "com.openclassrooms.realestatemanager.EXTRA_AGENT";

    @BindView(R.id.create_spinner_status)
    Spinner spinnerStatus;
    @BindView(R.id.create_spinner_type)
    Spinner spinnerType;
    @BindView(R.id.create_decription_text)
    EditText descriptionText;
    @BindView(R.id.create_insert_price_text)
    EditText price;
    @BindView(R.id.create_insert_surface)
    EditText surface;

    @BindView(R.id.create_insert_rooms)
    EditText rooms;
    @BindView(R.id.create_insert_bedrooms)
    EditText bedrooms;
    @BindView(R.id.create_insert_bathrooms)
    EditText bathrooms;

    @BindView(R.id.create_insert_address_number)
    EditText addressNumber;
    @BindView(R.id.create_insert_address_street)
    EditText addressStreet;
    @BindView(R.id.create_insert_address_street2)
    EditText addressStreet2;
    @BindView(R.id.create_insert_address_zipcode)
    EditText addressZipcode;
    @BindView(R.id.create_insert_address_town)
    EditText addressTown;
    @BindView(R.id.create_insert_address_country)
    EditText addressCountry;

    @BindView(R.id.create_checkbox_school)
    CheckBox checkboxSchool;
    @BindView(R.id.create_checkbox_shop)
    CheckBox checkboxShop;
    @BindView(R.id.create_checkbox_park)
    CheckBox checkboxPark;
    @BindView(R.id.create_checkbox_museum)
    CheckBox checkboxMuseum;

    @BindView(R.id.create_insert_upforsale)
    EditText dateUpForSale;
    @BindView(R.id.create_insert_soldon)
    EditText dateSoldOn;
    @BindView(R.id.create_spinner_agent)
    Spinner spinnerAgent;

    @BindView(R.id.save_new_property)
    Button saveProperty;
    @BindView(R.id.save_new_property_add_photos)
    Button addPhotos;
    @BindView(R.id.reset_new_property)
    Button resetProperty;

    String newStatus;
    String newDescription;
    int newPrice;
    String newType;
    int newSurface;
    int newRooms;
    int newBedrooms;
    int newBathrooms;

    String newAddressNumber;
    String newAddressStreet;
    String newAddressStreet2;
    String newZipcode;
    String newTown;
    String newCountry;

    Boolean nearSchool;
    Boolean nearShop;
    Boolean nearPark;
    Boolean nearMuseum;

    String newUpForSale;
    String newSoldOn;
    String newAgent;

    int intUpForSale;
    int intSoldOn;

    String newPhotoUrl;

    int agentId;
    int statusId;
    int addressId;
    int typeId;

    private Address myAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_home);
        ButterKnife.bind(this);

        configureView();

    }

    private void configureView() {
        spinnerStatus = (Spinner) findViewById(R.id.create_spinner_status);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.create_status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
        spinnerStatus.setOnItemSelectedListener(this);

        spinnerType = (Spinner) findViewById(R.id.create_spinner_type);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.search_type_answer, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter1);
        spinnerType.setOnItemSelectedListener(this);

        spinnerAgent = (Spinner) findViewById(R.id.create_spinner_agent);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.create_agent_name, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgent.setAdapter(adapter2);
        spinnerAgent.setOnItemSelectedListener(this);

        saveProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureViewModel();
                savePropertyData();
                createProperty();
                launchMainActivity();
            }
        });

        addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        resetProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void savePropertyData() {
        newStatus = spinnerStatus.getSelectedItem().toString();
        statusId = spinnerStatus.getSelectedItemPosition()+1;

        if(addressNumber.getText().toString().trim().isEmpty() ||
                addressStreet.getText().toString().trim().isEmpty() ||
                addressZipcode.getText().toString().trim().isEmpty() ||
                addressTown.getText().toString().trim().isEmpty()||
                addressCountry.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Il faut entrer l'adrese du bien", Toast.LENGTH_LONG).show();
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

            if (!dateUpForSale.getText().toString().isEmpty()) {
                newUpForSale = dateUpForSale.getText().toString();
                intUpForSale = Utils.convertStringDateToIntDate(newUpForSale);
                Log.d(TAG, "savePropertyData: enteredDate "+ newUpForSale);
                Log.d(TAG, "savePropertyData: formattedDate " + intUpForSale);
            } else {
                newUpForSale = "99/99/9999";
                intUpForSale = Utils.convertStringDateToIntDate(newUpForSale);
            }

            if (!dateSoldOn.getText().toString().isEmpty()) {
                newSoldOn = dateSoldOn.getText().toString();
                intSoldOn = Utils.convertStringDateToIntDate(newSoldOn);
            } else {
                newSoldOn = "99/99/9999";
                intSoldOn = Utils.convertStringDateToIntDate(newSoldOn);
            }

            newAgent = spinnerAgent.getSelectedItem().toString();
            agentId = spinnerAgent.getSelectedItemPosition()+1;
            Log.d(TAG, "savePropertyData: agentId " +agentId);

            // En attendant de gérer les images
            newPhotoUrl = "https://www.wedemain.fr/photo/art/default/4860223-7252677.jpg?v=1351271929?auto=compress,format&q=80&h=100&dpr=2";

        }
    }

    private void createProperty(){
        Log.d(TAG, "createProperty: statusId " +statusId);
        Log.d(TAG, "createProperty: typeId " + typeId);
        Log.d(TAG, "createProperty: agentId " +agentId);
        Property myProperty = new Property(newPrice, newRooms, newBedrooms, newBathrooms, newDescription, intUpForSale, intSoldOn, newSurface, nearShop, nearSchool, nearMuseum, nearPark, typeId, agentId, statusId,newPhotoUrl,newAddressNumber, newAddressStreet, newAddressStreet2, newZipcode, newTown, newCountry );

            propertyViewModel.insertProperty(myProperty);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void configureViewModel(){
        Log.d(TAG, "configureViewModel");
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }

    private void launchMainActivity() {
        Boolean displayDetail = false;
        Intent intent = new Intent(CreateHomeActivity.this, MainActivity.class);
        intent.putExtra(MapActivity.ID_FRAGMENT, "1");
        intent.putExtra(ListHouseFragment.DISPLAY_DETAIL, displayDetail);
        startActivity(intent);
    }


}
