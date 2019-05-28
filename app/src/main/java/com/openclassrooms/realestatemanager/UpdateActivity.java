package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.List;

public class UpdateActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "UpdateActivity";

    private PropertyViewModel propertyViewModel;

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

    private Property currentProperty;
    //private Address currentAddress;
    private TypeOfProperty currentType;
    private Status currentStatus;
    private Agent currentAgent;
    private int statusId;
    private int typeId;
    private int agentId;
    private int numberOfProperties;
    private int propertyId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_home);
        ButterKnife.bind(this);
        propertyId= getIntent().getIntExtra(ListHouseFragment.ID_PROPERTY, 1);
        Log.d(TAG, "onCreate: propertyId " +propertyId);

        configureView();
        configureViewModel();
        launchSavedData();
    }

    public void configureView() {
        // spinnerStatus = (Spinner) findViewById(R.id.create_spinner_status);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.create_status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
        spinnerStatus.setOnItemSelectedListener(this);

        //spinnerType = (Spinner) findViewById(R.id.create_spinner_type);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.search_type_answer, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter1);
        spinnerType.setOnItemSelectedListener(this);

        //spinnerAgent = (Spinner) findViewById(R.id.create_spinner_agent);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.create_agent_name, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgent.setAdapter(adapter2);
        spinnerAgent.setOnItemSelectedListener(this);

        resetProperty.setVisibility(View.GONE);

        saveProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //configureViewModel();
                savePropertyData();
                updateProperty();
                launchMainActivity();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

        setAddress();
        this.getStatus(statusId);
        this.getType(typeId);
        this.getAgent(agentId);
    }

    private void setAddress(){
        addressNumber.setText(String.valueOf(currentProperty.getNumberInStreet()));
        Log.d(TAG, "setAddress: number " + String.valueOf(currentProperty.getNumberInStreet()));
        addressStreet.setText(String.valueOf(currentProperty.getStreet()));
        if (!String.valueOf(currentProperty.getStreet2()).equals("")) {
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
        Log.d(TAG, "createProperty: statusId " +statusId);
        Log.d(TAG, "createProperty: typeId " + typeId);
        Log.d(TAG, "createProperty: agentId " +agentId);
        Log.d(TAG, "updateProperty: number " +newAddressNumber);
        Property myProperty = new Property(newPrice, newRooms, newBedrooms, newBathrooms, newDescription, intUpForSale, intSoldOn, newSurface, nearShop, nearSchool, nearMuseum, nearPark, typeId, agentId, statusId,newPhotoUrl,newAddressNumber, newAddressStreet, newAddressStreet2, newZipcode, newTown, newCountry );

        propertyViewModel.updateProperty(myProperty);
        Toast.makeText(this, "Le bien a été mis à jour", Toast.LENGTH_LONG).show();
    }

    private void configureViewModel(){
        Log.d(TAG, "configureViewModel");
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }

    private void launchMainActivity() {
        Boolean displayDetail = false;
        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        intent.putExtra(ListHouseFragment.DISPLAY_DETAIL, displayDetail);
        startActivity(intent);
    }
}
