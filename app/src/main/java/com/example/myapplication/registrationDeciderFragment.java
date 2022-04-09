package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class registrationDeciderFragment extends Fragment implements View.OnClickListener {

    private Button patientRegistrationButton,doctorRegistrationButton;
    private View view;
    private NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_registration_decider, container, false);
        init();
        return view;
    }

    private void init() {
       patientRegistrationButton = (Button) view.findViewById(R.id.patientRegistrationButtonId);
       doctorRegistrationButton = (Button) view.findViewById(R.id.doctorRegistrationButtonId);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        patientRegistrationButton.setOnClickListener(this);
        doctorRegistrationButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.doctorRegistrationButtonId){
            navController.navigate(R.id.action_registrationDeciderFragment_to_doctorRegistrationFragment);
        }else if(v.getId() == R.id.patientRegistrationButtonId){
            navController.navigate(R.id.action_registrationDeciderFragment_to_patientRegisterFragment);
        }
    }
}