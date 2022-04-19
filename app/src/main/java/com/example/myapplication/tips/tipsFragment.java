package com.example.myapplication.tips;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.homeUserClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class tipsFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private Context context;
    private DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_tips, container, false);
        context = view.getContext();

        recyclerView = view.findViewById(R.id.recyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        databaseReference = FirebaseDatabase.getInstance().getReference("Api");

        return view;
    }


    @Override
    public void onStart() {

        super.onStart();


        FirebaseRecyclerOptions<tipsAdapter>options =
                new FirebaseRecyclerOptions.Builder<tipsAdapter>()
                        .setQuery(databaseReference , tipsAdapter.class).build();

        FirebaseRecyclerAdapter<tipsAdapter,myViewHolder>adapter =
                new FirebaseRecyclerAdapter<tipsAdapter, myViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull tipsAdapter model) {
                        String serialNo = getRef(position).getKey().toString();
                        databaseReference.child(serialNo).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                               final String title  = snapshot.child("title").getValue().toString();
                               final String image = snapshot.child("image").getValue().toString();
                               final String description = snapshot.child("description").getValue().toString();
                               holder.titleTextView.setText(title);
                               holder.descriptionTextView.setText(description);
                                Picasso.get().load(image).placeholder(R.drawable.no_image).into(holder.imageView);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tips_layout,parent,false);
                        return new myViewHolder(view);
                    }
                };

               recyclerView.setAdapter(adapter);
               adapter.startListening();


    }


    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView,descriptionTextView;
        ImageView imageView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = (TextView)itemView.findViewById(R.id.titleTextViewId);
            imageView = (ImageView)itemView.findViewById(R.id.imageViewId);
            descriptionTextView = (TextView)itemView.findViewById(R.id.descriptionId);
        }



    }
}