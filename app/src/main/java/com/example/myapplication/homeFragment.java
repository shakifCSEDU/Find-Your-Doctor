package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;


public class homeFragment extends Fragment {
    private ShimmerFrameLayout shimmerFrameLayout;
    private Button homePageButton;
    private RecyclerView recyclerView;
    private View view;
    private Context context;
    Boolean isDoctor  = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();
        shimmerFrameLayout = (ShimmerFrameLayout)view.findViewById(R.id.shimmerId);
        shimmerFrameLayout.startShimmer();



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homePageButton = (Button)view.findViewById(R.id.homePageButtonId);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewId);





    }
}