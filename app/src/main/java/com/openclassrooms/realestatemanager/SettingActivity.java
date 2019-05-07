package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Spinner spinnerRadius = findViewById(R.id.spinner_devise);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.settings_devise_answer, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRadius.setAdapter(adapter);
    }
}
