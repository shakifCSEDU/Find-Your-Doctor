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
import androidx.recyclerview.widget.LinearLayoutManager;
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

    private String userType;

    ArrayList<homeUserClass>list1 = new ArrayList<homeUserClass>(); // Here list1 ---> patient see doctors appontment

    ArrayList<homeUserClass>list2 = new ArrayList<homeUserClass>(); // Here list1 ---> patient see doctors appontment



    String[] nameString  , locationString;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();
        userType = activity.getMyData();


        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();

        nameString = getResources().getStringArray(R.array.doctors_name);
        locationString = getResources().getStringArray(R.array.Dhaka_region);


        shimmerCode();

        return view;
    }

    private void shimmerCode() {
        shimmerFrameLayout = (ShimmerFrameLayout)view.findViewById(R.id.shimmerId);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.stopShimmer();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        navController = Navigation.findNavController(view);
        homePageButton = (Button)view.findViewById(R.id.homePageButtonId);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerViewId);




        if(userType.equals("2")){ // This userType defines that this is doctor page.
            homePageButton.setText("Manage Slot");


           // list1 hoise doctor jkhn next appointment khujte jabe tkhn tar value gula jst list2 te add kre dbo...

            // added all data in the arralist.
            for(int i = 0 ;i< nameString.length  ; i++){
                list2.add(new homeUserClass(nameString[i],"visitDate","visitID","010100101"));
            }

            homeCustomAdapter adapter = new homeCustomAdapter(context,list2,"doctor"); // here we pass patient because we want to check who he is.
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);

        }









        else if(userType.equals("1")){ ///  This condition is entered for the patient .
            homePageButton.setText("Search Doctor");

            // added all data in the arralist.
            for(int i = 0 ;i< nameString.length  ; i++){
                list1.add(new homeUserClass(nameString[i],"visitDate","visitId","019101010","MBBS",locationString[i],R.id.profileImageId));
            }
                // initialise the recyclerView
            homeCustomAdapter adapter = new homeCustomAdapter(context,list1,"patient"); // here we pass patient because we want to check who he is.
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        }


        homePageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == homePageButton){
            if(userType.equals("2")){
                navController.navigate(R.id.action_homeFragment_to_manageSlotAcitivity);
            }else if(userType.equals("1")){
                navController.navigate(R.id.action_homeFragment_to_searchDoctorFragment);
            }
        }
    }
}