package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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

public class DoctorsListActivity extends AppCompatActivity {

    private ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView recyclerView;
    private String doctorType, location, visitDate;
    private FirebaseDatabase db;
    private DatabaseReference root;
    private String userID;
    private ArrayList<String>userIDList=new ArrayList<String>();
    ArrayList<HashMap<String,String>> doctorsList= new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        this.setTitle("Doctors List"); // Set the toolbar name
        shimmerFrameLayout = (ShimmerFrameLayout)findViewById(R.id.shimmerDoctorsId);
        shimmerFrameLayout.startShimmer();

        String[] doctors =  getResources().getStringArray(R.array.doctors_name);
        String[] type = getResources().getStringArray(R.array.Doctor_Speciality);

        Intent intent = getIntent();
        doctorType = intent.getStringExtra("doctorType");
        location = intent.getStringExtra("location");
        visitDate = intent.getStringExtra("date");

        db = FirebaseDatabase.getInstance();
        root = db.getReference("DoctorType").child(doctorType).child(location);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        userID = dataSnapshot.child("uid").getValue(String.class);
                        userIDList.add(userID);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        for(String list : userIDList){
            db.getReference("Users").child(list).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    DoctorListDescription doctorListDescription = snapshot.getValue(DoctorListDescription.class);
                    HashMap<String,String> mp = new HashMap<>();
                    mp.put("doctorName",doctorListDescription.firstName+ " "+ doctorListDescription.lastName);
                    mp.put("institute",doctorListDescription.institute);
                    mp.put("qualification",doctorListDescription.educationalQualification);
                    mp.put("image",doctorListDescription.profileImage);
                    doctorsList.add(mp);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }









        DoctorListAdapter adapter = new DoctorListAdapter(getApplicationContext(),doctorsList);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

}

class DoctorListDescription{
    String firstName, lastName, institute, educationalQualification, profileImage;
}
