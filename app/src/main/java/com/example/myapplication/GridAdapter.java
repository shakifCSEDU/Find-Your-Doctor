package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class gridItemClass{
    int image;
    String numberText;

    public gridItemClass(int image, String numberText) {
        this.image = image;
        this.numberText = numberText;
    }
}

public class GridAdapter extends BaseAdapter {

    private Context context;
    ArrayList<gridItemClass>list = null;
    private LayoutInflater inflater;

    public GridAdapter(Context context, ArrayList<gridItemClass> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = LayoutInflater.from(context);

        if(view == null){
            view = inflater.inflate(R.layout.grid_item,viewGroup,false);
        }
       CircleImageView imageView =  view.findViewById(R.id.slot_imageViewId);
       TextView textView =  view.findViewById(R.id.gridTextViewId);

       // here we check the color of the slot image.

       imageView.setImageResource(list.get(i).image);
       textView.setText(list.get(i).numberText);

        return view;
    }
}
