package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorDescription extends AppCompatActivity {


    private GridView gridView;

    private FirebaseDatabase db, db2;
    private DatabaseReference root, root2;

    int[] isSelectSeat = new int[25];
    int totalSeats = 0;

   private String name,qualification,uid,institution,visitDate;
   private TextView doctorNameTextView, chamberTextView, instituteTextView, qualificationTextView, mobileNumberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_description);

        this.setTitle("Doctor Description");



        initView();

        getIntentData();


        // Firebase database called ...
        db = FirebaseDatabase.getInstance();
        root = db.getReference("Users").child(uid);
        Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_LONG).show();

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DoctorUser doctorUser = snapshot.getValue(DoctorUser.class);
                doctorNameTextView.setText(doctorUser.getFirstName()+" "+doctorUser.getLastName());
                chamberTextView.setText(doctorUser.getChamber());
                instituteTextView.setText(doctorUser.getInstitute());
                qualificationTextView.setText(doctorUser.getEducationalQualification());
                mobileNumberTextView.setText(doctorUser.getMobileNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Setting GridView.....
        ArrayList<CustomGrid>customGrids = new ArrayList<CustomGrid>();
        for(int i = 1 ; i<16 ; i++){
            customGrids.add(new CustomGrid(R.drawable.slot_background_white,"s"+i));
        }
        DoctorDescriptionAdapter descriptionAdapter = new DoctorDescriptionAdapter(getApplicationContext() ,customGrids);
        gridView.setAdapter(descriptionAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                view.setBackgroundColor(Color.parseColor("#00FF00"));
            }
        });



//        root2 = db.getReference("Users").child(uid).child("Schedule").child(visitDate);
//
//        root2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    int i;
//                    for(i = 1; i<=30; i++){
//                        String a = snapshot.child("S"+i).getValue(String.class);
//                        String i1 = Integer.toString(i);
//                        if(a.equals("1") || a.equals("2")){
//                            dates.add(i1+"");
//                            image.add(R.drawable.slot_background_red);
//                            slotMap.put("S"+i,"100");
//                        }
//                        else{
//                            dates.add(i1+"");
//                            image.add(R.drawable.slot_background_white);
//                            slotMap.put("S"+i,a);
//                        }
//                    }
//                    descriptionAdapter.notifyDataSetChanged();
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "Data does not exist", Toast.LENGTH_LONG).show();
//                    for (int i = 1; i <= 30; i++) {
//                        dates.add(i + "");
//                        image.add(R.drawable.slot_background_white);
//                        slotMap.put("S" + i, "0");
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });






    }

    private void initView() {
        gridView = (GridView)findViewById(R.id.gridViewId);
        doctorNameTextView = findViewById(R.id.doctorNameDescription);
        chamberTextView = findViewById(R.id.chamberDescription);
        instituteTextView = findViewById(R.id.instituteDescription);
        qualificationTextView = findViewById(R.id.qualificationDescription);
        mobileNumberTextView = findViewById(R.id.mobileNumberDescription);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        visitDate = intent.getStringExtra("visitDate");
        name = intent.getStringExtra("name");
        qualification = intent.getStringExtra("qualification");
        institution = intent.getStringExtra("institute");
    }
}