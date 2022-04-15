package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class DoctorDescription extends AppCompatActivity {


    private RecyclerView recyclerView;

    ArrayList<String>dates = new ArrayList<String>();
    ArrayList<Integer>image = new ArrayList<Integer>();

   private String name,qualification,uid,institution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_description);

        this.setTitle("Doctor Description");
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewId);

        getIntentData();


        for(int i = 1;i<=30 ; i++){
            dates.add(i+"");
            image.add(R.drawable.slot_background_white);
        }


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        recyclerView.setLayoutManager(linearLayoutManager);


        DoctorDescriptionAdapter descriptionAdapter = new DoctorDescriptionAdapter(getApplicationContext() , image , dates);
        recyclerView.setAdapter(descriptionAdapter);



    }

    private void getIntentData() {
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        name = intent.getStringExtra("name");
        qualification = intent.getStringExtra("qualification");
        institution = intent.getStringExtra("institute");
        Toast.makeText(this, uid+"   "+name+"  "+qualification+"  "+institution, Toast.LENGTH_SHORT).show();



    }
}