package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;


public class homeFragment extends Fragment implements View.OnClickListener {
    private ShimmerFrameLayout shimmerFrameLayout;
    private Button homePageButton;
    private NavController navController;


    private RecyclerView recyclerView;
    private View view;
    private Context context;
    Boolean isDoctor  = false;
    private int userType = 2;


    // Manage Slot UI element
    private GridView gridView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();
        String myDataFromActivity = activity.getUserType();


        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();
        shimmerFrameLayout = (ShimmerFrameLayout)view.findViewById(R.id.shimmerId);
        shimmerFrameLayout.startShimmer();

        Toast.makeText(context,myDataFromActivity+"...",Toast.LENGTH_LONG).show();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        homePageButton = (Button)view.findViewById(R.id.homePageButtonId);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewId);

        if(userType == 1){ // This userType defines that this is doctor page.
            homePageButton.setText("Manage Slot");

        }
        else if(userType == 2){
            homePageButton.setText("Search Doctor");
        }

        homePageButton.setOnClickListener(this);







    }

    @Override
    public void onClick(View view) {
        if(view == homePageButton){
            if(userType == 1){
                navController.navigate(R.id.action_homeFragment_to_manageSlotFragment);

            }else if(userType == 2){
                navController.navigate(R.id.action_homeFragment_to_searchDoctorFragment);
            }
        }
    }
}