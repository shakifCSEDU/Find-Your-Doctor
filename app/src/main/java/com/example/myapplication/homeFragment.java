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

import java.util.ArrayList;

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
    private ListView listView;

    private Context context;
    Boolean isDoctor  = false;

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

        }

        else if(userType.equals("1")){ ///  This condition is entered for the patient .
            homePageButton.setText("Search Doctor");


            firebaseAuth = FirebaseAuth.getInstance();
            userID = firebaseAuth.getCurrentUser().getUid();

            db = FirebaseDatabase.getInstance();
            root = db.getReference("Users").child(userID).child("Appointments");
        }


        homePageButton.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<homeUserClass>options =
                new FirebaseRecyclerOptions.Builder<homeUserClass>()
                        .setQuery(root,homeUserClass.class).build();


        if(userType.equals("1")){

            FirebaseRecyclerAdapter<homeUserClass,myViewHolder>adapter =
                    new FirebaseRecyclerAdapter<homeUserClass, myViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull homeUserClass model) {

                            String visitId = getRef(position).getKey();

                            root.child(visitId).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.exists()){

                                    //String doctorName, visitDate, visitId, phoneNo, doctorType, chamber;
                                    //String doctorUid;

                                    final String doctorName = snapshot.child("doctorName").getValue().toString();
                                    final String visitDate = snapshot.child("visitDate").getValue().toString();
                                    final String visitId = snapshot.child("visitId").getValue().toString();
                                    final String phoneNo = snapshot.child("doctorPhoneNumber").getValue().toString();
                                    final String doctorType = snapshot.child("doctorType").getValue().toString();
                                    final String chamber = snapshot.child("chamber").getValue().toString();
                                    final String doctorUid = snapshot.child("doctorUid").getValue().toString();


                                    holder.nameTextView.setText(doctorName);
                                    holder.visitDateTextView.setText(visitDate);
                                    holder.visitIdTextView.setText(visitId);
                                    holder.phoneNoTextView.setText(phoneNo);
                                    holder.doctorTypeTextView.setText(doctorType);
                                    holder.chamberTextView.setText(chamber);
                                    Picasso.get().load(R.drawable.profile_picture).into(holder.circleImageView);

                                    holder.removeButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            db.getReference("Users").child(doctorUid).child("Appointments")
                                                    .child(visitId).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            if (snapshot.exists()) {
                                                                db.getReference("Users").child(doctorUid).child("Appointments")
                                                                        .child(visitId).child("patientCancelState").setValue("yes")
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                Toast.makeText(context, "PatientCancel State yes", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                            db.getReference("Users").child(userID).child("Appointments")
                                                    .child(visitId).removeValue();
                                    /// Work for remove slot.........

                                        }
                                    });
                                }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }

                        @NonNull
                        @Override
                        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_custom_adapter_patient_layout,parent,false);
                            myViewHolder holder = new myViewHolder(view,"patient");
                            return holder;
                        }
                    };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }
        else{
            FirebaseRecyclerAdapter<homeUserClass,myViewHolder>adapter =
                    new FirebaseRecyclerAdapter<homeUserClass, myViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull homeUserClass model) {

                            String visitId = getRef(position).getKey();

                            root.child(visitId).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.exists()){

                                        //String patientName, visitDate, visitId, phoneNumber, patientCancelState, patientConfirmState, patientUid;

                                    final String patientUid = snapshot.child("patientUid").getValue(String.class);
                                    final String patientCancelState = snapshot.child("patientCancelState").getValue(String.class);
                                    final String patientConfirmState = snapshot.child("patientConfirmState").getValue(String.class);
                                    final String visitId = snapshot.child("visitId").getValue(String.class);

                                    final String  patientName = snapshot.child("patientName").getValue(String.class);
                                    final String visitDate = snapshot.child("visitDate").getValue(String.class);
                                    final String  phoneNumber = snapshot.child("patientPhoneNumber").getValue(String.class);

                                    holder.nameTextView.setText(patientName);
                                    holder.visitIdTextView.setText(visitId);
                                    holder.visitDateTextView.setText(visitDate);
                                    holder.phoneNoTextView.setText(phoneNumber);
                                    Picasso.get().load(R.drawable.profile_picture).into(holder.circleImageView);


                                    holder.removeButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            db.getReference("Users").child(patientUid).child("Appointments")
                                                    .child(visitId).addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                            if (snapshot.exists()) {
                                                                db.getReference("Users").child(patientUid).child("Appointments")
                                                                        .child(visitId).child("doctorCancelState").setValue("yes")
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                Toast.makeText(context, "DoctorCancel State yes", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });
                                            db.getReference("Users").child(userID).child("Appointments")
                                                    .child(visitId).removeValue();

                                            /// Work for remove slot.........


                                        }
                                    });

                                }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }

                        @NonNull
                        @Override
                        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_custom_adapter_doctor_layout,parent,false);
                            myViewHolder holder = new myViewHolder(view,"doctor");
                            return holder;
                        }
                    };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }






    }

    @Override
    public void onDestroyView() {
        //list1.clear();
        //list2.clear();

        super.onDestroyView();
    }





    public static class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView nameTextView,visitDateTextView,visitIdTextView,phoneNoTextView,doctorTypeTextView,chamberTextView;
        Button confirmButton,removeButton;
        CardView cardView;

        public myViewHolder(@NonNull View view,String person) {
            super(view);
            if(person.equals("patient")){
                circleImageView = (CircleImageView)view.findViewById(R.id.circleImageViewId);
                nameTextView = (TextView)view.findViewById(R.id.userNameTextViewId);
                visitDateTextView = (TextView)view.findViewById(R.id.visitDateTextViewId);
                visitIdTextView = (TextView)view.findViewById(R.id.visitIdTextViiewId);
                phoneNoTextView = (TextView)view.findViewById(R.id.phoneNoTextViewId);
                doctorTypeTextView = (TextView)view.findViewById(R.id.doctorTypeTextViewId);
                chamberTextView = (TextView)view.findViewById(R.id.chamberTextViewId);

                confirmButton = (Button)view.findViewById(R.id.confirmButtonId);
                removeButton  = (Button)view.findViewById(R.id.removeButtonId);
                cardView = (CardView)view.findViewById(R.id.cardViewId);
            }else{
                circleImageView = view.findViewById(R.id.circleImageViewId);
                nameTextView = view.findViewById(R.id.userNameTextViewId);
                visitDateTextView = view.findViewById(R.id.visitDateTextViewId);
                visitIdTextView = view.findViewById(R.id.visitIdTextViiewId);
                phoneNoTextView = view.findViewById(R.id.phoneNoTextViewId);
                confirmButton = view.findViewById(R.id.confirmButtonId);
                removeButton = view.findViewById(R.id.removeButtonId);
                cardView = view.findViewById(R.id.cardViewId);
            }



        }
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