package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;


public class loginFragment extends Fragment implements View.OnClickListener {
    private View view;
     private TextInputLayout textInputEmail,textInputPass;
    private Button loginButton,forgetPasswordButton;
    private TextView needAcount;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        init();
        return view;
    }

    private void init() {
        textInputEmail = (TextInputLayout)view.findViewById(R.id.loginEmailId);
        textInputPass = (TextInputLayout)view.findViewById(R.id.loginPasswordId);
        loginButton = (Button)view.findViewById(R.id.loginButtonId);
        needAcount = (TextView)view.findViewById(R.id.needAcountId);
        forgetPasswordButton  = (Button)view.findViewById(R.id.forgetPasswordId);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.loginButtonId){
            String emailText = textInputEmail.getEditText().getText().toString().trim();
            String passText = textInputPass.getEditText().getText().toString().trim();
            if(TextUtils.equals(emailText,"sakif")   &&  TextUtils.equals(passText,"1234")) {
                navController.navigate(R.id.action_loginFragment_to_homeFragment);

            }
        }
        else if(view.getId() == R.id.needAcountId){
            navController.navigate(R.id.action_loginFragment_to_registrationDeciderFragment);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        loginButton.setOnClickListener(this);
        needAcount.setOnClickListener(this);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}