package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


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


    private String userType, userID;

    ArrayList<homeUserClass>list1 = new ArrayList<homeUserClass>(); // Here list1 ---> patient see doctors appontment

    ArrayList<homeUserClass>list2 = new ArrayList<homeUserClass>(); // Here list1 ---> patient see doctors appontment

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();
        userType = activity.getMyData();


        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();

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
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


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

                        String patientName,visitDate,visitId,phoneNumber,patientCancelState,patientConfirmState,patientUid;

                        for (DataSnapshot dataSnapshot :snapshot.getChildren()){

                            patientUid = dataSnapshot.child("patientUid").getValue(String.class);
                            patientCancelState = dataSnapshot.child("patientCancelState").getValue(String.class);
                            patientConfirmState = dataSnapshot.child("patientConfirmState").getValue(String.class);
                            visitId = dataSnapshot.child("visitId").getValue(String.class);

                            patientName = dataSnapshot.child("patientName").getValue(String.class);
                            visitDate = dataSnapshot.child("visitDate").getValue(String.class);
                            phoneNumber  = dataSnapshot.child("patientPhoneNumber").getValue(String.class);

                            Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int date = calendar.get(Calendar.DATE);

                            String[] parse = visitDate.split(" - ");

                            int vdate = Integer.parseInt(parse[0]);
                            int vmonth = Integer.parseInt(parse[1]);
                            int vyear = Integer.parseInt(parse[2]);

                            calendar = Calendar.getInstance();

                            SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                            String  currentDate = currentDateFormat.format(calendar.getTime());

                            if(parse[0].length() == 1)parse[0] ="0"+parse[0];
                            if(parse[1].length() == 1)parse[1] = "0"+parse[1];

                            String vDate = parse[2]+parse[1]+parse[0];

                            Date dateFrom , dateTo;


                            try {
                                dateFrom = currentDateFormat.parse(currentDate);
                                dateTo = currentDateFormat.parse(vDate);
                                if(dateFrom.compareTo(dateTo) >= 0)
                                    list1.add(new homeUserClass(patientName , visitDate , visitId , phoneNumber));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

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

        }

        else if(userType.equals("1")){ ///  This condition is entered for the patient .
            homePageButton.setText("Search Doctor");


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
                        String doctorName, visitDate, visitId, phoneNumber, chamber, doctorType;

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            visitId  = dataSnapshot.child("visitId").getValue(String.class);
                            doctorName = dataSnapshot.child("doctorName").getValue(String.class);
                            visitDate = dataSnapshot.child("visitDate").getValue(String.class);

                            phoneNumber = dataSnapshot.child("doctorPhoneNumber").getValue(String.class);
                            chamber = dataSnapshot.child("chamber").getValue(String.class);
                            doctorType = dataSnapshot.child("doctorType").getValue(String.class);




                            Calendar calendar = Calendar.getInstance();




                            String[] parse = visitDate.split(" - ");

                            int vdate = Integer.parseInt(parse[0]);
                            int vmonth = Integer.parseInt(parse[1]);
                            int vyear = Integer.parseInt(parse[2]);

                            calendar = Calendar.getInstance();

                            SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                            String  currentDate = currentDateFormat.format(calendar.getTime());

                            if(parse[0].length() == 1)parse[0] ="0"+parse[0];
                            if(parse[1].length() == 1)parse[1] = "0"+parse[1];

                            String vDate = parse[2]+parse[1]+parse[0];

                           Date dateFrom , dateTo;


                            try {
                                dateFrom = currentDateFormat.parse(currentDate);
                                dateTo = currentDateFormat.parse(vDate);
                                if(dateFrom.compareTo(dateTo) >= 0)
                                    list1.add(new homeUserClass(doctorName,visitDate,visitId,phoneNumber,doctorType,chamber));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


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
        }


        homePageButton.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        list1.clear();
        list2.clear();

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