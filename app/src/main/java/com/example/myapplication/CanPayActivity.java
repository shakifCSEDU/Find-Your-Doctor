package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CanPayActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private CardView bKashCardView,rocketCardView,mCashCardView,nagadCardView;
    private String isSelected = null;
    private LayoutInflater layoutInflater;
    private  EditText userNumber;
    private View view;
    private  String payContactNo ;
    private String payContactNumber = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_can_pay);
        this.setTitle("Payment Method");

        initview();

        bKashCardView.setOnClickListener(this::onClick);
        nagadCardView.setOnClickListener(this::onClick);
        rocketCardView.setOnClickListener(this::onClick);
        mCashCardView.setOnClickListener(this::onClick);
        button.setOnClickListener(this::onClick);
        layoutInflater = LayoutInflater.from(getApplicationContext());



    }

    private void initview() {

        button = (Button)findViewById(R.id.payButtonId);
        bKashCardView = (CardView)findViewById(R.id.bkashCardViewId);
        mCashCardView = (CardView)findViewById(R.id.mCashCardViewId);
        rocketCardView = (CardView)findViewById(R.id.rocketCardViewId);
        nagadCardView = (CardView)findViewById(R.id.nagadCardViewId);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bkashCardViewId){
            setCardView("Bkash",bKashCardView,R.drawable.bkash);
        }
        if(v.getId() == R.id.nagadCardViewId){
            setCardView("Nagad",nagadCardView,R.drawable.nagad);
        }
        if(v.getId() == R.id.mCashCardViewId){
            setCardView("Mcash",mCashCardView,R.drawable.mcash);
        }
        if(v.getId() == R.id.rocketCardViewId){
            setCardView("Rocket",rocketCardView,R.drawable.rocket);
        }
        if(v.getId() == R.id.payButtonId){
            Intent intent = new Intent(getApplicationContext(),confirmationActitivity.class);
            startActivity(intent);
        }
    }

    private void setCardView(String Name, CardView cardView, int image) {

        if(isSelected == null){
            cardView.setCardBackgroundColor(Color.parseColor("#6495ED"));
            isSelected = Name;
            SetAlertDialogue(Name,image);
        }else if(isSelected.equals(Name)){
            cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            Toast.makeText(getApplicationContext(),"Unselected "+Name+"",Toast.LENGTH_SHORT).show();
            isSelected = null;
        }
    }
    private void SetAlertDialogue(String name,int image){


        view = layoutInflater.inflate(R.layout.payment_set,null,false);
        userNumber = view.findViewById(R.id.phoneNumberId);

        EditText otp = view.findViewById(R.id.otpId);
        payContactNo = userNumber.getText().toString();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CanPayActivity.this);
        alertDialogBuilder.setTitle(name);
        alertDialogBuilder.setIcon(image);
        alertDialogBuilder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        payContactNumber = userNumber.getText().toString();
                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.setView(view);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}