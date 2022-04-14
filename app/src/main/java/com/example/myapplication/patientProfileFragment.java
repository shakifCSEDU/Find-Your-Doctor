package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class patientProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button logOutButton;
    private View view;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private String userID;
    private TextView userNameTextView, emailTextView, phoneTextView, passwordTextView, genderTextView, locationTextView;



    public patientProfileFragment() {
        // Required empty public constructor
    }

    public static patientProfileFragment newInstance(String param1, String param2) {
        patientProfileFragment fragment = new patientProfileFragment();
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

        view = inflater.inflate(R.layout.fragment_patient_profile, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance("https://find-your-doctor-d7ff3-default-rtdb.firebaseio.com/").getReference("Users").child(userID);
        Log.d("test", userID);

        userNameTextView = view.findViewById(R.id.userNameText);
        emailTextView = view.findViewById(R.id.emailTxt);
        phoneTextView = view.findViewById(R.id.phoneTxt);
        passwordTextView = view.findViewById(R.id.passwordTxt);
        genderTextView = view.findViewById(R.id.genderTxt);
        locationTextView = view.findViewById(R.id.locationTxt);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patientUser patient_user = snapshot.getValue(patientUser.class);
                //proSystem.out.println("hellllllllllllllllllllllllllll" + patient_user.getFirstName());
                userNameTextView.setText(patient_user.getFirstName()+ " "+ patient_user.getLastName());
                emailTextView.setText(patient_user.getEmail());
                phoneTextView.setText(patient_user.getMobileNumber());
                genderTextView.setText(patient_user.getGender());
                locationTextView.setText(patient_user.getDistrict());
                Toast.makeText(getContext(), "Something wrong is happened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logOutButton = (Button) view.findViewById(R.id.logoutButton);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                ((MainActivity) getActivity()).startActivity(new Intent(getActivity(),loginActivity.class));
                getActivity().finish();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}