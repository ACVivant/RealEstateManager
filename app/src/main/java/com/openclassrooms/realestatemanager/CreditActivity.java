package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.util.Log;
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
    private double monthCapital;

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

    }

    private void getData() {
        input = Double.parseDouble(creditInput.getText().toString());
        interest = Double.parseDouble(creditInterest.getText().toString());
        lenght = Integer.parseInt(creditLenght.getText().toString());
    }

    private void calculate() {
        double[] monthRestTab = new double[(int) lenght*12];
        double[] monthPaidTab = new double[(int) lenght*12];

        monthCapital = input/lenght/12;

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
