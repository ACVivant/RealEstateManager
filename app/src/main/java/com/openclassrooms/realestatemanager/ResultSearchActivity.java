package com.openclassrooms.realestatemanager;

//import android.support.v7.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Property;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ResultSearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "ResultSearchActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    public static final int CREATE_PROPERTY_REQUEST = 333;
    private static final String ID_FRAGMENT = "fragment_to_expose";
    private static final String ID_PLACE = "id_of_place";
    public static final String USE_TABLET = "mobile_or_tablet";

    //Design
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView  navigationView;

    //Fragments
    final ListFilteredPropertiesFragment fragment1 = new ListFilteredPropertiesFragment();
    final DetailFragment fragment2 = new DetailFragment();

    final FragmentManager fm = getSupportFragmentManager();
    private Fragment active = fragment1;
    private String fragmentToExpose;
    private String fragmentToExposeFromMap;
    private Bundle state;
    private int homeToExpose;
    private boolean displayDetail = false;
    private boolean filteredResults = false;
    private ArrayList<Integer> filteredResultsArray = new ArrayList<>();
    private String searchQuery;
    private int position;


    private int clic = 0;
    private boolean firstView1 = false;
    private boolean firstView2 = false;

    //ViewModel
    private PropertyViewModel propertyViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        //this.configureBottomNavigationView();

        filteredResultsArray = getIntent().getIntegerArrayListExtra(SearchActivity.ID_FILTERED);
        position = getIntent().getIntExtra(ListFilteredPropertiesFragment.POSITON_IN_FILTER, 0);
        Log.d(TAG, "onCreate: position " +position);
        Log.d(TAG, "onCreate: filteredResuls " + filteredResultsArray.size());
        homeToExpose =filteredResultsArray.get(position);
        displayDetail = getIntent().getBooleanExtra(ListHouseFragment.DISPLAY_DETAIL, false);
        filteredResults = getIntent().getBooleanExtra(SearchActivity.RESULTS_FILTERED, true);

        Log.d(TAG, "onCreate: homeToExpose id " + homeToExpose);


            if (savedInstanceState == null) {
                Log.d(TAG, "onCreate: savedInstanceState null");
                this.configureFirstView();
            }

            // Decide which fragment has to be shown (rotation)
            if (savedInstanceState != null) {
                Log.d(TAG, "onCreate: savedInstanceState non null");
                this.configureView();
            }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // Configure toolbar
    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }

    // Configure Drawer Layout
    private void configureDrawerLayout(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // Configure NavigationView
    private void configureNavigationView(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // Configure first view
    private void configureFirstView() {
        Log.d(TAG, "configureFirstView");
        Bundle args = new Bundle();
        args.putInt(ListHouseFragment.ID_PROPERTY, homeToExpose);
        args.putBoolean(SearchActivity.RESULTS_FILTERED, filteredResults);
        args.putIntegerArrayList(SearchActivity.ID_FILTERED, filteredResultsArray);
        args.putInt(ListFilteredPropertiesFragment.POSITON_IN_FILTER, position);
        //    if(filteredResults) {args.putString(SearchActivity.SEARCH_QUERY, searchQuery);}

        if (findViewById(R.id.frame_layout_detail) == null) {
            Log.d(TAG, "configureFirstView: mobile");

            args.putBoolean(USE_TABLET, false);
            fragment2.setArguments(args);
            fragment1.setArguments(args);


            if (!displayDetail) {
                fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
                fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();
            } else {
                fm.beginTransaction().add(R.id.main_container, fragment1, "1").hide(fragment1).commit();
                fm.beginTransaction().add(R.id.main_container, fragment2, "2").commit();
            }

        } else {

            args.putBoolean(USE_TABLET, true);
            fragment2.setArguments(args);
            fragment1.setArguments(args);

            Log.d(TAG, "configureFirstView: tablette");

            fm.beginTransaction().add(R.id.frame_layout_detail, fragment2, "2").commit();
            fm.beginTransaction().add(R.id.frame_layout_list, fragment1, "1").commit();

        }
        firstView2 = true;
    }

    // Configure view after rotation
    private void configureView() {
        // Ajouter les infos pour afficher la bonne maison
        Bundle args = new Bundle();
        args.putInt(ListHouseFragment.ID_PROPERTY, homeToExpose);
        args.putBoolean(SearchActivity.RESULTS_FILTERED, filteredResults);
        args.putIntegerArrayList(SearchActivity.ID_FILTERED, filteredResultsArray);
        args.putInt(ListFilteredPropertiesFragment.POSITON_IN_FILTER, position);
        //  if(filteredResults) {args.putString(SearchActivity.SEARCH_QUERY, searchQuery);}
        Log.d(TAG, "configureView: bundle " + homeToExpose);
        fragment1.setArguments(args);
        fragment2.setArguments(args);

        Log.d(TAG, "configureView");
        if (findViewById(R.id.frame_layout_detail) == null) {
            if (fragmentToExposeFromMap.equals("1") || displayDetail) {
                fm.beginTransaction().hide(fragment2).commit();
                fm.beginTransaction().show(fragment1).commit();
            }
            } else {
                fm.beginTransaction().hide(fragment1).commit();
                fm.beginTransaction().show(fragment2).commit();
            }
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu and add it to the top Toolbar
        getMenuInflater().inflate(R.menu.top_toolbar_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Actions from navigation drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_add :
                launchCreate();
                return true;

            case R.id.menu_search:
                launchSearch();
                return true;

            case R.id.menu_param:
                launchSetting();
                return true;

            case R.id.menu_map:
                launchMap();
                return true;
        }
        return false;
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

    private void launchSetting() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    private void launchUpdate() {
        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);
    }

    private void launchMap() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

//-------------------------------------------------------
// Gère la rotation
// ------------------------------------------------------


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String idFragment = active.getTag();
        outState.putString(ID_FRAGMENT, idFragment);
    }
}
