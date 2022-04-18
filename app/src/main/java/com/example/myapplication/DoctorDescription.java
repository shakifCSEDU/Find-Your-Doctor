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
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorDescription extends AppCompatActivity implements View.OnClickListener {


    private GridView gridView;
    private FirebaseDatabase db, db2;
    private DatabaseReference root, root2;

    int[] isSelectSlot = new int[25];
    int totalSlots = 0;

   private String name,qualification,uid,institution,visitDate;
   private TextView doctorNameTextView, chamberTextView, instituteTextView, qualificationTextView, mobileNumberTextView;
   private Button proceedButton;
   Map<String, String> seatSlot = new HashMap<>();
   private String slotName = " ";
   private int count = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_description);

        this.setTitle("Doctor Description");

        initView();

        getIntentData();
        
        Toast.makeText(DoctorDescription.this,"Date " + uid, Toast.LENGTH_LONG).show();


        // Firebase database called ...
        db = FirebaseDatabase.getInstance();
        root = db.getReference("Users").child(uid);
        ArrayList<CustomGrid>customGrids = new ArrayList<CustomGrid>();
        DoctorDescriptionAdapter descriptionAdapter = new DoctorDescriptionAdapter(DoctorDescription.this ,customGrids);
        gridView.setAdapter(descriptionAdapter);


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

        root2 = db.getReference("Users").child(uid).child("Schedule").child(visitDate);

        root2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(int index = 1; index <= 15; index++){
                        String a = snapshot.child("S"+index).getValue(String.class);
                        String index1 = Integer.toString(index);
                        if(a.equals("1") || a.equals("2")){
                            customGrids.add(new CustomGrid(R.drawable.slot_background_red,"S"+index1));
                            Toast.makeText(getApplicationContext(),"S"+index1+"is booked",Toast.LENGTH_SHORT).show();
                            seatSlot.put("S"+index,"100");
                        }
                        else{
                            customGrids.add(new CustomGrid(R.drawable.slot_background_white,"S"+index1));
                            seatSlot.put("S"+index, a);
                        }
                    }
                    descriptionAdapter.notifyDataSetChanged();
                }
                else{
                    int index;
                    for(index = 1;index <= 15; index++){
                        customGrids.add(new CustomGrid(R.drawable.slot_background_white, "S"+index));
                        seatSlot.put("S"+index, "0");
                    }

                }
                descriptionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String slot = "S" + Integer.toString(position + 1);
                if(seatSlot.get(slot).equals("100")){
                    Toast.makeText(DoctorDescription.this, "This slot is already booked! Please choose another one!!", Toast.LENGTH_SHORT).show();
                }

                // if(seatMap.get(seat).equals("0")) {
                if (isSelectSlot[position] == 0) {
                    if(count>0){
                        Toast.makeText(DoctorDescription.this,"You cannot select more than one slot at a time\nPlz unselect the previous one",Toast.LENGTH_SHORT).show();
                    }
                    else if(seatSlot.get(slot).equals("0") && count == 0){
                        view.setBackgroundColor(Color.parseColor("#00FF00"));
                        Toast.makeText(getApplicationContext(), "You Selected Slot Number :" + (position + 1), Toast.LENGTH_SHORT).show();
                        isSelectSlot[position] = 1;
                        seatSlot.put(slot,"1");
                        count++;
                    }

                } else if(isSelectSlot[position] == 1){
                    view.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    Toast.makeText(getApplicationContext(), "You Unselected Seat Number :" + (position + 1), Toast.LENGTH_SHORT).show();
                    isSelectSlot[position] = 0;
                    seatSlot.put(slot,"0");
                    count--;
                }
            }
        });


         proceedButton.setOnClickListener(this);




    }

    private void initView() {
        gridView = (GridView)findViewById(R.id.gridViewId);
        doctorNameTextView = findViewById(R.id.doctorNameDescription);
        chamberTextView = findViewById(R.id.chamberDescription);
        instituteTextView = findViewById(R.id.instituteDescription);
        qualificationTextView = findViewById(R.id.qualificationDescription);
        mobileNumberTextView = findViewById(R.id.mobileNumberDescription);
        proceedButton = (Button)findViewById(R.id.proceedButtonId);


    }

    private void  getIntentData() {
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        visitDate = intent.getStringExtra("visitDate");
        name = intent.getStringExtra("name");
        qualification = intent.getStringExtra("qualification");
        institution = intent.getStringExtra("institute");
    }

    @Override
    public void onClick(View view) {

        if(view == proceedButton){
            if(count == 0){
                Toast.makeText(getApplicationContext(),"plz select a slot to proceed",Toast.LENGTH_SHORT).show();
                return;
            }else{

                Intent intent = new Intent(getApplicationContext(),CanPayActivity.class);
                intent.putExtra("doctorUid",uid);
                intent.putExtra("slotMap", (Serializable) seatSlot);
                intent.putExtra("visitDate",visitDate);
                startActivity(intent);
            }
        }
    }
}