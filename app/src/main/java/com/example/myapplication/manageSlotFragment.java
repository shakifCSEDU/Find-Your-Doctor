package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;


public class manageSlotFragment extends Fragment {

    private View view;
    private GridView gridView;
    private AutoCompleteTextView monthAutoCompleteTextView;
    private String monthString;
    private Button okaButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_manage_slot, container, false);
        monthAutoCompleteTextView = (AutoCompleteTextView)view.findViewById(R.id.month_auto_completeTextViewId);
        gridView = (GridView)view.findViewById(R.id.manageSlotGridViewId);


        // Auto complete textView field...
        String[] month = getResources().getStringArray(R.array.month);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.dropdown_item,month);
        monthAutoCompleteTextView.setAdapter(arrayAdapter);
        monthString = monthAutoCompleteTextView.getText().toString();

    return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(monthString.isEmpty()){
            monthAutoCompleteTextView.setError("Month field is Required");
            monthAutoCompleteTextView.requestFocus();
            return;
        }
        ArrayList<CustomGrid>customGrids = new ArrayList<CustomGrid>();

        for(int i = 1 ; i<16 ; i++){
            customGrids.add(new CustomGrid(R.drawable.slot_background_white,"s"+i));
        }
        DoctorDescriptionAdapter descriptionAdapter = new DoctorDescriptionAdapter(view.getContext() ,customGrids);
        gridView.setAdapter(descriptionAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                view.setBackgroundColor(Color.parseColor("#00FF00"));
            }
        });
    }
}