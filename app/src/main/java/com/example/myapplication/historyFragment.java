package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class historyFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase db;
    private DatabaseReference root;


    private RecyclerView recyclerView;
    private View view;


    private Context context;


    private String userType, userID;

    ArrayList<homeUserClass> list1 = new ArrayList<homeUserClass>(); // Here list1 ---> patient see doctors appontment

    ArrayList<homeUserClass>list2 = new ArrayList<homeUserClass>(); // Here list1 ---> patient see doctors appontment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        userType = activity.getMyData();
        view =  inflater.inflate(R.layout.fragment_history, container, false);

        context = view.getContext();
    return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


        if(userType.equals("2")){ // This userType defines that this is doctor page.


            firebaseAuth = FirebaseAuth.getInstance();
            userID = firebaseAuth.getCurrentUser().getUid();
            db = FirebaseDatabase.getInstance();
            root = db.getReference("Users").child(userID).child("Appointments");


            homeCustomAdapter adapter = new homeCustomAdapter(context,list2,"doctor"); // here we pass patient because we want to check who he is.
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);


            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){

                        String patientName,visitDate,visitId,phoneNumber,patientCancelState,patientConfirmState,patientUid,slot;

                        for (DataSnapshot dataSnapshot :snapshot.getChildren()){

                            patientUid = dataSnapshot.child("patientUid").getValue(String.class);
                            patientCancelState = dataSnapshot.child("patientCancelState").getValue(String.class);
                            patientConfirmState = dataSnapshot.child("patientConfirmState").getValue(String.class);
                            visitId = dataSnapshot.child("visitId").getValue(String.class);

                            patientName = dataSnapshot.child("patientName").getValue(String.class);
                            visitDate = dataSnapshot.child("visitDate").getValue(String.class);
                            phoneNumber  = dataSnapshot.child("patientPhoneNumber").getValue(String.class);
                            slot = dataSnapshot.child("slots").getValue(String.class);


                            Calendar calendar = Calendar.getInstance();


                            String[] parse = visitDate.split(" - ");

//                           //nt vdate = Integer.parseInt(parse[0]);
//                            int vmonth = Integer.parseInt(parse[1]);
//                            int vyear = Integer.parseInt(parse[2]);
////
                            calendar = Calendar.getInstance();

                            SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy - MM - dd");

                            String  currentDate = currentDateFormat.format(calendar.getTime());

                            String[] parse1 = currentDate.split(" - ");
//
                            if(parse[0].length() == 1)parse[0] ="0"+parse[0];
                            if(parse[1].length() == 1)parse[1] = "0"+parse[1];
//
                            String vDate = parse[2]+parse[1]+parse[0];
                            String cDate = parse1[0]+parse1[1]+parse1[2];

                            if(vDate.compareTo(cDate) < 0){
                                list2.add(new homeUserClass(patientName , visitDate , visitId , phoneNumber,slot));
                            }

                        }
                        adapter.notifyDataSetChanged();

                    }
                    else {
                        Toast.makeText(context," You don't have any history",Toast.LENGTH_LONG ).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


        else if(userType.equals("1")){ ///  This condition is entered for the patient .

            firebaseAuth = FirebaseAuth.getInstance();
            userID = firebaseAuth.getCurrentUser().getUid();
            db = FirebaseDatabase.getInstance();
            root = db.getReference("Users").child(userID).child("Appointments");



            homeCustomAdapter adapter = new homeCustomAdapter(context,list1,"patient");

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);


            root.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){
                        String doctorName, visitDate, visitId, phoneNumber, chamber, doctorType,slot;

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                            visitId  = dataSnapshot.child("visitId").getValue(String.class);
                            doctorName = dataSnapshot.child("doctorName").getValue(String.class);
                            visitDate = dataSnapshot.child("visitDate").getValue(String.class);

                            phoneNumber = dataSnapshot.child("doctorPhoneNumber").getValue(String.class);
                            chamber = dataSnapshot.child("chamber").getValue(String.class);
                            doctorType = dataSnapshot.child("doctorType").getValue(String.class);
                            slot = dataSnapshot.child("slots").getValue(String.class);

                            Calendar calendar = Calendar.getInstance();


                            String[] parse = visitDate.split(" - ");

//                           //nt vdate = Integer.parseInt(parse[0]);
//                            int vmonth = Integer.parseInt(parse[1]);
//                            int vyear = Integer.parseInt(parse[2]);
////
                            calendar = Calendar.getInstance();

                            SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy - MM - dd");

                            String  currentDate = currentDateFormat.format(calendar.getTime());

                            String[] parse1 = currentDate.split(" - ");
//
                            if(parse[0].length() == 1)parse[0] ="0"+parse[0];
                            if(parse[1].length() == 1)parse[1] = "0"+parse[1];
//
                            String vDate = parse[2]+parse[1]+parse[0];
                            String cDate = parse1[0]+parse1[1]+parse1[2];

                            if(vDate.compareTo(cDate) < 0){
                                list1.add(new homeUserClass(doctorName,visitDate,visitId,phoneNumber,doctorType,chamber,slot));
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(context," You don't have any history",Toast.LENGTH_LONG ).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        list1.clear();
        list2.clear();

    }



}