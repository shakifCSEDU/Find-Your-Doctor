package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AppointmentFinishActivity extends AppCompatActivity {

    private TextView doctorNameText, chamberTextView, visitDateTextView, appointmentIDTextView;
    private Button homeButton;
    private String doctorUid, patientUid, visitDate,slots, issueDate, issueTime;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase patientFirebaseDatabase, doctorFirebaseDatabase;
    private DatabaseReference patientDatabaseReference, doctorDatabaseReference, databaseReferenceKeyGenerator;
    private FirebaseUser patientFirebaseUser, doctorFirebaseUser;
    private String doctorName, doctorPhoneNumber, doctorType, chamber, patientName, patientPhoneNumber;

    Calendar calendar;
    Map<String,String> appointmentDetailsMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_finish);

        Intent intent = getIntent();
        doctorUid = intent.getStringExtra("doctorUid");
        patientUid = intent.getStringExtra("patientUid");
        visitDate = intent.getStringExtra("visitDate");
        slots = intent.getStringExtra("slots");

        Toast.makeText(AppointmentFinishActivity.this,"Doctor UID "+ doctorUid,Toast.LENGTH_LONG).show();

        calendar = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd - M - yyyy");
        issueDate = currentDateFormat.format(calendar.getTime());
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("HH:mm");
        issueTime = currentTimeFormat.format(calendar.getTime());

        doctorNameText = findViewById(R.id.doctorName);
        chamberTextView = findViewById(R.id.chamberId);
        visitDateTextView  =findViewById(R.id.visitDateId);
        appointmentIDTextView = findViewById(R.id.appointmentID);

        databaseReferenceKeyGenerator  = FirebaseDatabase.getInstance().getReference("Users");
        String key = databaseReferenceKeyGenerator.push().getKey();

        doctorDatabaseReference  = FirebaseDatabase.getInstance().getReference("Users").child(doctorUid);
        doctorDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    DoctorUser doctorUser = snapshot.getValue(DoctorUser.class);
                    doctorNameText.setText(doctorUser.getFirstName() + " "+ doctorUser.getLastName());
                    chamberTextView.setText(doctorUser.getChamber());
                    doctorName = doctorUser.getFirstName()+ " "+doctorUser.getLastName();
                    doctorPhoneNumber = doctorUser.getMobileNumber();
                    doctorType = doctorUser.getSpeciality();
                    chamber = doctorUser.getChamber();
                    appointmentDetailsMap.put("doctorName",doctorName);
                    appointmentDetailsMap.put("doctorPhoneNumber",doctorPhoneNumber);
                    appointmentDetailsMap.put("doctorType",doctorType);
                    appointmentDetailsMap.put("chamber",chamber);
                    patientDatabaseReference  = FirebaseDatabase.getInstance().getReference("Users").child(patientUid);
                    patientDatabaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            patientUser patient_user = snapshot.getValue(patientUser.class);
                            patientName = patient_user.getFirstName()+" "+patient_user.getLastName();
                            patientPhoneNumber = patient_user.getMobileNumber();
                            appointmentDetailsMap.put("patientName",patientName);
                            appointmentDetailsMap.put("patientPhoneNumber",patientPhoneNumber);

                            appointmentDetailsMap.put("doctorUid",doctorUid);
                            appointmentDetailsMap.put("patientUid",patientUid);
                            appointmentDetailsMap.put("visitDate",visitDate);
                            appointmentDetailsMap.put("slots",slots);
                            appointmentDetailsMap.put("doctorCancelState","No");
                            appointmentDetailsMap.put("patientCancelState","No");
                            appointmentDetailsMap.put("doctorConfirmSate","No");
                            appointmentDetailsMap.put("patientConfirmState","No");
                            appointmentDetailsMap.put("patientReview","No");
                            appointmentDetailsMap.put("issueDate",issueDate);
                            appointmentDetailsMap.put("issueTime",issueTime);

                            patientDatabaseReference.child("Appointments").child(key).setValue(appointmentDetailsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //Toast.makeText(AppointmentFinishActivity.this, "Patient Database is created", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Toast.makeText(AppointmentFinishActivity.this, "Patient Database is not created", Toast.LENGTH_SHORT).show();
                                }
                            });


                            doctorDatabaseReference.child("Appointments").child(key).setValue(appointmentDetailsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //Toast.makeText(AppointmentFinishActivity.this, "Doctor Database is created", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Toast.makeText(AppointmentFinishActivity.this, "Doctor Database is not created", Toast.LENGTH_SHORT).show();
                                }
                            });



                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        visitDateTextView.setText(visitDate);
        appointmentIDTextView.setText(key);

        homeButton = findViewById(R.id.homeButtonId);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("userType","1");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
}