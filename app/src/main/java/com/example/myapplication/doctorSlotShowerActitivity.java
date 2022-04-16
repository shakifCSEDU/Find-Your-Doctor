package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class doctorSlotShowerActitivity extends AppCompatActivity {
   private String dateString;
    private GridView gridView;
    private TextView dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_slot_shower_actitivity);
        this.setTitle("Doctor Slot");

        Intent intent =  getIntent();
        dateString = intent.getStringExtra("date");




        gridView = (GridView)findViewById(R.id.manageSlotGridViewId);
        dateTextView = (TextView)findViewById(R.id.dateTextViewId);
        dateTextView.setText(dateString);


        ArrayList<CustomGrid> customGrids = new ArrayList<CustomGrid>();

        for (int i = 1; i < 16; i++) {
            customGrids.add(new CustomGrid(R.drawable.slot_background_white, "s" + i));
        }


        DoctorDescriptionAdapter descriptionAdapter = new DoctorDescriptionAdapter(getApplicationContext(), customGrids);
        gridView.setAdapter(descriptionAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                view.setBackgroundColor(Color.parseColor("#00FF00"));
            }
        });


    }
}