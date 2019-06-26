package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CreditActivity extends AppCompatActivity {

    private static final String TAG = "CreditActivity";

    @BindView(R.id.credit_input)
    EditText creditInput;
    @BindView(R.id.credit_interest)
    EditText creditInterest;
    @BindView(R.id.credit_length)
    EditText creditLenght;
    @BindView(R.id.credit_month)
    TextView creditMonth;
    @BindView(R.id.credit_total)
    TextView creditCost;
    @BindView(R.id.credit_validate)
    ImageButton creditValidate;

    private double input;
    private double interest;
    private  int lenght;
    private double cost;
    private double month;

    //Design
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        ButterKnife.bind(this);

        creditValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                calculate();
            }
        });

        this.configureToolbar();

    }

    // Configure toolbar
    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return false;
    }

    private void getData() {
        input = Double.parseDouble(creditInput.getText().toString());
        interest = Double.parseDouble(creditInterest.getText().toString());
        lenght = Integer.parseInt(creditLenght.getText().toString());
    }

    private void calculate() {

        double haut = input*interest/12/100;
        Log.d(TAG, "calculate: haut " + haut);
        double bas = 1- Math.pow(1+(interest/12/100), -lenght*12);
        Log.d(TAG, "calculate: bas " + bas);

        month = haut/bas;
        Log.d(TAG, "calculate: mensualité " + month);

        DecimalFormat df = new DecimalFormat("########.00");

        String str = df.format(month);
        month = Double.parseDouble(str.replace(',', '.'));
        creditMonth.setText(Double.toString(month));

        cost = month*lenght*12-input;
        str = df.format(cost);
        cost = Double.parseDouble(str.replace(',', '.'));
        creditCost.setText(Double.toString(cost));
    }
}
