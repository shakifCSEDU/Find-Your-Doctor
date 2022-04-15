package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorDescriptionAdapter extends RecyclerView.Adapter<DoctorDescriptionAdapter.MyViewHoler>{
    private Context context;

    private ArrayList<String>date;
    private ArrayList<Integer>image;

    public DoctorDescriptionAdapter(Context context,ArrayList<Integer>image , ArrayList<String>date){
        this.context = context;
        this.date = date;
        this.image = image;
    }

    @NonNull
    @Override
    public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item,parent,false);
        MyViewHoler viewHoler = new MyViewHoler(view);
        return viewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {
           holder.textView.setText(date.get(position));
           Picasso.get().load(image.get(position)).placeholder(R.drawable.slot_background_white).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    class MyViewHoler extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView textView;

        public MyViewHoler(@NonNull View itemView) {
            super(itemView);
        circleImageView = (CircleImageView)itemView.findViewById(R.id.slot_imageViewId);
        textView = (TextView)itemView.findViewById(R.id.gridTextViewId);

        }
    }
}
