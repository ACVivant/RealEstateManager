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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    //Design
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView  navigationView;

    //Fragments
    final ListHouseFragment fragment1 = new ListHouseFragment();
    final DetailFragment fragment2 = new DetailFragment();

    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    private int clic = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        //this.configureBottomNavigationView();
        this.configureFirstView();

        if (isServiceOK()) {
            init();
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
        if (findViewById(R.id.frame_layout_detail) == null) {
            fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
            fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();
        } else {
            fm.beginTransaction().add(R.id.frame_layout_detail, fragment2, "2").commit();
            fm.beginTransaction().add(R.id.frame_layout_list, fragment1, "1").commit();
        }
    }

/*    private void configureBottomNavigationView() {
        //We only add use bottom navigation in phone mode (If not found frame_layout_detail)
        if (findViewById(R.id.frame_layout_detail) == null) {
            BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }
    }*/

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


    // Actions from bottom bar
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_navigation_list:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    getSupportActionBar().setTitle(R.string.nav_title);
                    clic = 1; // To know which fragment was choosen how to design ActionBar
                    invalidateOptionsMenu();
                    return true;

                case R.id.bottom_navigation_detail:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    getSupportActionBar().setTitle(R.string.nav_title);
                    clic = 2;
                    invalidateOptionsMenu();
                    return true;
            }
            return false;
        }
    };

    // Actions from navigation drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_add :
                launchCreate();
                return true;

            case R.id.menu_update:
                launchUpdate();
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

            case R.id.top_menu_update:
                launchUpdate();
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

    private void init(){

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

