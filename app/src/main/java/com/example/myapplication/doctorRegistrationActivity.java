package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class doctorRegistrationActivity extends AppCompatActivity {

    private AutoCompleteTextView doctorRegionAutoCompleteTextView, doctorSpecialityAutoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        doctorRegionAutoCompleteTextView = findViewById(R.id.regLocation);
        doctorSpecialityAutoCompleteTextView = findViewById(R.id.regSpeciality);

        String[] doctorRegion = getResources().getStringArray(R.array.Dhaka_region);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_item,doctorRegion);
        doctorRegionAutoCompleteTextView.setAdapter(arrayAdapter1);

        String[] doctorSpeciality = getResources().getStringArray(R.array.Doctor_Speciality);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_item,doctorSpeciality);
        doctorSpecialityAutoCompleteTextView.setAdapter(arrayAdapter2);


    }
}