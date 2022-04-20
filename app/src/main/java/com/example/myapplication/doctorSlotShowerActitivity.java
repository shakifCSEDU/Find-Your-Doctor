package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class doctorSlotShowerActitivity extends AppCompatActivity {
    private String dateString;
    private GridView gridView;
    private TextView dateTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String userID;
    Map<String, String> seatSlot = new HashMap<>();
    Map<String, String> appointmentSlot = new HashMap<>();
    private View view;
    int[] isSelectSlot = new int[25];
    int count;
    private Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_slot_shower_actitivity);
        this.setTitle("Doctor Slot");

        Intent intent =  getIntent();
        dateString = intent.getStringExtra("date");

        gridView = (GridView)findViewById(R.id.manageSlotGridViewId);
        dateTextView = (TextView)findViewById(R.id.dateTextViewId);
        confirmButton = (Button)findViewById(R.id.confirmButton);
        dateTextView.setText(dateString);

        ArrayList<CustomGrid>customGrids = new ArrayList<CustomGrid>();
        DoctorDescriptionAdapter descriptionAdapter = new DoctorDescriptionAdapter(getApplicationContext(), customGrids);
        gridView.setAdapter(descriptionAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(userID).child("Schedule").child(dateString);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int index;
                    for(index = 1; index <= 15; index++){
                        String a = snapshot.child("S"+index).getValue(String.class);
                        String index1 = Integer.toString(index);
                        if(a.equals("1")){
                            customGrids.add(new CustomGrid(R.drawable.slot_background_red,"S"+index1));
                            Toast.makeText(getApplicationContext(),"S"+index1+"is booked",Toast.LENGTH_SHORT).show();
                            seatSlot.put("S"+index,"100");
                        }
                        else if(a.equals("2")){
                            customGrids.add(new CustomGrid(R.drawable.slot_background_green,"S"+index1));
                            seatSlot.put("S"+index,"200");
                        }
                        else{
                            customGrids.add(new CustomGrid(R.drawable.slot_background_white,"S"+index1));
                            seatSlot.put("S"+index, a);
                        }
                    }
                    descriptionAdapter.notifyDataSetChanged();
                }

                else {
                    int index;
                    for (index = 1; index <= 15; index++) {
                        customGrids.add(new CustomGrid(R.drawable.slot_background_white, "S" + index));
                        seatSlot.put("S" + index, "0");
                    }
                    descriptionAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String slot = "S" + Integer.toString(position + 1);
                if(seatSlot.get(slot).equals("100")){
                    Toast.makeText(doctorSlotShowerActitivity.this, "This slot is already booked! Please choose another one!!", Toast.LENGTH_SHORT).show();
                }

                else if(seatSlot.get(slot).equals("200")){
                    view.setBackgroundColor(Color.parseColor("#00FF00"));
                    Toast.makeText(getApplicationContext(), "You mark Slot Number :" + (position + 1)+ "available again", Toast.LENGTH_SHORT).show();
                    isSelectSlot[position] = 4;
                    seatSlot.put(slot,"500");
                }

                else if(seatSlot.get(slot).equals("500")){
                    view.setBackgroundColor(Color.parseColor("#DDDDDD"));
                    Toast.makeText(getApplicationContext(), "You Undo Slot Number :" + (position + 1)+ "'s availability", Toast.LENGTH_SHORT).show();
                    isSelectSlot[position] = 3;
                    seatSlot.put(slot,"200");
                }

                else if(seatSlot.get(slot).equals("0")){
                    view.setBackgroundColor(Color.parseColor("#FF4646"));
                    Toast.makeText(getApplicationContext(), "You Selected Slot Number :" + (position + 1), Toast.LENGTH_SHORT).show();
                    seatSlot.put(slot,"2");
                }

                else if(seatSlot.get(slot).equals("2")){
                        view.setBackgroundColor(Color.parseColor("#DDDDDD"));
                        Toast.makeText(getApplicationContext(), "You Unselected Seat Number :" + (position + 1), Toast.LENGTH_SHORT).show();
                        seatSlot.put(slot,"0");
                }
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            int index;
                            for (index = 1; index <= 15; index++) {
                                String seatIndex = "S" + index;
                                if (seatSlot.get(seatIndex).equals("2")) {
                                    databaseReference.child(seatIndex).setValue("2");
                                }
                                else if (seatSlot.get(seatIndex).equals("500")){
                                    databaseReference.child(seatIndex).setValue("0");
                                }
                            }
                        }else {
                            databaseReference.setValue(seatSlot).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(getApplicationContext(),"Database updated", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




                intent1.putExtra("userType","2");
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        });




    }
}