package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.myViewHolder>{

    private Context context;
    private ArrayList<CustomRowItem>list = null;



    public DoctorListAdapter(Context context,ArrayList<CustomRowItem>list){
        this.context = context;
        this.list = list;
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
        holder.doctorNameTextView.setText(list.get(position).getName());
        holder.instituteTextView.setText(list.get(position).getInstitute());
        holder.qualification.setText(list.get(position).getEducationalQualification());
        //Picasso.get().load()..placeholder(R.drawable.profile_picture).into(holder.imageView);
        holder.imageView.setImageResource(R.drawable.login);
    }

    @Override
    public int getItemCount() {
      return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView doctorNameTextView,instituteTextView,qualification,rating;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
           imageView = (ImageView)itemView.findViewById(R.id.ImageViewId);
           doctorNameTextView = (TextView)itemView.findViewById(R.id.doctorNameTextViewId);
           instituteTextView = (TextView)itemView.findViewById(R.id.instituteTextViewId);
           qualification = (TextView)itemView.findViewById(R.id.qualificationTextViewId);
           rating = (TextView)itemView.findViewById(R.id.ratingTextViewId);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();


            String name = list.get(position).getName();
            String institute = list.get(position).getInstitute();
            String qualification = list.get(position).getEducationalQualification();
            String uid = list.get(position).getUid();

            Toast.makeText(context, ""+uid, Toast.LENGTH_SHORT).show();



            String visitDate = list.get(position).getVisitDate();

            Intent intent = new Intent(context,DoctorDescription.class);
            intent.putExtra("uid",uid);
            intent.putExtra("name",name);
            intent.putExtra("institute",institute);
            intent.putExtra("qualification",qualification);
            intent.putExtra("visitDate",visitDate);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }
}
