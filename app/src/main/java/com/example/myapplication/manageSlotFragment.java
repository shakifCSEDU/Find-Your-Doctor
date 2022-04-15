package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        String[] from = getResources().getStringArray(R.array.month);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.dropdown_item,from);
        monthAutoCompleteTextView.setAdapter(arrayAdapter);
        monthString = monthAutoCompleteTextView.getText().toString();

    return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       gridView = (GridView)view.findViewById(R.id.manageSlotGridViewId);



        if(monthString.isEmpty()){
            monthAutoCompleteTextView.setError("Month field is Required");
            monthAutoCompleteTextView.requestFocus();
            return;
        }
        int totalDate = 30;
        if(monthString.equals("January") || monthString.equals("March") || monthString.equals("May") ||
                monthString.equals("July") || monthString.equals("August") || monthString.equals("October") ||
                monthString.equals("December")){
               totalDate = 31;
        }else if(monthString.equals("February"))totalDate = 28;



       ArrayList<gridItemClass>list = new ArrayList<>();

       list.add(new gridItemClass(R.drawable.slot_background_green,"1"));
       list.add(new gridItemClass(R.drawable.slot_background_red,"2"));

       for(int i = 3 ; i<= totalDate ; i++)
       {
           list.add(new gridItemClass(R.drawable.slot_background_white,i+""));
       }
        gridView.setVisibility(View.VISIBLE);
       gridView.setVisibility(View.VISIBLE);
       GridAdapter adapter  = new GridAdapter(view.getContext(),list);
       gridView.setAdapter(adapter);



    }
}