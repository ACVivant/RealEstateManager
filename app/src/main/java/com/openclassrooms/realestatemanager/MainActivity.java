package com.openclassrooms.realestatemanager;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PhotoRecyclerViewAdapter.DeletePhotoListener, ListHouseFragment.OnItemRVClickedListener{

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final String ID_FRAGMENT = "fragment_to_expose";
    public static final String USE_TABLET = "mobile_or_tablet";
    public static final String PROPERTY_ID_SAVED = "property_saved_when_rotation";
    public static final String POSITION_SAVED = "position_saved_when_rotation";

    //Design
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView  navigationView;

    //Fragments
    ListHouseFragment fragment1 = new ListHouseFragment();
    DetailFragment fragment2 = new DetailFragment();

    final FragmentManager fm = getSupportFragmentManager();
    //private Fragment active = fragment1;
    //private String fragmentToExposeFromMap;
    private int homeToExpose;
    //private boolean displayDetail = false;

    private int positionRV;

    private int propertyIdClicked;

    private  boolean tabletSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();

        tabletSize = getResources().getBoolean(R.bool.isTablet);

        // id de la propriété à exposer
        homeToExpose = getIntent().getIntExtra(ListHouseFragment.ID_PROPERTY, 1);
        // on a besoin du fragment détail et pas de la liste
        //displayDetail = getIntent().getBooleanExtra(ListHouseFragment.DISPLAY_DETAIL, false);
        // On vient de MapActivity
        //fragmentToExposeFromMap = getIntent().getStringExtra(ID_FRAGMENT);
        positionRV = getIntent().getIntExtra(ListHouseFragment.POSITION_IN_RV, 0);

        Log.d(TAG, "onCreate: homeToExpose id " + homeToExpose);

       // if (fragmentToExposeFromMap == null) {
            if (savedInstanceState == null) {
                Log.d(TAG, "onCreate: savedInstanceState null");
                this.configureFirstView();
            }

            // Decide which fragment has to be shown (rotation)
            if (savedInstanceState != null) {
                Log.d(TAG, "onCreate: savedInstanceState non null");
                homeToExpose = savedInstanceState.getInt(PROPERTY_ID_SAVED);
                positionRV = savedInstanceState.getInt(POSITION_SAVED);
                //this.configureFirstView();
                this.configureView();
            }
/*        } else {
            if (savedInstanceState == null) {
                this.configureViewFromMapOrRecyclerView();
            } else {
                homeToExpose = savedInstanceState.getInt(PROPERTY_ID_SAVED);
                positionRV = savedInstanceState.getInt(POSITION_SAVED);
                //this.configureFirstView();
                this.configureView();
            }
        }*/

        isServiceOK();
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

        Bundle args = new Bundle();
            args.putInt(ListHouseFragment.ID_PROPERTY, homeToExpose);
            args.putInt(ListHouseFragment.POSITION_IN_RV, positionRV);

        if (!tabletSize) {
                Log.d(TAG, "configureFirstView: mobile");

                args.putBoolean(USE_TABLET, false);
                fragment2.setArguments(args);
                fragment1.setArguments(args);

               // if (!displayDetail) {
                    fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
                    fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();
               /* } else {
                    fm.beginTransaction().add(R.id.main_container, fragment1, "1").hide(fragment1).commit();
                    fm.beginTransaction().add(R.id.main_container, fragment2, "2").commit();
                }*/
            } else {

                args.putBoolean(USE_TABLET, true);
                fragment2.setArguments(args);
                fragment1.setArguments(args);

                Log.d(TAG, "configureFirstView: tablette");
                Log.d(TAG, "configureFirstView: position_in_rv " + positionRV);

                fm.beginTransaction().add(R.id.frame_layout_detail, fragment2, "2").commit();
                fm.beginTransaction().add(R.id.frame_layout_list, fragment1, "1").commit();

            }
    }

    // Configure view after rotation
    private void configureView() {

        Bundle args = new Bundle();
        args.putInt(ListHouseFragment.ID_PROPERTY, homeToExpose);
        args.putInt(ListHouseFragment.POSITION_IN_RV, positionRV);
        args.putInt("TEST", 5);

        Log.d(TAG, "configureView: bundle " + homeToExpose);
        Log.d(TAG, "configureView: position RV " + positionRV);
        fragment1.setArguments(args);
        fragment2.setArguments(args);

        Log.d(TAG, "configureView4");
        ListHouseFragment rotation =new ListHouseFragment();
        rotation.setArguments(args);

        if (tabletSize) {
            fm.beginTransaction().replace(R.id.frame_layout_list, rotation, "1").commit();
        } else {
            fm.beginTransaction().replace(R.id.main_container, rotation, "1").commit();
        }

      /*  Log.d(TAG, "configureView1");
        if (findViewById(R.id.frame_layout_detail) == null) {

            if (fragmentToExposeFromMap!=null) {
                if (fragmentToExposeFromMap.equals("1") || displayDetail) {
                    //fm.beginTransaction().hide(fragment2).commit();
                    //fm.beginTransaction().show(fragment1).commit();
                    Log.d(TAG, "configureView2");
                    ListHouseFragment rotation =new ListHouseFragment();
                    rotation.setArguments(args);

                    fm.beginTransaction().replace(R.id.frame_layout_list, rotation, "1").commit();
                }
            }else {
                Log.d(TAG, "configureView3");
                ListHouseFragment rotation =new ListHouseFragment();
                rotation.setArguments(args);

                fm.beginTransaction().replace(R.id.frame_layout_list, rotation, "1").commit();

                //fm.beginTransaction().hide(fragment1).commit();
                //fm.beginTransaction().show(fragment2).commit();
            }
        }*/

    }

    // Configure view when called by mapActivity
   /* private void configureViewFromMapOrRecyclerView() {
        Log.d(TAG, "configureViewFromMapOrRecyclerView");
        Bundle args = new Bundle();
        args.putInt(ListHouseFragment.ID_PROPERTY, homeToExpose);
        args.putBoolean(ListHouseFragment.DISPLAY_DETAIL, displayDetail);
        Log.d(TAG, "configureView: bundle " + homeToExpose);
        fragment1.setArguments(args);
        fragment2.setArguments(args);

            if (findViewById(R.id.frame_layout_detail) == null) {
                Log.d(TAG, "configureViewFromMapOrRecyclerView: mode telephone");

                fm.beginTransaction().add(R.id.main_container, fragment1, "1").hide(fragment1).commit();
                fm.beginTransaction().add(R.id.main_container, fragment2, "2").commit();
                // Ajouter les infos pour afficher la bonne maison}
            } else {
                Log.d(TAG, "configureViewFromMapOrRecyclerView: mode tablette");
                fm.beginTransaction().add(R.id.frame_layout_detail, fragment2, "2").commit();
                fm.beginTransaction().add(R.id.frame_layout_list, fragment1, "1").commit();
            }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu and add it to the top Toolbar
        if (findViewById(R.id.frame_layout_detail) != null) {
            getMenuInflater().inflate(R.menu.top_toolbar_main_menu_update, menu);
        } else {
            getMenuInflater().inflate(R.menu.top_toolbar_main_menu, menu);
        }
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

            case R.id.menu_credit:
                launchCredit();
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

            case R.id.top_menu_update:
                launchUpdate(propertyIdClicked);
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

    private void launchCredit() {
        Intent intent = new Intent(this, CreditActivity.class);
        startActivity(intent);
    }

    private void launchMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void launchUpdate(int propertyId) {
        Bundle args = new Bundle();
        args.putInt(ListHouseFragment.ID_PROPERTY, propertyId);

        UpdateFragment update =new UpdateFragment();
        update.setArguments(args);

        fm.beginTransaction().replace(R.id.frame_layout_detail, update, "3").commit();
    }


//--------------------------------------------------------
// Verify connection to Map
//-------------------------------------------------------
public boolean isServiceOK() {
    Log.d(TAG, "isServiceOK: Tracking GoogleServices version");

    int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

    if (available == ConnectionResult.SUCCESS) {
        // Everything is fine and the user ccan make map request
        Log.d(TAG, "isServiceOK: GooglePlayServices is working");
        return true;
    } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
        // an error occured but we can resolve it
        Log.d(TAG, "isServiceOK: An error occured but we can fix it");
        Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
        dialog.show();
    } else {
        Toast.makeText(this, R.string.map_connection_error, Toast.LENGTH_LONG).show();
    }
    return false;
}

//-------------------------------------------------------
// Gère la rotation
// ------------------------------------------------------


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int idSaved = propertyIdClicked;
        outState.putInt(PROPERTY_ID_SAVED, idSaved);
        outState.putInt(POSITION_SAVED, positionRV);
        Log.d(TAG, "onSaveInstanceState");
        Log.d(TAG, "onSaveInstanceState: idsaved " + idSaved);
        Log.d(TAG, "onSaveInstanceState: positionRV " + positionRV);
        //String idFragment = active.getTag();
        //outState.putString(ID_FRAGMENT, idFragment);
    }

    @Override
    public void photoToDelete(long photoId) {

    }

    @Override
    public void onItemRVClicked(int propertyId, int position) {
        Log.d(TAG, "onItemRVClicked: Item cliqué " + propertyId);
        propertyIdClicked = propertyId;
        positionRV = position;

        if (!tabletSize) {  // cas du téléphone
            Log.d(TAG, "onItemRVClicked: portable");
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(ListHouseFragment.ID_PROPERTY, propertyId);
            startActivity(intent);
        } else { // cas de la tablette
            Log.d(TAG, "onItemRVClicked: tablette");
            Bundle args = new Bundle();
            args.putInt(ListHouseFragment.ID_PROPERTY, propertyId);

             DetailFragment detail =fragment2.newInstance(propertyId);

            fm.beginTransaction().replace(R.id.frame_layout_detail, detail, "2").commit();

        }
    }
}
//---------------------------------------------------------
// A conserver
//---------------------------------------------------------
/*        // CORRECTION 1
        //this.textViewMain = findViewById(R.id.activity_second_activity_text_view_main); erreur sur l'id fourni
        this.textViewMain = findViewById(R.id.activity_main_activity_text_view_main);
        this.textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity);*/

/*        // CORRECTION 2
        //this.textViewQuantity.setText(quantity);
        this.textViewQuantity.setText(String.valueOf(quantity));*/


/*if (getIntent().getStringExtra(ID_FRAGMENT) != null) {
        homeToExpose = getIntent().getStringExtra(ID_PLACE);
        fragmentToExpose = getIntent().getStringExtra(ID_FRAGMENT);
        Log.d(TAG, "onCreate: fragment_id " + fragmentToExpose);
        Log.d(TAG, "onCreate: homeToExpose " + homeToExpose);}*/
