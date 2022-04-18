//package com.example.myapplication;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.graphics.Color;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class homeCustomAdapter extends BaseAdapter {
//    private Context context;
//    private ArrayList<homeUserClass>list = null;
//    private String person;
//    private DatabaseReference databaseReference;
//    private String Rootuid;
//
//    public homeCustomAdapter(Context context, ArrayList<homeUserClass> list,String person,String Rootuid) {
//
//        this.context = context;
//        this.list = list;
//        this.person = person;
//        this.Rootuid = Rootuid;
//
//        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(Rootuid).child("Appointments");
//
//    }
//
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return list.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//
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
//            confirmButton = (Button)view.findViewById(R.id.confirmButtonId);
//            removeButton  = (Button)view.findViewById(R.id.removeButtonId);
//            cardView = (CardView)view.findViewById(R.id.cardViewId);
//
//
//            visitDateTextView.setText(list.get(position).getVisitDate());
//            visitIdTextView.setText(list.get(position).getVisitId());
//
//            doctorTypeTextView.setText(list.get(position).getDoctorType());
//            chamberTextView.setText(list.get(position).getChamber());
//            cardView.setCardBackgroundColor(Color.parseColor(list.get(position).getBackgroundColor()));
//
//
//
//
//            removeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    String visitId  = list.get(position).getVisitId();
//                    // String doctorUid = list.get(position).getUid();
//                   // DatabaseReference db =  FirebaseDatabase.getInstance().getReference("Users").child(doctorUid).child("Appointments");
//
//
//                   // db.child(visitId).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if(snapshot.exists()){
//                                db.child(visitId).child("patientCancelState").setValue("yes");
//
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//                    FirebaseDatabase.getInstance().getReference("Users").child(Rootuid).child("Appointments")
//                            .child(visitId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    list.remove(position);
//                                    notifyDataSetChanged();
//
//                                }
//                            });
//
//
//
//                }
//            });
//
//
//
//        }
//        else{
//            if(view == null){
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_custom_adapter_doctor_layout,parent,false);
//
//            }
//        }
//
//
//        return view;
//    }
//}
