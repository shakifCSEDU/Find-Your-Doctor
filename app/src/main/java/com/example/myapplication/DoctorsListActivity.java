package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

public class DoctorsListActivity extends AppCompatActivity {

    private ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        this.setTitle("Doctors List"); // Set the toolbar name
        shimmerFrameLayout = (ShimmerFrameLayout)findViewById(R.id.shimmerDoctorsId);
        shimmerFrameLayout.startShimmer();

        String[] doctors =  getResources().getStringArray(R.array.doctors_name);
        String[] type = getResources().getStringArray(R.array.Doctor_Speciality);


        DoctorListAdapter adapter = new DoctorListAdapter(getApplicationContext(),doctors,type);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }
}