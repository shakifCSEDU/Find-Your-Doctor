package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoctorsListActivity extends AppCompatActivity {

    private ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView recyclerView;
    private String doctorType, location, visitDate;
    private FirebaseDatabase db;
    private DatabaseReference root;
    private String userID;
    private ArrayList<String>userIDList = new ArrayList<String>();


    List<DoctorUser>doctorUserList  = new ArrayList<DoctorUser>();
    ArrayList<CustomRowItem>list;

    String fName,lName,institute,qualification,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        this.setTitle("Doctors List"); // Set the toolbar name
        shimmerFrameLayout = (ShimmerFrameLayout)findViewById(R.id.shimmerDoctorsId);
        shimmerFrameLayout.startShimmer();

        recyclerView = findViewById(R.id.recyclerViewId);

        String[] doctors =  getResources().getStringArray(R.array.doctors_name);
        String[] type = getResources().getStringArray(R.array.Doctor_Speciality);

        Intent intent = getIntent();
        doctorType = intent.getStringExtra("doctorType");
        location = intent.getStringExtra("location");
        visitDate = intent.getStringExtra("date");


        db = FirebaseDatabase.getInstance();
        root = db.getReference("DoctorType").child(doctorType).child(location);

        list = new ArrayList<CustomRowItem>();

        DoctorListAdapter adapter = new DoctorListAdapter(this,list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        userID = dataSnapshot.child("uid").getValue(String.class);
                        db.getReference("Users").child(userID).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                  if(snapshot.exists()){
                                      DoctorUser doctor_user = snapshot.getValue(DoctorUser.class);
                                      uid = doctor_user.getUid();
                                      fName =  doctor_user.getFirstName();
                                      lName =   doctor_user.getLastName();
                                      institute = doctor_user.getInstitute();
                                      qualification = doctor_user.getEducationalQualification();
                                      list.add(new CustomRowItem((fName+" "+lName),institute,qualification,uid,visitDate));
                                  }
                              adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"No doctor found",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}

class DoctorListDescription{
    String firstName, lastName, institute, educationalQualification, profileImage;
}
