package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class doctorRegistrationActivity extends AppCompatActivity {

    private View view;
    private AutoCompleteTextView doctorRegionAutoCompleteTextView, doctorSpecialityAutoCompleteTextView;
    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, confirmPasswordEditText, mobileNumberEditText, instituteEditText, chamberAddressEditText, educationalQualificationEditText;
    private RadioButton maleBtn, femaleBtn;
    private Button signUpBtn;
    private FirebaseAuth firebaseAuth;
    private RadioGroup genderBtn;
    private String userID;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);

        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText = findViewById(R.id.lastName);
        emailEditText  = findViewById(R.id.regEmail);
        passwordEditText = findViewById(R.id.regPassword);
        confirmPasswordEditText = findViewById(R.id.regConfirmPassword);
        mobileNumberEditText = findViewById(R.id.contactNo);
        instituteEditText = findViewById(R.id.institute);
        chamberAddressEditText = findViewById(R.id.detailsAddress);
        educationalQualificationEditText = findViewById(R.id.educationalQualification);

        doctorRegionAutoCompleteTextView = findViewById(R.id.regLocation);
        doctorSpecialityAutoCompleteTextView = findViewById(R.id.regSpeciality);

        genderBtn =  findViewById(R.id.male_female);
        maleBtn = findViewById(R.id.male);
        femaleBtn =findViewById(R.id.female);
        signUpBtn = findViewById(R.id.btnSignUpDoctor);

        firebaseAuth = FirebaseAuth.getInstance();

        String[] doctorRegion = getResources().getStringArray(R.array.Dhaka_region);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_item,doctorRegion);
        doctorRegionAutoCompleteTextView.setAdapter(arrayAdapter1);

        String[] doctorSpeciality = getResources().getStringArray(R.array.Doctor_Speciality);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_item,doctorSpeciality);
        doctorSpecialityAutoCompleteTextView.setAdapter(arrayAdapter2);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                String mobileNumber = mobileNumberEditText.getText().toString().trim();
                String region = doctorRegionAutoCompleteTextView.getText().toString().trim();
                String institute = instituteEditText.getText().toString().trim();
                String chamber = chamberAddressEditText.getText().toString().trim();
                String educationalQualification = educationalQualificationEditText.getText().toString().trim();
                String speciality = doctorSpecialityAutoCompleteTextView.getText().toString().trim();

                if(firstName.isEmpty()){
                    firstNameEditText.setError("First Name is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(lastName.isEmpty()){
                    lastNameEditText.setError("Last Name is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(email.isEmpty()){
                    emailEditText.setError("Email is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()){
                    passwordEditText.setError("Password is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(confirmPassword.isEmpty()){
                    confirmPasswordEditText.setError("Password confirmation is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(confirmPassword)){
                    confirmPasswordEditText.setError("Password doesn't match");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields correctly", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mobileNumber.isEmpty()){
                    mobileNumberEditText.setError("Phone number is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(region.isEmpty()){
                    doctorRegionAutoCompleteTextView.setError("Region is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(institute.isEmpty()){
                    instituteEditText.setError("Institute is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(chamber.isEmpty()){
                    chamberAddressEditText.setError("Chamber Address is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(educationalQualification.isEmpty()){
                    educationalQualificationEditText.setError("Region is Required");
                    Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(speciality.isEmpty()){
                    doctorSpecialityAutoCompleteTextView.setError("Region is Required");
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

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    //Toast.makeText(patientRegistrationAcitivity.this, "Verification link sent to your email\nPlz Verify to log-in", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent"+e.getMessage());
                                }
                            });

                            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            DoctorUser doctorUser = new DoctorUser(firstName,lastName, email, password, mobileNumber, region, institute, chamber,educationalQualification,speciality,finalGender,"2",userID);

                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            String key = databaseReference.push().getKey();

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(userID)
                                    .setValue(doctorUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        FirebaseDatabase.getInstance().getReference("DoctorType").child(speciality).child(region).child(key).child("uid").setValue(userID).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(doctorRegistrationActivity.this, "User created successfully!\n Plz verify email to log-in!!", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(getApplicationContext(), loginActivity.class);
                                                    i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(i);
                                                    finish();
                                                }
                                                else{
                                                    Toast.makeText(doctorRegistrationActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });

                                    }
                                    else {
                                        Toast.makeText(doctorRegistrationActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }else{
                            Toast.makeText(doctorRegistrationActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}