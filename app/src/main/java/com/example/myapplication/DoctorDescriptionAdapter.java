package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorDescriptionAdapter extends BaseAdapter {
    private Context context;

    private ArrayList<CustomGrid>customGrids = null;

    public DoctorDescriptionAdapter(Context context,ArrayList<CustomGrid>customGrids){
        this.context = context;
        this.customGrids = customGrids;
    }


    @Override
    public int getCount() {
        return customGrids.size();
    }

    @Override
    public Object getItem(int i) {
        return customGrids.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, viewGroup, false);
        }

            CircleImageView imageView =  view.findViewById(R.id.slot_imageViewId);
            TextView textView = (TextView)view.findViewById(R.id.gridTextViewId);

            Picasso.get().load(customGrids.get(i).getImage()).placeholder(R.drawable.slot_background_white).into(imageView);
            textView.setText(customGrids.get(i).getSlotName());


        return view;
    }
}

