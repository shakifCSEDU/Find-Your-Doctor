package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class homeFragment extends Fragment implements View.OnClickListener {
    private ShimmerFrameLayout shimmerFrameLayout;
    private Button homePageButton;
    private NavController navController;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase db;
    private DatabaseReference root;


    private RecyclerView recyclerView;
    private View view;
    private Context context;
    Boolean isDoctor  = false;

    private String userType, userID;

    ArrayList<homeUserClass>list1 = new ArrayList<homeUserClass>(); // Here list1 ---> patient see doctors appontment

    ArrayList<homeUserClass>list2 = new ArrayList<homeUserClass>(); // Here list1 ---> patient see doctors appontment



    String[] nameString  , locationString;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();
        userType = activity.getMyData();


        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();

        nameString = getResources().getStringArray(R.array.doctors_name);
        locationString = getResources().getStringArray(R.array.Dhaka_region);


        shimmerCode();

        return view;
    }

    private void shimmerCode() {
        shimmerFrameLayout = (ShimmerFrameLayout)view.findViewById(R.id.shimmerId);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.stopShimmer();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);
        homePageButton = (Button)view.findViewById(R.id.homePageButtonId);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewId);





        if(userType.equals("2")){ // This userType defines that this is doctor page.
            homePageButton.setText("Manage Slot");

            firebaseAuth = FirebaseAuth.getInstance();
            userID = firebaseAuth.getCurrentUser().getUid();
            db = FirebaseDatabase.getInstance();
            root = db.getReference("Users").child(userID).child("Appointments");

            homeCustomAdapter adapter = new homeCustomAdapter(context,list2,"doctor"); // here we pass patient because we want to check who he is.
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);

            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        String patientName,visitDate,visitID,phoneNumber;
                        for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                            patientName = dataSnapshot.child("patientName").getValue(String.class);
                            visitDate = dataSnapshot.child("visitDate").getValue(String.class);
                            phoneNumber  = dataSnapshot.child("patientPhoneNumber").getValue(String.class);
                            list2.add(new homeUserClass(patientName,visitDate,"visitID",phoneNumber,R.drawable.profile_picture));
                        }
                        adapter.notifyDataSetChanged();

                    }
                    else {
                        Toast.makeText(context," You don't have any next appointments",Toast.LENGTH_LONG ).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




           // list1 hoise doctor jkhn next appointment khujte jabe tkhn tar value gula jst list2 te add kre dbo...

            // added all data in the arralist.



        }









        else if(userType.equals("1")){ ///  This condition is entered for the patient .
            homePageButton.setText("Search Doctor");

            // here we pass patient because we want to check who he is.

            // added all data in the arralist.
            firebaseAuth = FirebaseAuth.getInstance();
            userID = firebaseAuth.getCurrentUser().getUid();
            db = FirebaseDatabase.getInstance();
            root = db.getReference("Users").child(userID).child("Appointments");

            homeCustomAdapter adapter = new homeCustomAdapter(context,list1,"patient");
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);


            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String doctorName, visitDate, visitID, phoneNumber, chamber, doctorType;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            doctorName = dataSnapshot.child("doctorName").getValue(String.class);
                            visitDate = dataSnapshot.child("visitDate").getValue(String.class);
                            //visitID = dataSnapshot.child("visitID").getValue(String.class);
                            phoneNumber = dataSnapshot.child("doctorPhoneNumber").getValue(String.class);
                            chamber = dataSnapshot.child("chamber").getValue(String.class);
                            doctorType = dataSnapshot.child("doctorType").getValue(String.class);
                            list1.add(new homeUserClass(doctorName, visitDate,"visitId",phoneNumber,doctorType , chamber,R.id.profileImageId));
                        }

                        adapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(context," You don't have any next appointments",Toast.LENGTH_LONG ).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

                // initialise the recyclerView

        }


        homePageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == homePageButton){
            if(userType.equals("2")){
                navController.navigate(R.id.action_homeFragment_to_manageSlotAcitivity);
            }else if(userType.equals("1")){
                navController.navigate(R.id.action_homeFragment_to_searchDoctorFragment);
            }
        }
    }
}