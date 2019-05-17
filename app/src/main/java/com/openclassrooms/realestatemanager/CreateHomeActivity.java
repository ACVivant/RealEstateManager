package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateHomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    @BindView(R.id.create_insert_mandatory)
    EditText agent;

    @BindView(R.id.save_new_property)
    Button saveProperty;
    @BindView(R.id.save_new_property_add_photos)
    Button addPhotos;
    @BindView(R.id.reset_new_property)
    Button resetProperty;

    String newType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_home);
        ButterKnife.bind(this);

        configureView();

    }

    private void configureView() {
        Spinner spinner = (Spinner) findViewById(R.id.create_spinner_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_type_answer, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        saveProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProperty();
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

    private void saveProperty() {

        String newDescription = descriptionText.getText().toString();
        int newPrice = Integer.parseInt(price.getText().toString());
        int newSurface = Integer.parseInt(surface.getText().toString());
        int newRooms = Integer.parseInt(rooms.getText().toString());
        int newBedroomse = Integer.parseInt(bedrooms.getText().toString());
        int newBathrooms = Integer.parseInt(bathrooms.getText().toString());

        String newAddressNumber = addressNumber.getText().toString();
        String newAdressStreet = addressStreet.getText().toString();
        String newAdressStreet2 = addressStreet2.getText().toString();
        String newZipcode = addressZipcode.getText().toString();
        String newTown = addressTown.getText().toString();
        String newCountry = addressCountry.getText().toString();

        Boolean nearSchool = checkboxSchool.isChecked();
        Boolean nearShop = checkboxShop.isChecked();
        Boolean nearPark = checkboxPark.isChecked();
        Boolean nearMuseum = checkboxMuseum.isChecked();

        String newUpForSale = dateUpForSale.getText().toString();
        String newSoldOn = dateSoldOn.getText().toString();
        String newAgent = agent.getText().toString();

        if (newAgent.trim().isEmpty()) {
            Toast.makeText(this, R.string.please_insert_agent,Toast.LENGTH_LONG ).show();
        } else {
            Intent data = new Intent();
            data.putExtra(EXTRA_DESCRIPTION, newDescription);
            data.putExtra(EXTRA_TYPE, newType);
            data.putExtra(EXTRA_SURFACE, newSurface);
            data.putExtra(EXTRA_ROOMS, newRooms);
            data.putExtra(EXTRA_BEDROOMS, newBedroomse);
            data.putExtra(EXTRA_BATHROOMS, newBathrooms);
            data.putExtra(EXTRA_ADDRESS_NUMBER, newAddressNumber);
            data.putExtra(EXTRA_STREET, newAdressStreet);
            data.putExtra(EXTRA_STREET2, newAdressStreet2);
            data.putExtra(EXTRA_ZIPCODE, newZipcode);
            data.putExtra(EXTRA_TOWN, newTown);
            data.putExtra(EXTRA_COUNTRY, newCountry);
            data.putExtra(EXTRA_SCHOOL, nearSchool);
            data.putExtra(EXTRA_SHOP, nearShop);
            data.putExtra(EXTRA_PARK, nearPark);
            data.putExtra(EXTRA_MUSEUM, nearMuseum);
            data.putExtra(EXTRA_UPFORSALE, newUpForSale);
            data.putExtra(EXTRA_SOLDON, newSoldOn);
            data.putExtra(EXTRA_AGENT, newAgent);

            setResult(RESULT_OK, data);
            finish();

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        newType = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
