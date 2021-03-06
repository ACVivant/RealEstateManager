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
                if(euros.getText().toString().equals("")) { Toast.makeText(context, getResources().getString(R.string.euros_needed), Toast.LENGTH_LONG).show();
                } else {
                    valueToConvert = Integer.parseInt(euros.getText().toString());;
                    convertedValue = Utils.convertEuroToDollar(valueToConvert);
                    dollars.setText(String.valueOf(convertedValue));
                }
            }
        });

        convertFromDollars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dollars.getText().toString().equals("")) { Toast.makeText(context, getResources().getString(R.string.dollars_needed), Toast.LENGTH_LONG).show();
                } else { ;
                    valueToConvert = Integer.parseInt(dollars.getText().toString());
                    convertedValue = Utils.convertDollarToEuro(valueToConvert);
                    euros.setText(String.valueOf(convertedValue));
                }
            }
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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
}
