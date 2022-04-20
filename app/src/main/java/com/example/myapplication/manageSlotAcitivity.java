package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class manageSlotAcitivity extends AppCompatActivity implements View.OnClickListener {
    private Button okaButton;
    private TextInputEditText dateInputEditText;
    private String dateTextString, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_slot_acitivity);
        this.setTitle("Manage Slot");

        dateInputEditText = (TextInputEditText)findViewById(R.id.chooseDateEditTextViewId);
        dateInputEditText.setOnClickListener(this);
        okaButton = (Button)findViewById(R.id.okaButtonId);
        okaButton.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View view) {
        if(view == okaButton){

            date = dateInputEditText.getText().toString();
            if(date.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please select a date to proceed", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(getApplicationContext(),doctorSlotShowerActitivity.class);
            intent.putExtra("date",dateTextString);
            startActivity(intent);
        }
        else if(view == dateInputEditText){
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int date = calendar.get(Calendar.DATE);


            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                    dateTextString  = date +" - "+(month+1)+" - "+year;
                    dateInputEditText.setText(dateTextString);
                }
            },year,month,date);
            datePickerDialog.show();
        }
        }
    }
