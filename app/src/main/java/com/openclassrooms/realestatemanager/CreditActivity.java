package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import java.text.DecimalFormat;

/**
 * Activity Credit: calculate the cost of a credit
 */

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);
        ButterKnife.bind(this);

        creditValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                creditMonth.setText(Double.toString(calculateMonth(input, interest, lenght)));
                creditCost.setText(Double.toString(calculateCost(input, lenght,calculateMonth(input, interest, lenght)) ));
            }
        });

        this.configureToolbar();

    }

    // Configure toolbar
    private void configureToolbar(){
        Toolbar toolbar;
        // Get the toolbar view inside the activity layout
        toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar
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

    public double calculateMonth(double inputValue, double interestValue, int lenghtValue) {
        double haut = inputValue * interestValue / 12 / 100;
        double bas = 1 - Math.pow(1 + (interestValue / 12 / 100), -lenghtValue * 12);

        double month = haut / bas;

        DecimalFormat df = new DecimalFormat("########.00");

        String str = df.format(month);
        month = Double.parseDouble(str.replace(',', '.'));

        return month;
    }

    public double calculateCost(double inputValue, int lenghtValue, double month) {
        double cost;
        String str;
        DecimalFormat df = new DecimalFormat("########.00");
        cost = month*lenghtValue*12-inputValue;
        str = df.format(cost);
        cost = Double.parseDouble(str.replace(',', '.'));
        return cost;
    }
}
