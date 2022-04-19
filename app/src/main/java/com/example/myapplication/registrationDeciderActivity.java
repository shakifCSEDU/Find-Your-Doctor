package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class registrationDeciderActivity extends AppCompatActivity implements View.OnClickListener{
    private Button patientRegistrationButton,doctorRegistrationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_decider);
        this.setTitle("Registration Decider");
        patientRegistrationButton = (Button)findViewById(R.id.patientRegistrationButtonId);
        doctorRegistrationButton = (Button)findViewById(R.id.doctorRegistrationButtonId);
        patientRegistrationButton.setOnClickListener(this);
        doctorRegistrationButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.doctorRegistrationButtonId){
            Intent intent = new Intent(this, doctorRegistrationActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.patientRegistrationButtonId){
            Intent intent = new Intent(this, patientRegistrationAcitivity.class);
            startActivity(intent);
        }
    }




}