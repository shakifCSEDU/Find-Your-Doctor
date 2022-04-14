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
    String userType;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        MainActivity activity = (MainActivity) getActivity();
        String myDataFromActivity = activity.getMyData();

        if(myDataFromActivity != null){

            if(myDataFromActivity.equals("1")){
                view = inflater.inflate(R.layout.fragment_patient_home, container, false);
            }
            else if(myDataFromActivity.equals("2")){
                view = inflater.inflate(R.layout.fragment_doctor_home_fragement, container, false);
            }
        }




        //view = inflater.inflate(R.layout.fragment_home, container, false);

        context = view.getContext();

        //Toast.makeText(getContext(),userType,Toast.LENGTH_SHORT).show();

        //       shimmerFrameLayout = (ShimmerFrameLayout)view.findViewById(R.id.shimmerId);
//        shimmerFrameLayout.startShimmer();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homePageButton = (Button)view.findViewById(R.id.homePageButtonId);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewId);





    }
}