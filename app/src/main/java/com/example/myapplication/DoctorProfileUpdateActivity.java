package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class DoctorProfileUpdateActivity extends AppCompatActivity {

    private View view;
    private Button updateButton;
    private TextInputEditText emailTextInputEditText, firstNameTextInputEditText, lastNameTextInputEditText, phoneTextInputEditText, genderTextInputEditText,  passwordTextInputEditText;
    private TextInputEditText instituteTextInputEdittext, chamberTextInputEditText, eduQualificationTextInputEditText;
    private String email, firstName, lastName, phone, gender, region, password, userId, institute, chamber, eduQualification, speciality;
    private AutoCompleteTextView regionAutoCompleteTextView, specialityAutoCompleteTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_update);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        phone = intent.getStringExtra("phoneNumber");
        gender = intent.getStringExtra("gender");
        region = intent.getStringExtra("region");
        password = intent.getStringExtra("password");
        institute = intent.getStringExtra("institute");
        chamber = intent.getStringExtra("chamber");
        eduQualification = intent.getStringExtra("educationalQualification");
        speciality = intent.getStringExtra("speciality");

        emailTextInputEditText = findViewById(R.id.emailTxt);
        firstNameTextInputEditText = findViewById(R.id.firstNameTxt);
        lastNameTextInputEditText = findViewById(R.id.lastNameTxt);
        phoneTextInputEditText = findViewById(R.id.phoneTxt);
        genderTextInputEditText = findViewById(R.id.genderTxt);
        regionAutoCompleteTextView = findViewById(R.id.regionTxt);
        passwordTextInputEditText = findViewById(R.id.passwordTxt);
        instituteTextInputEdittext =findViewById(R.id.instituteTxt);
        chamberTextInputEditText = findViewById(R.id.chamberTxt);
        eduQualificationTextInputEditText = findViewById(R.id.educationalQualificationTxt);
        specialityAutoCompleteTextView = findViewById(R.id.specialityTxt);

        updateButton = findViewById(R.id.updateButton);

        String[] doctorRegion = getResources().getStringArray(R.array.Dhaka_region);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_item,doctorRegion);
        regionAutoCompleteTextView.setAdapter(arrayAdapter1);

        String[] doctorSpeciality = getResources().getStringArray(R.array.Doctor_Speciality);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_item,doctorSpeciality);
        specialityAutoCompleteTextView.setAdapter(arrayAdapter2);

        emailTextInputEditText.setText(email);
        firstNameTextInputEditText.setText(firstName);
        lastNameTextInputEditText.setText(lastName);
        phoneTextInputEditText.setText(phone);
        genderTextInputEditText.setText(gender);
        regionAutoCompleteTextView.setText(region);
        passwordTextInputEditText.setText(password);
        instituteTextInputEdittext.setText(institute);
        chamberTextInputEditText.setText(chamber);
        eduQualificationTextInputEditText.setText(eduQualification);
        specialityAutoCompleteTextView.setText(speciality);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        emailTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Email cannot be changed", Toast.LENGTH_SHORT).show();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFirstNameChanged() || isLastNameChanged() || isPasswordChanged() || isPhoneNoChanged() || isGenderChanged() || isRegionChanged() || isChamberChanged() || isInstituteChanged() || isEduQualificationChanged() || isSpecialityChanged()){
                    Toast.makeText(getApplicationContext(), "Data has been Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "data not changed ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean isPhoneNoChanged() {
        if(!phone.equals(phoneTextInputEditText.getText().toString())){
            databaseReference.child("mobileNumber").setValue(phoneTextInputEditText.getText().toString());
            phone = phoneTextInputEditText.getText().toString();
            return true;
        }else {
            return false;
        }
    }
    private boolean isFirstNameChanged(){
        if(!firstName.equals(firstNameTextInputEditText.getText().toString())){
            databaseReference.child("firstName").setValue(firstNameTextInputEditText.getText().toString());
            firstName = firstNameTextInputEditText.getText().toString();
            return true;
        }else {
            return false;
        }

    }
    private boolean isLastNameChanged(){
        if(!lastName.equals(lastNameTextInputEditText.getText().toString())){
            databaseReference.child("lastName").setValue(lastNameTextInputEditText.getText().toString());
            lastName = lastNameTextInputEditText.getText().toString();
            return true;
        }else {
            return false;
        }

    }

    private boolean isPasswordChanged(){
        if(!password.equals(passwordTextInputEditText.getText().toString()) ){

            AuthCredential authCredential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),password);
            firebaseUser.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    firebaseUser.updatePassword(passwordTextInputEditText.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if((!passwordTextInputEditText.getText().toString().isEmpty()) && passwordTextInputEditText.getText().toString().length()>5){
                                databaseReference.child("password").setValue(passwordTextInputEditText.getText().toString());
                                password = passwordTextInputEditText.getText().toString();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(getApplicationContext(), ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return true;


        }else{
            return false;
        }

    }

    private boolean isGenderChanged() {
        if(!gender.equals(genderTextInputEditText.getText().toString())){
            databaseReference.child("gender").setValue(genderTextInputEditText.getText().toString());
            gender = genderTextInputEditText.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isRegionChanged() {
        if(!region.equals(regionAutoCompleteTextView.getText().toString())){
            databaseReference.child("region").setValue(regionAutoCompleteTextView.getText().toString());
            region = regionAutoCompleteTextView.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isInstituteChanged() {
        if(!institute.equals(instituteTextInputEdittext.getText().toString())){
            databaseReference.child("institute").setValue(instituteTextInputEdittext.getText().toString());
            institute = instituteTextInputEdittext.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isChamberChanged() {
        if(!chamber.equals(chamberTextInputEditText.getText().toString())){
            databaseReference.child("chamber").setValue(chamberTextInputEditText.getText().toString());
            chamber = chamberTextInputEditText.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isEduQualificationChanged() {
        if(!eduQualification.equals(eduQualificationTextInputEditText.getText().toString())){
            databaseReference.child("educationalQualification").setValue(eduQualificationTextInputEditText.getText().toString());
            eduQualification = eduQualificationTextInputEditText.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isSpecialityChanged() {
        if(!region.equals(regionAutoCompleteTextView.getText().toString())){
            databaseReference.child("region").setValue(regionAutoCompleteTextView.getText().toString());
            region = regionAutoCompleteTextView.getText().toString();
            return true;
        }else {
            return false;
        }
    }

}