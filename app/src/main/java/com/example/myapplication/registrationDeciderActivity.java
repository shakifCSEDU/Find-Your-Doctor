package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class registrationDeciderActivity extends AppCompatActivity implements View.OnClickListener{
    private Button patientRegistrationButton,doctorRegistrationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_decider);
        patientRegistrationButton = (Button)findViewById(R.id.patientRegistrationButtonId);
        doctorRegistrationButton = (Button)findViewById(R.id.doctorRegistrationButtonId);
        patientRegistrationButton.setOnClickListener(this);
        doctorRegistrationButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.doctorRegistrationButtonId){

        }else if(v.getId() == R.id.patientRegistrationButtonId){

        }
    }




}