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

public class PatientProfileUpdateActivity extends AppCompatActivity {

    private View view;
    private Button updateButton;
    private TextInputEditText emailTextInputEditText, firstNameTextInputEditText, lastNameTextInputEditText, phoneTextInputEditText, genderTextInputEditText,  passwordTextInputEditText;
    private String email, firstName, lastName, phone, gender, district, password, userId;
    private AutoCompleteTextView districtAutoCompleteTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_update);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        phone = intent.getStringExtra("phoneNumber");
        gender = intent.getStringExtra("gender");
        district = intent.getStringExtra("district");
        password = intent.getStringExtra("password");

        emailTextInputEditText = findViewById(R.id.emailTxt);
        firstNameTextInputEditText = findViewById(R.id.firstNameTxt);
        lastNameTextInputEditText = findViewById(R.id.lastNameTxt);
        phoneTextInputEditText = findViewById(R.id.phoneTxt);
        genderTextInputEditText = findViewById(R.id.genderTxt);
        districtAutoCompleteTextView  = findViewById(R.id.districtTxt);
        passwordTextInputEditText = findViewById(R.id.passwordTxt);
        updateButton = findViewById(R.id.updateButton);

        String[] patientDistrict = getResources().getStringArray(R.array.District);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_item,patientDistrict);
        districtAutoCompleteTextView.setAdapter(arrayAdapter);

        emailTextInputEditText.setText(email);
        firstNameTextInputEditText.setText(firstName);
        lastNameTextInputEditText.setText(lastName);
        phoneTextInputEditText.setText(phone);
        genderTextInputEditText.setText(gender);
        districtAutoCompleteTextView.setText(district);
        passwordTextInputEditText.setText(password);

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
                if(isFirstNameChanged() || isLastNameChanged() || isPasswordChanged() || isPhoneNoChanged() || isGenderChanged() || isDistrictChanged()){
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

    private boolean isDistrictChanged() {
        if(!district.equals(districtAutoCompleteTextView.getText().toString())){
            databaseReference.child("district").setValue(districtAutoCompleteTextView.getText().toString());
            district = districtAutoCompleteTextView.getText().toString();
            return true;
        }else {
            return false;
        }
    }

}