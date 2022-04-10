package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    private View view;
    private TextInputLayout textInputEmail,textInputPass;
    private Button loginButton,forgetPasswordButton;
    private TextView needAcount;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        if(firebaseAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            finish();
//        }

        init();
        loginButton.setOnClickListener(this::onClick);
        needAcount.setOnClickListener(this::onClick);

    }
    private void init() {
        textInputEmail = (TextInputLayout)findViewById(R.id.loginEmailId);
        textInputPass = (TextInputLayout)findViewById(R.id.loginPasswordId);
        loginButton = (Button)findViewById(R.id.loginButtonId);
        needAcount = (TextView)findViewById(R.id.needAcountId);
        forgetPasswordButton  = (Button)findViewById(R.id.forgetPasswordId);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.loginButtonId){
            String emailText = textInputEmail.getEditText().getText().toString().trim();
            String passText = textInputPass.getEditText().getText().toString().trim();
            if(TextUtils.equals(emailText,"sakif")   &&  TextUtils.equals(passText,"1234")) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        }
        else if(view.getId() == R.id.needAcountId){
              Intent intent = new Intent(getApplicationContext(),registrationDeciderActivity.class);
              startActivity(intent);
        }
    }


}