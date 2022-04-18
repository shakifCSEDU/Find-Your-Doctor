package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class homeCustomAdapter extends RecyclerView.Adapter<homeCustomAdapter.myViewHolder> {
    private Context context;
    private ArrayList<homeUserClass>list = null;
    private String person;
    private DatabaseReference databaseReference;
    private String Rootuid;

    public homeCustomAdapter(Context context, ArrayList<homeUserClass> list,String person,String Rootuid) {

        this.context = context;
        this.list = list;
        this.person = person;
        this.Rootuid = Rootuid;

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(Rootuid).child("Appointments");

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(TextUtils.equals(person,"patient")){ // If the profile is patients profile then we show this ui..
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_custom_adapter_patient_layout,parent,false);
            myViewHolder holder = new myViewHolder(view);
            return holder;
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_custom_adapter_doctor_layout,parent,false);
            myViewHolder holder = new myViewHolder(view);
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(final myViewHolder holder,final int position) {
        if(TextUtils.equals(person,"patient")){

            holder.nameTextView.setText(list.get(position).getName());
            holder.visitDateTextView.setText(list.get(position).getVisitDate());
            holder.visitIdTextView.setText(list.get(position).getVisitId());
            holder.phoneNoTextView.setText(list.get(position).getPhoneNo());
            holder.doctorTypeTextView.setText(list.get(position).getDoctorType());
            holder.chamberTextView.setText(list.get(position).getChamber());
            Picasso.get().load(list.get(position).getImageId()).placeholder(R.drawable.profile_picture).into(holder.circleImageView);
            holder.cardView.setCardBackgroundColor(Color.parseColor(list.get(position).getBackgroundColor()));

        }else{
            holder.nameTextView.setText(list.get(position).getName());
            holder.visitDateTextView.setText(list.get(position).getVisitDate());
            holder.visitIdTextView.setText(list.get(position).getVisitId());
            holder.phoneNoTextView.setText(list.get(position).getPhoneNo());
            Picasso.get().load(list.get(position).getImageId()).placeholder(R.drawable.profile_picture).into(holder.circleImageView);
            holder.cardView.setCardBackgroundColor(Color.parseColor(list.get(position).getBackgroundColor()));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView circleImageView;
       private TextView nameTextView,visitDateTextView,visitIdTextView,phoneNoTextView,doctorTypeTextView,chamberTextView;
       private Button confirmButton,removeButton;
       private CardView cardView;

       public myViewHolder(@NonNull View itemView) {
           super(itemView);
           circleImageView = (CircleImageView)itemView.findViewById(R.id.circleImageViewId);
           nameTextView = (TextView)itemView.findViewById(R.id.userNameTextViewId);
           visitDateTextView = (TextView)itemView.findViewById(R.id.visitDateTextViewId);
           visitIdTextView = (TextView)itemView.findViewById(R.id.visitIdTextViiewId);
           phoneNoTextView = (TextView)itemView.findViewById(R.id.phoneNoTextViewId);
           doctorTypeTextView = (TextView)itemView.findViewById(R.id.doctorTypeTextViewId);
           chamberTextView = (TextView)itemView.findViewById(R.id.chamberTextViewId);

           confirmButton = (Button)itemView.findViewById(R.id.confirmButtonId);
           removeButton  = (Button)itemView.findViewById(R.id.removeButtonId);
           cardView = (CardView) itemView.findViewById(R.id.cardViewId);

           confirmButton.setOnClickListener(this::onClick);
           removeButton.setOnClickListener(this::onClick);
       }

        @Override
        public void onClick(View view) {
            if(view == confirmButton){
                Toast.makeText(context, "Confirm button pressed ", Toast.LENGTH_SHORT).show();
            }

            else if(view == removeButton){
                // Here we delete the item on the recyclerView

                        int position = getAdapterPosition();
                        String visitId  = list.get(position).getVisitId();

                        if(person.equals("patient")){ // Patient click the remove button

                            String doctorUid = list.get(position).getUid();

                            DatabaseReference db =  FirebaseDatabase.getInstance().getReference("Users").child(doctorUid).child("Appointments");


                            db.child(visitId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        db.child(visitId).child("patientCancelState").setValue("yes");

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            FirebaseDatabase.getInstance().getReference("Users").child(Rootuid).child("Appointments")
                                    .child(visitId).removeValue();

                            list.remove(position);
                            notifyItemRemoved(position);
                        }

                   else{

                    String patientUid = list.get(position).getUid();

                    DatabaseReference db =  FirebaseDatabase.getInstance().getReference("Users").child(patientUid).child("Appointments");



                    db.child(visitId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                db.child(visitId).child("doctorCancelState").setValue("yes");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    FirebaseDatabase.getInstance().getReference("Users").child(Rootuid).child("Appointments")
                          .child(visitId).removeValue();
                    list.remove(position);

                }

            }
        }
    }

}
