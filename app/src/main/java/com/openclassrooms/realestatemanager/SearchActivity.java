package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.sqlite.db.SimpleSQLiteQuery;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final String TAG = "SearchActivity";
    public static final String ID_FILTERED = "list_of_filtered_properties";
    public static final String RESULTS_FILTERED = "results_are_filtered";
    public static final String SEARCH_QUERY = "search_query";

    @BindView(R.id.spinner_type)
    Spinner spinnerType;
    @BindView(R.id.price_min)
    EditText priceMin;
    @BindView(R.id.price_max)
    EditText priceMax;
    @BindView(R.id.surface_min)
            EditText surfMin;
    @BindView(R.id.surface_max)
            EditText surfMax;
    @BindView(R.id.rooms_min)
            EditText rooms;
    @BindView(R.id.bedrooms_min)
            EditText bedrooms;
    @BindView(R.id.bathrooms_min)
            EditText bathrooms;
    @BindView(R.id.photos_min)
            EditText photos;
    @BindView(R.id.filter_zipcode)
            EditText zipcode;
    @BindView(R.id.filter_town)
            EditText town;
    @BindView(R.id.filter_country)
            EditText country;
    @BindView(R.id.filter_school)
    CheckBox school;
    @BindView(R.id.filter_shop)
            CheckBox shop;
    @BindView(R.id.filter_park)
            CheckBox park;
    @BindView(R.id.filter_museum)
            CheckBox museum;
    @BindView(R.id.spinner_status)
    Spinner spinnerStatus;
    @BindView(R.id.soldOn_min)
            EditText solOnMin;
    @BindView(R.id.soldOn_max)
            EditText soldOnMax;
    @BindView(R.id.upForSale_max)
            EditText upForSaleMax;
    @BindView(R.id.upForSale_min)
            EditText upForSaleMin;
    @BindView(R.id.spinner_responsable)
            Spinner spinnerAgent;

    @BindView(R.id.search_cancel_btn)
    Button cancelBtn;
    @BindView(R.id.search_validation_btn)
            Button validationBtn;

    private PropertyViewModel propertyViewModel;

    private Toolbar toolbar;

    // Query string
    String queryString = new String();

    // List of bind parameters
    List<Object> args = new ArrayList();

    boolean containsCondition = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        validationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
                createQuery();
                getFilteredProperties();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        configureSpinner();
        configureViewModel();
        configureToolbar();
    }

    private  void configureSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_status_ask, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
        spinnerStatus.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.filter_type_ask, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter1);
        spinnerType.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.filter_agent_ask, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgent.setAdapter(adapter2);
        spinnerAgent.setOnItemSelectedListener(this);
    }

    // Configure toolbar
    private void configureToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return false;
    }

    private void createQuery() {
        // Beginning of query string
        queryString += "SELECT * FROM Property";

        // Optional parts are added to query string and to args upon here
        if(!spinnerAgent.getSelectedItem().toString().equals(getResources().getString(R.string.all))) {
            queryString += " WHERE ";
            queryString += "agentId = ?";
            int argAgent = spinnerAgent.getSelectedItemPosition()+1;
            args.add(argAgent);
            Log.d(TAG, "createQuery: argAgent " + argAgent);
            containsCondition = true;
        }

        if(!spinnerStatus.getSelectedItem().toString().equals(getResources().getString(R.string.all))) {
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += "statusId = ?";
            int argStatus= spinnerStatus.getSelectedItemPosition()+1;
            args.add(argStatus);
            Log.d(TAG, "createQuery: argStatus " + argStatus);
            containsCondition = true;
        }

        if(!spinnerType.getSelectedItem().toString().equals(getResources().getString(R.string.all))) {
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += "typeId = ?";
            int argType = spinnerType.getSelectedItemPosition()+1;
            args.add(argType);
            Log.d(TAG, "createQuery: argType " + argType);
            containsCondition = true;
        }

        if(!priceMin.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " price > ?";
            int minPrice = Integer.parseInt(priceMin.getText().toString()) ;
            //queryString += minPrice;
            args.add(minPrice);
            containsCondition = true;
        }

        if(!priceMax.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " price < ? ";
            int maxPrice = Integer.parseInt(priceMax.getText().toString()) ;
            args.add(maxPrice);
            containsCondition = true;
        }

        if(!surfMin.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " surface > ?";
            int minSurf = (int)  Integer.parseInt(surfMin.getText().toString()) ;
           args.add(minSurf);
            containsCondition = true;
        }

        if(!surfMax.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " surface < ?";
            int maxSurf = (int)  Integer.parseInt(surfMax.getText().toString()) ;
            args.add(maxSurf);
            containsCondition = true;
        }

        if(!rooms.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " rooms > ?";
            int minRooms = (int)  Integer.parseInt(rooms.getText().toString()) ;
            args.add(minRooms);
            containsCondition = true;
        }

        if(!bedrooms.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " bedrooms > ?";
            int minBedrooms = (int)  Integer.parseInt(bedrooms.getText().toString()) ;
            args.add(minBedrooms);
            containsCondition = true;
        }

        if(!bathrooms.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " bathroom > ?";
            int minBathrooms = (int)  Integer.parseInt(bathrooms.getText().toString()) ;
            args.add(minBathrooms);
            containsCondition = true;
        }

        if(!photos.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " nbrePhotos > ?";
            int minPhotos = (int)  Integer.parseInt(photos.getText().toString()) ;
            args.add(minPhotos+1);
            containsCondition = true;
        }

        if(!zipcode.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " zipcode = ?";
            args.add(zipcode.getText().toString());
            containsCondition = true;
        }

        if(!town.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " town = ?";
            args.add(town.getText().toString());
            containsCondition = true;
        }

        if(!country.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " country = ?";
            args.add(country.getText().toString());
            containsCondition = true;
        }

        if(school.isChecked()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " school = ?";
            args.add(true);
            containsCondition = true;
        }

        if(shop.isChecked()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " shop = ?";
            args.add(true);
            containsCondition = true;
        }

        if(park.isChecked()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " park = ?";
            args.add(true);
            containsCondition = true;
        }

        if(museum.isChecked()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " museum = ?";
            args.add(true);
            containsCondition = true;
        }

        if(!soldOnMax.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " soldOnDate > ?";
            int soldOnMaxInt = Utils.convertStringDateToIntDate(soldOnMax.getText().toString());
            args.add(soldOnMaxInt);
            containsCondition = true;
        }

        if(!solOnMin.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " soldOnDate < ?";
            int soldOnMinInt = Utils.convertStringDateToIntDate(solOnMin.getText().toString());
            args.add(soldOnMinInt);
            containsCondition = true;
        }

        if(!upForSaleMin.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " upForSaleDate > ?";
            int upForSaleMinInt = Utils.convertStringDateToIntDate(upForSaleMin.getText().toString());
            args.add(upForSaleMinInt);
            containsCondition = true;
        }

        if(!upForSaleMax.getText().toString().trim().isEmpty()){
            if (containsCondition) {
                queryString += " AND ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += " upForSaleDate < ?";
            int upForSaleMaxInt = Utils.convertStringDateToIntDate(upForSaleMax.getText().toString());
            args.add(upForSaleMaxInt);
            containsCondition = true;
        }

// End of query string
        queryString += ";";

        Log.d(TAG, "createQuery: QueryString "+ queryString);
        Log.d(TAG, "createQuery: args " + args);
    }



    private void getFilteredProperties(){
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString, args.toArray());
        this.propertyViewModel.getFilteredProperties(query).observe(this, this::updatePropertyList);
    }

    private void updatePropertyList(List<Property> properties) {

        ArrayList<Integer> filteredId = new ArrayList<Integer>();

        for (int i = 0; i < properties.size(); i++) {
            filteredId.add(properties.get(i).getPropertyId());
        }
        launchResultActivity( queryString, filteredId);
           }

    private void launchResultActivity(String query, ArrayList<Integer> tabId) {
        Intent intent = new Intent(this, ResultSearchActivity.class);
        intent.putExtra(RESULTS_FILTERED, true);
        intent.putExtra(SEARCH_QUERY, query);
        intent.putExtra(ID_FILTERED, tabId);
        startActivity(intent);
    }

    private void configureViewModel(){
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
