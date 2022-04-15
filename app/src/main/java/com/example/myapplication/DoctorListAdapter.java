package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


class Doctor{
}


public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.myViewHolder>{

    private Context context;
    private String[] doctors_name,doctors_type;


    public DoctorListAdapter(Context context,String[] doctors_name,String[] doctors_type){
        this.context = context;
        this.doctors_name = doctors_name;
        this.doctors_type = doctors_type;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.doctor_list_row,parent,false);
        myViewHolder viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
            holder.doctorNameTextView.setText(doctors_name[position]);
            holder.doctorTypeTextView.setText(doctors_type[position]);
            holder.rating.setText("3*");
            holder.visitMoney.setText("1000");
            Picasso.get().load(R.drawable.icon_profile).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return doctors_name.length;
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView doctorNameTextView,doctorTypeTextView,visitMoney,rating;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.patientImageViewId);
            doctorNameTextView = (TextView)itemView.findViewById(R.id.doctorNameTextViewId);
            doctorTypeTextView = (TextView)itemView.findViewById(R.id.doctorTypeTextViewId);
            visitMoney = (TextView)itemView.findViewById(R.id.visitMoneyTextViewId);
            rating = (TextView)itemView.findViewById(R.id.ratingTextViewId);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,DoctorDescription.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
