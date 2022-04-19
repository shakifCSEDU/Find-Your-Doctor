package com.example.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class patientRegistrationAcitivity extends AppCompatActivity {
    private AutoCompleteTextView districtNameAutoCompleteTextView;
    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, confirmPasswordEditText, mobileNumberEditText;
    private RadioButton  maleBtn, femaleBtn;
    private Button signUpBtn;
    private FirebaseAuth firebaseAuth;
    private RadioGroup genderBtn;
    private FirebaseDatabase firebaseDatabase;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);
        this.setTitle("Patient Registration");

        firstNameEditText = findViewById(R.id.firstName);
        lastNameEditText =findViewById(R.id.lastName);
        emailEditText = findViewById(R.id.regEmail);
        passwordEditText =findViewById(R.id.regPassword);
        confirmPasswordEditText = findViewById(R.id.regConfirmPassword);
        mobileNumberEditText = findViewById(R.id.contactNo);
        districtNameAutoCompleteTextView = findViewById(R.id.regLocation);
        genderBtn =  findViewById(R.id.male_female);
        maleBtn = findViewById(R.id.male);
        femaleBtn =findViewById(R.id.female);
        signUpBtn = findViewById(R.id.btnSignUpPatient);

        String[] patientDistrict = getResources().getStringArray(R.array.District);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_item,patientDistrict);
        districtNameAutoCompleteTextView.setAdapter(arrayAdapter);

        firebaseAuth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String mobileNumber = mobileNumberEditText.getText().toString();
                String district = districtNameAutoCompleteTextView.getText().toString();
                String male = maleBtn.getText().toString();
                String female = femaleBtn.getText().toString();

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
                if(district.isEmpty()){
                    districtNameAutoCompleteTextView.setError("District is Required");
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

                                patientUser patientUser = new patientUser(firstName,lastName, email, password, mobileNumber,district,finalGender,"1");

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(patientUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(patientRegistrationAcitivity.this, "User created successfully!\n Plz verify email to log-in!!", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), loginActivity.class);
                                            i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                                            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(i);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(patientRegistrationAcitivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                            }
                            else{
                                Toast.makeText(patientRegistrationAcitivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                });


            }
        });

    }
}