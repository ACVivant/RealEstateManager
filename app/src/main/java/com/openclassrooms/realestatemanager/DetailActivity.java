package com.openclassrooms.realestatemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements PhotoRecyclerViewAdapter.DeletePhotoListener {

    //Design
    private Toolbar toolbar;
    //private DrawerLayout drawerLayout;
    //private NavigationView navigationView;

    final FragmentManager fm = getSupportFragmentManager();

    DetailFragment fragmentDetail = new DetailFragment();

    private int propertyId;
    private boolean filteredResults;
    private ArrayList<Integer> filteredResultsArray = new ArrayList<>();
    private int filteredPosition;

    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.configureToolbar();

        propertyId = getIntent().getIntExtra(ListHouseFragment.ID_PROPERTY,1);
        filteredResults = getIntent().getBooleanExtra(SearchActivity.RESULTS_FILTERED, false);
        filteredResultsArray = getIntent().getIntegerArrayListExtra(SearchActivity.ID_FILTERED);
        filteredPosition = getIntent().getIntExtra(ListFilteredPropertiesFragment.POSITON_IN_FILTER, 0);


        Bundle args = new Bundle();
        args.putInt(ListHouseFragment.ID_PROPERTY, propertyId);
        args.putBoolean(SearchActivity.RESULTS_FILTERED, filteredResults);
        args.putIntegerArrayList(SearchActivity.ID_FILTERED, filteredResultsArray);
        args.putInt(ListFilteredPropertiesFragment.POSITON_IN_FILTER, filteredPosition);
        fragmentDetail.setArguments(args);

        if (savedInstanceState != null) {
                   return;
        } else {
            fm.beginTransaction().add(R.id.detail_container, fragmentDetail, "2").commit();
        }
        Log.d(TAG, "onCreate: ");
    }

    // Configure toolbar
    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu and add it to the top Toolbar
        getMenuInflater().inflate(R.menu.top_toolbar_main_menu_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top_menu_add:
                launchCreate();
                return true;

            case R.id.top_menu_search:
                launchSearch();
                return true;

            case R.id.top_menu_update:
                launchUpdateActivity();
                return true;
        }
        return false;
    }

    private void launchCreate() {
        Intent intent = new Intent(this, CreateHomeActivity.class);
        startActivity(intent);
    }

    private void launchSearch() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void photoToDelete(long photoId) {

    }

    private void launchUpdateActivity() {
        Intent intent = new Intent(DetailActivity.this, UpdateActivity.class);
        intent.putExtra(ListHouseFragment.ID_PROPERTY, propertyId);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int idSaved = propertyId;
        outState.putInt(MainActivity.PROPERTY_ID_SAVED, idSaved);
        Log.d(TAG, "onSaveInstanceState");
        Log.d(TAG, "onSaveInstanceState: idsaved " + idSaved);
    }

}