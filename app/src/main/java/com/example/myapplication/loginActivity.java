package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    private View view;
    private TextInputLayout textInputEmail,textInputPass;
    private Button loginButton,forgetPasswordButton;
    private TextView needAccount;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase userDatabaseReference;
    private String current_User_Id, userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        init();
        loginButton.setOnClickListener(this::onClick);
        needAccount.setOnClickListener(this::onClick);
        forgetPasswordButton.setOnClickListener(this::onClick);

    }

    private void init() {
        textInputEmail = (TextInputLayout)findViewById(R.id.loginEmailId);
        textInputPass = (TextInputLayout)findViewById(R.id.loginPasswordId);
        loginButton = (Button)findViewById(R.id.loginButtonId);
        needAccount = (TextView)findViewById(R.id.needAcountId);
        forgetPasswordButton  = (Button)findViewById(R.id.forgetPasswordId);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.loginButtonId){
            String emailText = textInputEmail.getEditText().getText().toString().trim();
            String passText = textInputPass.getEditText().getText().toString().trim();

            if(emailText.isEmpty()){
                textInputEmail.setError("Email is required");
                Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if(passText.isEmpty()){
                textInputPass.setError("Password is required");
                Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if(passText.length() <= 6 ){
                textInputPass.setError("Password must be >= 6 Characters");
                Toast.makeText(getApplicationContext(), "Please fill up the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(emailText,passText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        if(firebaseUser.isEmailVerified()){
                            current_User_Id = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference userDatabaseReference = FirebaseDatabase.getInstance().getReference("Users").child(current_User_Id).child("userType");
                            userDatabaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String userType = snapshot.getValue().toString();
                                    Toast.makeText(loginActivity.this,"Logged In SuccessFully" + userType, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    intent.putExtra("userType",userType);
                                    startActivity(intent);
                                    finish();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else{
                            firebaseUser.sendEmailVerification();
                            Toast.makeText(loginActivity.this,"Plz verify your email before first time log-in", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(loginActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else if(view.getId() == R.id.needAcountId){
              Intent intent = new Intent(getApplicationContext(),registrationDeciderActivity.class);
              startActivity(intent);
        }
        else if(view.getId() == R.id.forgetPasswordId){
            EditText resetMail = new EditText(view.getContext());
            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
            passwordResetDialog.setTitle("Reset Password?");
            passwordResetDialog.setMessage("Type your email registered address: ");
            passwordResetDialog.setView(resetMail);

            passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //extract the email and send password to it
                    String mail = resetMail.getText().toString();
                    firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(loginActivity.this, "Reset link is sent to your email",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(loginActivity.this, e.getMessage() +"\nReset link is not sent",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //close the dialog box
                }
            });

            passwordResetDialog.create().show();


        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(firebaseAuth.getCurrentUser()!= null && firebaseAuth.getCurrentUser().isEmailVerified()){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }
//    }


}