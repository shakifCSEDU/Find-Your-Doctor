package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientProfileUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private Button updateButton;
    private TextInputEditText emailTextInputEditText, firstNameTextInputEditText, lastNameTextInputEditText, phoneTextInputEditText, genderTextInputEditText,  passwordTextInputEditText;
    private String email, firstName, lastName, phone, gender, district, password, userId;
    private AutoCompleteTextView districtAutoCompleteTextView;



    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,databaseReference1;
    private FirebaseUser firebaseUser;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private boolean isProfileImageChange = false;

    private static final int  PICK_IMAGE_REQUEST = 1;
    private Uri uri;
    private ImageView profileImage;
    private ImageButton imageButton;
    private String imageUrl = null;
    HashMap<String,String> profileData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_update);

        getDataFromIntent();


        init();

        String[] patientDistrict = getResources().getStringArray(R.array.District);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_item,patientDistrict);
        districtAutoCompleteTextView.setAdapter(arrayAdapter);

        emailTextInputEditText.setText(email);
        firstNameTextInputEditText.setText(firstName);
        lastNameTextInputEditText.setText(lastName);
        phoneTextInputEditText.setText(phone);
        genderTextInputEditText.setText(gender);
        districtAutoCompleteTextView.setText(district);
        passwordTextInputEditText.setText(password);
        if(imageUrl != null) {
            Picasso.get().load(imageUrl).placeholder(R.drawable.profile_picture).into(profileImage);
        }
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        // Database reference for uploading image

        storageReference = FirebaseStorage.getInstance("gs://find-your-doctor-d7ff3.appspot.com").getReference("profileImage");
        databaseReference1 = databaseReference.child("profileImage");



        imageButton.setOnClickListener(this);

        emailTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Email cannot be changed", Toast.LENGTH_SHORT).show();
            }
        });



        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isProfileImageChange || isFirstNameChanged() || isLastNameChanged() || isPasswordChanged() || isPhoneNoChanged() || isGenderChanged() || isDistrictChanged()){
                    Toast.makeText(getApplicationContext(), "Data has been Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "data not changed ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        phone = intent.getStringExtra("phoneNumber");
        gender = intent.getStringExtra("gender");
        district = intent.getStringExtra("district");
        password = intent.getStringExtra("password");
        imageUrl = intent.getStringExtra("profileImage");
    }


    private void init(){
        emailTextInputEditText = findViewById(R.id.emailTxt);
        firstNameTextInputEditText = findViewById(R.id.firstNameTxt);
        lastNameTextInputEditText = findViewById(R.id.lastNameTxt);
        phoneTextInputEditText = findViewById(R.id.phoneTxt);
        genderTextInputEditText = findViewById(R.id.genderTxt);
        districtAutoCompleteTextView  = findViewById(R.id.districtTxt);
        passwordTextInputEditText = findViewById(R.id.passwordTxt);
        updateButton = findViewById(R.id.updateButton);
        profileImage = (ImageView)findViewById(R.id.profileImageViewId);
        imageButton = (ImageButton)findViewById(R.id.selectImageButton);
    }


    private boolean isPhoneNoChanged() {
        if(!phone.equals(phoneTextInputEditText.getText().toString())){
            databaseReference.child("mobileNumber").setValue(phoneTextInputEditText.getText().toString());
            phone = phoneTextInputEditText.getText().toString();
            return true;
        }else {
            return false;
        }
    }
    private boolean isFirstNameChanged(){
        if(!firstName.equals(firstNameTextInputEditText.getText().toString())){
            databaseReference.child("firstName").setValue(firstNameTextInputEditText.getText().toString());
            firstName = firstNameTextInputEditText.getText().toString();
            return true;
        }else {
            return false;
        }

    }
    private boolean isLastNameChanged(){
        if(!lastName.equals(lastNameTextInputEditText.getText().toString())){
            databaseReference.child("lastName").setValue(lastNameTextInputEditText.getText().toString());
            lastName = lastNameTextInputEditText.getText().toString();
            return true;
        }else {
            return false;
        }

    }

    private boolean isPasswordChanged(){
        if(!password.equals(passwordTextInputEditText.getText().toString()) ){

            AuthCredential authCredential = EmailAuthProvider.getCredential(firebaseUser.getEmail(),password);
            firebaseUser.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    firebaseUser.updatePassword(passwordTextInputEditText.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            if((!passwordTextInputEditText.getText().toString().isEmpty()) && passwordTextInputEditText.getText().toString().length()>5){
                                databaseReference.child("password").setValue(passwordTextInputEditText.getText().toString());
                                password = passwordTextInputEditText.getText().toString();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(getApplicationContext(), ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return true;


        }else{
            return false;
        }

    }

    private boolean isGenderChanged() {
        if(!gender.equals(genderTextInputEditText.getText().toString())){
            databaseReference.child("gender").setValue(genderTextInputEditText.getText().toString());
            gender = genderTextInputEditText.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isDistrictChanged() {
        if(!district.equals(districtAutoCompleteTextView.getText().toString())){
            databaseReference.child("district").setValue(districtAutoCompleteTextView.getText().toString());
            district = districtAutoCompleteTextView.getText().toString();
            return true;
        }else {
            return false;
        }
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);

    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.selectImageButton){
            openFileChooser();
        }
    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile() {

        if(uri != null){

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Picture");
            progressDialog.setMessage("Wait ... until the uploading is successfully completed..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));


            fileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    databaseReference1.setValue(imageUrl);
                                    isProfileImageChange = true;
                                    progressDialog.dismiss();
                                }
                            });
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot snapshot) {

                        }
                    });



        }else{
            Toast.makeText(getApplicationContext(),"No file Selected",Toast.LENGTH_SHORT).show();
        }


    }




    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            Picasso.get().load(uri).placeholder(R.drawable.profile_picture).into(profileImage);
            uploadFile();
        }
    }


}