package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private ArrayList<homeUserClass> list = null;
    private String person;



    public homeCustomAdapter(Context context, ArrayList<homeUserClass> list, String person) {

        this.context = context;
        this.list = list;
        this.person = person;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(person.equals("patient")){
             View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_custom_adapter_patient_layout,parent,false);
             myViewHolder holder = new myViewHolder(view);
             return holder;
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_custom_adapter_doctor_layout,parent,false);
            myViewHolder holder = new myViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        if(person.equals("patient")){
              holder.nameTextView.setText(list.get(position).getDoctorName());
              holder.visitDateTextView.setText(list.get(position).getVisitDate());
              holder.visitIdTextView.setText(list.get(position).getVisitId());
              holder.phoneNoTextView.setText(list.get(position).getDoctorPhoneNumber());
              holder.doctorTypeTextView.setText(list.get(position).getDoctorType());
              holder.chamberTextView.setText(list.get(position).getChamber());
              Picasso.get().load(R.drawable.profile_picture).into(holder.circleImageView);
              holder.slotTextView.setText(list.get(position).getSlot());
        }
        else{
             holder.nameTextView.setText(list.get(position).getPatientName());
             holder.visitDateTextView.setText(list.get(position).getVisitDate());
             holder.visitIdTextView.setText(list.get(position).getVisitId());
             Picasso.get().load(R.drawable.profile_picture).into(holder.circleImageView);
             holder.phoneNoTextView.setText(list.get(position).getPatientPhoneNumber());
             holder.slotTextView.setText(list.get(position).getSlot());
          }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView nameTextView, visitDateTextView, visitIdTextView, phoneNoTextView, doctorTypeTextView, chamberTextView, slotTextView;


        public myViewHolder(@NonNull View view) {
            super(view);
            if (person.equals("patient")) {


                circleImageView = (CircleImageView) view.findViewById(R.id.circleImageViewId);
                nameTextView = (TextView) view.findViewById(R.id.userNameTextViewId);
                visitDateTextView = (TextView) view.findViewById(R.id.visitDateTextViewId);
                visitIdTextView = (TextView) view.findViewById(R.id.visitIdTextViiewId);
                phoneNoTextView = (TextView) view.findViewById(R.id.phoneNoTextViewId);
                doctorTypeTextView = (TextView) view.findViewById(R.id.doctorTypeTextViewId);
                chamberTextView = (TextView) view.findViewById(R.id.chamberTextViewId);
                slotTextView = (TextView) view.findViewById(R.id.slot);


            }
            else {
                circleImageView = view.findViewById(R.id.circleImageViewId);
                nameTextView = view.findViewById(R.id.userNameTextViewId);
                visitDateTextView = view.findViewById(R.id.visitDateTextViewId);
                visitIdTextView = view.findViewById(R.id.visitIdTextViiewId);
                phoneNoTextView = view.findViewById(R.id.phoneNoTextViewId);
                slotTextView = (TextView) view.findViewById(R.id.slot);
            }


        }

    }

}
//        CircleImageView circleImageView;
//        TextView nameTextView,visitDateTextView,visitIdTextView,phoneNoTextView,doctorTypeTextView,chamberTextView;
//        Button confirmButton,removeButton;
//        CardView cardView;
//
//
//
//        if(TextUtils.equals(person,"patient")){ // If the profile is patients profile then we show this ui..
//
//            if(view == null){
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_custom_adapter_patient_layout,parent,false);
//            }
//            circleImageView = (CircleImageView)view.findViewById(R.id.circleImageViewId);
//            nameTextView = (TextView)view.findViewById(R.id.userNameTextViewId);
//            visitDateTextView = (TextView)view.findViewById(R.id.visitDateTextViewId);
//            visitIdTextView = (TextView)view.findViewById(R.id.visitIdTextViiewId);
//            phoneNoTextView = (TextView)view.findViewById(R.id.phoneNoTextViewId);
//            doctorTypeTextView = (TextView)view.findViewById(R.id.doctorTypeTextViewId);
//            chamberTextView = (TextView)view.findViewById(R.id.chamberTextViewId);
//
//
//            cardView = (CardView)view.findViewById(R.id.cardViewId);
//
//
//            visitDateTextView.setText(list.get(position).getVisitDate());
//            visitIdTextView.setText(list.get(position).getVisitId());
//
//            doctorTypeTextView.setText(list.get(position).getDoctorType());
//            chamberTextView.setText(list.get(position).getChamber());


