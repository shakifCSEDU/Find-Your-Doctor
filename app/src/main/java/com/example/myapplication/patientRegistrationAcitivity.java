package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class patientRegistrationAcitivity extends AppCompatActivity {
    private AutoCompleteTextView districtName;
    private EditText firstName, lastName, regEmail, regPassword, regConfirmPassword, regMobileNumber;
    private RadioButton genderBtn, maleBtn, femaleBtn;
    private Button signUpBtn;
    private FirebaseAuth firebaseAuth;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);


        firstName = findViewById(R.id.firstName);
        lastName =findViewById(R.id.lastName);
        regEmail = findViewById(R.id.regEmail);
        regPassword =findViewById(R.id.regPassword);
        regConfirmPassword = findViewById(R.id.regConfirmPassword);
        regMobileNumber = findViewById(R.id.contactNo);
        districtName = findViewById(R.id.regLocation);
        genderBtn =  findViewById(R.id.male_female);
        maleBtn = findViewById(R.id.male);
        femaleBtn =findViewById(R.id.female);
        signUpBtn = findViewById(R.id.btnSignUpPatient);

        String[] patientDistrict = getResources().getStringArray(R.array.District);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_item,patientDistrict);
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
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(lastname.isEmpty()){
                    lastName.setError("Last Name is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(email.isEmpty()){
                    regEmail.setError("Email is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()){
                    regPassword.setError("Password is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(confirmPassword.isEmpty()){
                    regEmail.setError("Password confirmation is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(confirmPassword)){
                    regConfirmPassword.setError("Password doesn't match");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields correctly", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mobileNumber.isEmpty()){
                    regMobileNumber.setError("Phone number is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(district.isEmpty()){
                    districtName.setError("District is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!maleBtn.isChecked() && !femaleBtn.isChecked()){
                    Toast.makeText(getApplicationContext(), "Please Select your gender", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(),"new user created successfully! Plz verify your email!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(),loginActivity.class);
                            i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });









    }
}