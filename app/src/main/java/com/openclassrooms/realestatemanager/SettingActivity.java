package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.utils.Utils;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "SettingActivity";

    @BindView(R.id.convert_euros)
    EditText euros;
    @BindView(R.id.convert_dollars)
    EditText dollars;
    @BindView(R.id.convert_btn2)
    ImageButton convertFromEuros;
    @BindView((R.id.convert_btn1))
    ImageButton convertFromDollars;

    Context context;
    private int valueToConvert;
    private double convertedValue;

    //Design
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        context = this.getApplicationContext();

        this.configureToolbar();

        convertFromEuros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(euros.getText().toString().equals("")) { Toast.makeText(context, "Il faut entrer une valeur en euros.", Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG, "onClick: euros: " + euros.getText().toString());
                    valueToConvert = Integer.parseInt(euros.getText().toString());;
                    convertedValue = Utils.convertEuroToDollar(valueToConvert);
                    dollars.setText(String.valueOf(convertedValue));
                }
            }
        });

        convertFromDollars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dollars.getText().toString().equals("")) { Toast.makeText(context, "Il faut entrer une valeur en dollars.", Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG, "onClick: dollars: " + dollars.getText().toString());
                    valueToConvert = Integer.parseInt(dollars.getText().toString());
                    convertedValue = Utils.convertDollarToEuro(valueToConvert);
                    euros.setText(String.valueOf(convertedValue));
                }
            }
        });
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
        getMenuInflater().inflate(R.menu.top_toolbar_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {*/
            super.onBackPressed();
       // }
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

    private void launchMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
