package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.Map;

public class CanPayActivity extends AppCompatActivity implements View.OnClickListener {


    private Button button;
    private CardView bKashCardView,rocketCardView,mCashCardView,nagadCardView;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String doctorUid, patientUid, slots, visitDate;
    private String isSelected = null;
    private LayoutInflater layoutInflater;
    private  EditText userNumber;
    private View view;
    private  String payContactNo ;
    private String payContactNumber = "";
    Map<String,String> slotMap;




    private TextView doctorNameTextView,patientNameTextView,doctorPhoneNumberTextView,patientPhoneNumberTextView,visitDateTextView,visitTimeTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_can_pay);
        this.setTitle("Payment Method");

        initview();

        getIntentData();


        slots = " ";
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        patientUid = firebaseUser.getUid();


        databaseReference  = FirebaseDatabase.getInstance().getReference("Users").child(doctorUid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    DoctorUser doctorUser = snapshot.getValue(DoctorUser.class);
                    doctorNameTextView.setText(doctorUser.getFirstName() + " "+ doctorUser.getLastName());
                    doctorPhoneNumberTextView.setText(doctorUser.getMobileNumber());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference  = FirebaseDatabase.getInstance().getReference("Users").child(patientUid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    patientUser patient_User = snapshot.getValue(patientUser.class);
                    patientNameTextView.setText(patient_User.getFirstName() + " "+ patient_User.getLastName());
                    patientPhoneNumberTextView.setText(patient_User.getMobileNumber());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        visitDateTextView.setText(visitDate);




        bKashCardView.setOnClickListener(this::onClick);
        nagadCardView.setOnClickListener(this::onClick);
        rocketCardView.setOnClickListener(this::onClick);
        mCashCardView.setOnClickListener(this::onClick);
        button.setOnClickListener(this::onClick);
        layoutInflater = LayoutInflater.from(getApplicationContext());



    }

    private void getIntentData() {
        Intent intent = getIntent();
        doctorUid = intent.getStringExtra("doctorUid");
        slotMap = (Map<String, String>)intent.getSerializableExtra("slotMap");
        visitDate = intent.getStringExtra("visitDate");

    }

    private void initview() {

        button = (Button)findViewById(R.id.payButtonId);
        bKashCardView = (CardView)findViewById(R.id.bkashCardViewId);
        mCashCardView = (CardView)findViewById(R.id.mCashCardViewId);
        rocketCardView = (CardView)findViewById(R.id.rocketCardViewId);
        nagadCardView = (CardView)findViewById(R.id.nagadCardViewId);
        doctorNameTextView = findViewById(R.id.doctorNameId);
        patientNameTextView = findViewById(R.id.patientNameId);
        doctorPhoneNumberTextView = findViewById(R.id.doctorPhoneNumberId);
        patientPhoneNumberTextView = findViewById(R.id.patientPhoneNumberId);
        visitDateTextView = findViewById(R.id.visitDateId);
        visitTimeTextView = findViewById(R.id.visitTimeId);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bkashCardViewId){
            setCardView("Bkash",bKashCardView,R.drawable.bkash);
        }
        if(v.getId() == R.id.nagadCardViewId){
            setCardView("Nagad",nagadCardView,R.drawable.nagad);
        }
        if(v.getId() == R.id.mCashCardViewId){
            setCardView("Mcash",mCashCardView,R.drawable.mcash);
        }
        if(v.getId() == R.id.rocketCardViewId){
            setCardView("Rocket",rocketCardView,R.drawable.rocket);
        }


        if(v.getId() == R.id.payButtonId){
            if(!payContactNumber.isEmpty()) {
                String message = "Your booking is  confirmed";
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();




                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Users").child(doctorUid).child("Schedule").child(visitDate);


                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            int index;
                            for (index = 1; index <= 15; index++) {
                                String seatIndex = "S" + index;
                                if (slotMap.get(seatIndex).equals("1")) {
                                    databaseReference.child(seatIndex).setValue("1");
                                }
                            }
                            Toast.makeText(CanPayActivity.this, "Databsse updated", Toast.LENGTH_SHORT).show();

                        } else {
                            databaseReference.setValue(slotMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    Toast.makeText(CanPayActivity.this, "Database created", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure( Exception e) {
                                    Toast.makeText(CanPayActivity.this, "Databse creation failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(getApplicationContext(),AppointmentFinishActivity.class);
                intent.putExtra("doctorUid",doctorUid);
                intent.putExtra("patientUid",patientUid);
                intent.putExtra("slots",slots);
                intent.putExtra("visitDate",visitDate);
                startActivity(intent);

            }else{
                Toast.makeText(this, "Please give your payment contact number", Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void setCardView(String Name, CardView cardView, int image) {

        if(isSelected == null){
            cardView.setCardBackgroundColor(Color.parseColor("#6495ED"));
            isSelected = Name;
            SetAlertDialogue(Name,image);
        }else if(isSelected.equals(Name)){
            cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            Toast.makeText(getApplicationContext(),"Unselected "+Name+"",Toast.LENGTH_SHORT).show();
            isSelected = null;
        }
    }
    private void SetAlertDialogue(String name,int image){


        view = layoutInflater.inflate(R.layout.payment_set,null,false);

        userNumber = view.findViewById(R.id.phoneNumberId);

        EditText otp = view.findViewById(R.id.otpId);

        payContactNo = userNumber.getText().toString();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CanPayActivity.this);
        alertDialogBuilder.setTitle(name);
        alertDialogBuilder.setIcon(image);





        alertDialogBuilder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        payContactNumber = userNumber.getText().toString();

                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.setView(view);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}