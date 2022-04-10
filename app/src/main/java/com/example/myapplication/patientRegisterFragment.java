
package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class patientRegisterFragment extends Fragment {

    private View view;
    private AutoCompleteTextView districtName;
    private EditText firstName, lastName, regEmail, regPassword, regConfirmPassword, regMobileNumber;
    private RadioButton genderBtn, maleBtn, femaleBtn;
    private Button signUpBtn;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_patient_register, container, false);
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        regEmail = view.findViewById(R.id.regEmail);
        regPassword = view.findViewById(R.id.regPassword);
        regConfirmPassword = view.findViewById(R.id.regConfirmPassword);
        regMobileNumber = view.findViewById(R.id.contactNo);
        districtName = view.findViewById(R.id.regLocation);
        genderBtn = view.findViewById(R.id.male_female);
        maleBtn = view.findViewById(R.id.male);
        femaleBtn = view.findViewById(R.id.female);
        signUpBtn = view.findViewById(R.id.btnSignUpPatient);

        String[] patientDistrict = getResources().getStringArray(R.array.District);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.dropdown_item,patientDistrict);
        districtName.setAdapter(arrayAdapter);

        firebaseAuth = FirebaseAuth.getInstance();

//        if(firebaseAuth.getCurrentUser() != null){
//
//        }

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = firstName.getText().toString().trim();
                String lastname = lastName.getText().toString();
                String email = regEmail.getText().toString();
                String password = regPassword.getText().toString();
                String confirmPassword = regConfirmPassword.getText().toString();
                String mobileNumber = regMobileNumber.getText().toString();
                String district = districtName.getText().toString();
                String male = maleBtn.getText().toString();
                String female = femaleBtn.getText().toString();

                if(firstname.isEmpty()){
                    firstName.setError("First Name is Required");
                    Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(lastname.isEmpty()){
                    lastName.setError("Last Name is Required");
                    Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(email.isEmpty()){
                    regEmail.setError("Email is Required");
                    Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()){
                    regPassword.setError("Password is Required");
                    Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(confirmPassword.isEmpty()){
                    regEmail.setError("Password confirmation is Required");
                    Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(confirmPassword)){
                    regConfirmPassword.setError("Password doesn't match");
                    Toast.makeText(getContext(), "Please fill up the fields correctly", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mobileNumber.isEmpty()){
                    regMobileNumber.setError("Phone number is Required");
                    Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(district.isEmpty()){
                    districtName.setError("District is Required");
                    Toast.makeText(getContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!maleBtn.isChecked() && !femaleBtn.isChecked()){
                    Toast.makeText(getContext(), "Please Select your gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                String gender = "";
                if(maleBtn.isChecked()){
                    gender = "male";
                }
                else{
                    gender = "female";
                }
                String finalGender = gender;

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"new user created successfully! Plz verify your email!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(), loginFragment.class);
                            i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(getActivity(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })


            }
        });



        return view;
    }
}