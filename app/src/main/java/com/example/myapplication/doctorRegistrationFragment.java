package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class doctorRegistrationFragment extends Fragment {

    private View view;
    private AutoCompleteTextView doctorRegionAutoCompleteTextView, doctorSpecialityAutoCompleteTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doctor_registration, container, false);
        doctorRegionAutoCompleteTextView = view.findViewById(R.id.regLocation);
        doctorSpecialityAutoCompleteTextView = view.findViewById(R.id.regSpeciality);

        String[] doctorRegion = getResources().getStringArray(R.array.Dhaka_region);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(),R.layout.dropdown_item,doctorRegion);
        doctorRegionAutoCompleteTextView.setAdapter(arrayAdapter1);

        String[] doctorSpeciality = getResources().getStringArray(R.array.Doctor_Speciality);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity(),R.layout.dropdown_item,doctorSpeciality);
        doctorSpecialityAutoCompleteTextView.setAdapter(arrayAdapter2);

        return view;
    }
}