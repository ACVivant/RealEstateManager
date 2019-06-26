package com.openclassrooms.realestatemanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ResultSearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,PhotoRecyclerViewAdapter.DeletePhotoListener, ListFilteredPropertiesFragment.OnItemFilteredRVClickedListener{

    private static final String TAG = "ResultSearchActivity";
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
    private int homeToExpose;
    private boolean filteredResults = false;
    private ArrayList<Integer> filteredResultsArray = new ArrayList<>();
    private int position;
    private int propertyIdClicked=1;

    private  boolean tabletSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tabletSize = getResources().getBoolean(R.bool.isTablet);

        setContentView(R.layout.activity_main);
        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        filteredResultsArray = getIntent().getIntegerArrayListExtra(SearchActivity.ID_FILTERED);
        position = getIntent().getIntExtra(ListFilteredPropertiesFragment.POSITON_IN_FILTER, 0);
        homeToExpose = getIntent().getIntExtra(ListFilteredPropertiesFragment.ID_PROPERTY, 1);
        filteredResults = getIntent().getBooleanExtra(SearchActivity.RESULTS_FILTERED, true);

            if (savedInstanceState == null) {
                this.configureFirstView();
            }

            // Decide which fragment has to be shown (rotation)
            if (savedInstanceState != null) {
                propertyIdClicked = savedInstanceState.getInt(MainActivity.PROPERTY_ID_SAVED);
                this.configureView();
            }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // Configure toolbar
    private void configureToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        fragment2.setArguments(args);
        fragment1.setArguments(args);

        if (!tabletSize) {
                fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
                fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();

        } else {
            fm.beginTransaction().add(R.id.frame_layout_detail, fragment2, "2").commit();
                fm.beginTransaction().add(R.id.frame_layout_list, fragment1, "1").commit();
        }
    }

    // Configure view after rotation
    private void configureView() {

        Bundle args = new Bundle();
        args.putInt(ListHouseFragment.ID_PROPERTY, propertyIdClicked);
        args.putBoolean(SearchActivity.RESULTS_FILTERED, filteredResults);
        args.putIntegerArrayList(SearchActivity.ID_FILTERED, filteredResultsArray);
        args.putInt(ListFilteredPropertiesFragment.POSITON_IN_FILTER, position);

        fragment1.setArguments(args);
        fragment2.setArguments(args);

        ListFilteredPropertiesFragment rotation =new ListFilteredPropertiesFragment();
        rotation.setArguments(args);

        if (tabletSize) {
            fm.beginTransaction().replace(R.id.frame_layout_list, rotation, "1").commit();
        } else {
            fm.beginTransaction().replace(R.id.main_container, rotation, "1").commit();
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
            case R.id.menu_home:
                launchMain();
                return true;

            case R.id.menu_add :
                launchCreate();
                return true;

            case R.id.menu_search:
                launchSearch();
                return true;

            case R.id.menu_convert:
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

            case R.id.top_menu_home:
                launchMain();
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

    private void launchMap() {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    private void launchMain() {
        Intent intent = new Intent(this, MainActivity.class);
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
        outState.putInt(MainActivity.PROPERTY_ID_SAVED, propertyIdClicked);
        Log.d(TAG, "onSaveInstanceState: propertyIdClicked " + propertyIdClicked);
    }

    @Override
    public void photoToDelete(long photoId) {
    }

    @Override
    public void onFilteredItemRVClicked(int propertyId, int position) {
        propertyIdClicked = propertyId;

        if (!tabletSize) {  // cas du téléphone
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(ListHouseFragment.ID_PROPERTY, propertyId);
            intent.putExtra(SearchActivity.RESULTS_FILTERED,true);
            intent.putExtra(SearchActivity.ID_FILTERED, filteredResultsArray);
            intent.putExtra(ListFilteredPropertiesFragment.POSITON_IN_FILTER, position);
            startActivity(intent);
        } else { // cas de la tablette
            Bundle args = new Bundle();
            args.putInt(ListHouseFragment.ID_PROPERTY, propertyId);
            DetailFragment detail =fragment2.newInstance(propertyId);
            fm.beginTransaction().replace(R.id.frame_layout_detail, detail, "2").commit();
        }
    }
}
