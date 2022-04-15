package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class profileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button logOutButton;
    String userType;
    private View view;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private TextView userNameTextView, emailTextView, phoneTextView, passwordTextView, genderTextView, locationTextView;
    private TextView instituteTextView,chamberTextView,eduQualificationTextView, specialityTextView;
    private Button editProfileButton;
    private String fname, lname, email, phone, password, gender, location;

    public profileFragment() {
        // Required empty public constructor
    }

    public static profileFragment newInstance(String param1, String param2) {
        profileFragment fragment = new profileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        MainActivity activity = (MainActivity) getActivity();
        String myDataFromActivity = activity.getMyData();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        String userId = firebaseUser.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance("https://find-your-doctor-d7ff3-default-rtdb.firebaseio.com/");
        databaseReference = firebaseDatabase.getReference("Users").child(userId);

        if(myDataFromActivity != null){

            if(myDataFromActivity.equals("1")){
                view = inflater.inflate(R.layout.fragment_patient_profile, container, false);

                userNameTextView = view.findViewById(R.id.userNameText);
                emailTextView = view.findViewById(R.id.emailTxt);
                phoneTextView = view.findViewById(R.id.phoneTxt);
                passwordTextView = view.findViewById(R.id.passwordTxt);
                genderTextView = view.findViewById(R.id.genderTxt);
                locationTextView = view.findViewById(R.id.locationTxt);
                editProfileButton = view.findViewById(R.id.editButton);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        patientUser patient_user = snapshot.getValue(patientUser.class);
                        userNameTextView.setText(patient_user.getFirstName()+ " "+ patient_user.getLastName());
                        emailTextView.setText(patient_user.getEmail());
                        phoneTextView.setText(patient_user.getMobileNumber());
                        passwordTextView.setText(patient_user.getPassword());
                        genderTextView.setText(patient_user.getGender());
                        locationTextView.setText(patient_user.getDistrict());
                        fname = patient_user.getFirstName();
                        lname = patient_user.getLastName();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                editProfileButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(),PatientProfileUpdateActivity.class);
                        intent.putExtra("firstName",fname);
                        intent.putExtra("lastName",lname);
                        intent.putExtra("email",emailTextView.getText().toString());
                        intent.putExtra("phoneNumber",phoneTextView.getText().toString());
                        intent.putExtra("password",passwordTextView.getText().toString());
                        intent.putExtra("gender",genderTextView.getText().toString());
                        intent.putExtra("district",locationTextView.getText().toString());

                        startActivity(intent);


                    }
                });



            }
            else if(myDataFromActivity.equals("2")){
                view = inflater.inflate(R.layout.fragment_doctor_profile, container, false);

                userNameTextView = view.findViewById(R.id.userNameText);
                emailTextView = view.findViewById(R.id.emailTxt);
                phoneTextView = view.findViewById(R.id.phoneTxt);
                passwordTextView = view.findViewById(R.id.passwordTxt);
                genderTextView = view.findViewById(R.id.genderTxt);
                locationTextView = view.findViewById(R.id.locationTxt);
                instituteTextView = view.findViewById(R.id.instituteTxt);
                chamberTextView = view.findViewById(R.id.chamberTxt);
                eduQualificationTextView = view.findViewById(R.id.educationalQualificationTxt);
                specialityTextView = view.findViewById(R.id.specialityTxt);

                editProfileButton = view.findViewById(R.id.editButton);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DoctorUser doctor_user = snapshot.getValue(DoctorUser.class);
                        userNameTextView.setText(doctor_user.getFirstName()+ " "+ doctor_user.getLastName());
                        emailTextView.setText(doctor_user.getEmail());
                        phoneTextView.setText(doctor_user.getMobileNumber());
                        passwordTextView.setText(doctor_user.getPassword());
                        genderTextView.setText(doctor_user.getGender());
                        locationTextView.setText(doctor_user.getRegion());
                        instituteTextView.setText(doctor_user.getInstitute());
                        chamberTextView.setText(doctor_user.getChamber());
                        eduQualificationTextView.setText(doctor_user.getEducationalQualification());
                        specialityTextView.setText(doctor_user.getSpeciality());
                        fname = doctor_user.getFirstName();
                        lname = doctor_user.getLastName();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                editProfileButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(),DoctorProfileUpdateActivity.class);
                        intent.putExtra("firstName",fname);
                        intent.putExtra("lastName",lname);
                        intent.putExtra("email",emailTextView.getText().toString());
                        intent.putExtra("phoneNumber",phoneTextView.getText().toString());
                        intent.putExtra("password",passwordTextView.getText().toString());
                        intent.putExtra("gender",genderTextView.getText().toString());
                        intent.putExtra("region",locationTextView.getText().toString());
                        intent.putExtra("institute",instituteTextView.getText().toString());
                        intent.putExtra("chamber",chamberTextView.getText().toString());
                        intent.putExtra("educationalQualification",eduQualificationTextView.getText().toString());
                        intent.putExtra("speciality",specialityTextView.getText().toString());

                        startActivity(intent);


                    }
                });




            }
        }

        logOutButton = (Button) view.findViewById(R.id.logoutButton);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                ((MainActivity) getActivity()).startActivity(new Intent(getActivity(),loginActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }


}