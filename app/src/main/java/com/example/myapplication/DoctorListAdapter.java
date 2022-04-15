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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.myViewHolder>{

    private Context context;
    private ArrayList<HashMap<String,String>>doctorsList = null;



    public DoctorListAdapter(Context context,ArrayList<HashMap<String,String>>doctorsList){
        this.context = context;
        this.doctorsList = doctorsList;
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
        holder.doctorNameTextView.setText(doctorsList.get(position).get("doctorName"));
        holder.instituteTextView.setText(doctorsList.get(position).get("institute"));
        holder.qualification.setText(doctorsList.get(position).get("qualification"));
        holder.rating.setText("3*");
        Picasso.get().load(doctorsList.get(position).get("image")).placeholder(R.drawable.profile_picture).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
      return doctorsList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView doctorNameTextView,instituteTextView,qualification,rating;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
           imageView = (ImageView)itemView.findViewById(R.id.ImageViewId);
           doctorNameTextView = (TextView)itemView.findViewById(R.id.doctorNameTextViewId);
           instituteTextView = (TextView)itemView.findViewById(R.id.instituteTextViewId);
           qualification = (TextView)itemView.findViewById(R.id.qualificationTextViewId);
           rating = (TextView)itemView.findViewById(R.id.ratingTextViewId);



        }
    }
}
