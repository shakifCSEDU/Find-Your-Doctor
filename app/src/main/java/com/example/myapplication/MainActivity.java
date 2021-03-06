package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private Bundle bundle;


    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView =(BottomNavigationView) findViewById(R.id.bottomNavigationViewId);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        navController = navHostFragment.getNavController();

        Intent intent = getIntent();
        userType = intent.getStringExtra("userType").toString();


//        homeFragment home_fragment = new homeFragment();
////        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
////                .beginTransaction();
//
//        Bundle data  = new Bundle();
//        data.putString("key_value", userType);
//        home_fragment.setArguments(data);

       //fragmentTransaction.replace(R.id.homeFragment, home_fragment).commit();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment,
                R.id.historyFragment,
                R.id.tipsFragment,
                R.id.profileFragment,
                R.id.historyFragment,
                R.id.searchDoctorFragment

        ).build();


//
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);


    }
    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp() || navController.navigateUp();
    }

    public String getMyData (){
        return userType;
    }

}
//    int fragmentId =  navController.getCurrentDestination().getId();
//        if(fragmentId == R.id.loginFragment){
//                Toast.makeText(getApplicationContext(),"Login ",Toast.LENGTH_SHORT).show();
//                }else{
//                Toast.makeText(getApplicationContext(),"Others ",Toast.LENGTH_SHORT).show();
//                }