package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class DoctorRegistrationActivity extends AppCompatActivity {

    private AutoCompleteTextView regionAutoCompleteTextView, doctorSpecialityAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        String[] Dhaka_Region = getResources().getStringArray(R.array.Dhaka_region);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<>(this, R.layout.dropdown_item, Dhaka_Region);
        regionAutoCompleteTextView.setAdapter(arrayAdapter1);

        String[] Doctor_Speciality = getResources().getStringArray(R.array.Doctor_Speciality);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, R.layout.dropdown_item, Doctor_Speciality);
        regionAutoCompleteTextView.setAdapter(arrayAdapter2);

    }
}