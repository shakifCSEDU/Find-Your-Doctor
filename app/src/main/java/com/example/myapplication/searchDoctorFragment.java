package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;


public class searchDoctorFragment extends Fragment implements View.OnClickListener {
    private AutoCompleteTextView locationAutoCompleteTextView,chooseDoctorType;
    private String locationString,chooseDateString,chooseDoctorTypeString;
    private TextInputEditText dateInputEditText;
    private NavController navController;
    private Context context;

    Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_search_doctor, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = view.getContext();
        navController = Navigation.findNavController(view);



        locationAutoCompleteTextView = (AutoCompleteTextView)view.findViewById(R.id.location_auto_completeTextViewId);
        searchButton = (Button)view.findViewById(R.id.doctorSearchButtonId);
        dateInputEditText = (TextInputEditText)view.findViewById(R.id.chooseDateEditTextViewId);
        chooseDoctorType  = (AutoCompleteTextView)view.findViewById(R.id.chooseDoctor_auto_completeTextViewId);

        String[] location = getResources().getStringArray(R.array.Dhaka_region);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.dropdown_item,location);
        locationAutoCompleteTextView.setAdapter(arrayAdapter);

        String[] doctor_type = getResources().getStringArray(R.array.Doctor_Speciality);
        ArrayAdapter<String> arrayAdapter1  = new ArrayAdapter<String>(getActivity(),R.layout.dropdown_item,doctor_type);
        chooseDoctorType.setAdapter(arrayAdapter1);

        searchButton.setOnClickListener(this);
        dateInputEditText.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == searchButton){
            locationString = locationAutoCompleteTextView.getText().toString();
            chooseDateString = dateInputEditText.getText().toString();
            chooseDoctorTypeString = chooseDoctorType.getText().toString();
            //navController.navigate(R.id.action_searchDoctorFragment_to_doctorListFragment);
            Intent intent = new Intent(context,DoctorsListActivity.class);
            startActivity(intent);


        }else if(view.getId() == R.id.chooseDateEditTextViewId){
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int date = calendar.get(Calendar.DATE);


            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                       dateInputEditText.setText(date +" / "+month+" / "+year);
                }
            },year,month,date);
          datePickerDialog.show();
        }




    }
}