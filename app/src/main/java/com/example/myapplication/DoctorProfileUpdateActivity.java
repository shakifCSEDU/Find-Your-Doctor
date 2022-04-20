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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Queue;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorProfileUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private View view;
    private Button updateButton;
    private TextInputEditText emailTextInputEditText, firstNameTextInputEditText, lastNameTextInputEditText, phoneTextInputEditText, genderTextInputEditText,  passwordTextInputEditText;
    private TextInputEditText instituteTextInputEdittext, chamberTextInputEditText, eduQualificationTextInputEditText;

    private Uri imageUri;

    private StorageReference storageReference;

    private String email, firstName, lastName, phone, gender, region, password, userId, institute, chamber, eduQualification, speciality;
    private AutoCompleteTextView regionAutoCompleteTextView, specialityAutoCompleteTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,databaseReference1,databaseReference2;
    private FirebaseUser firebaseUser;


    private boolean isProfileImageChange = false;
    private Uri uri;
    private ImageView profileImage;
    private ImageButton imageButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile_update);

        getIntentData();

        init();


        String[] doctorRegion = getResources().getStringArray(R.array.Dhaka_region);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_item,doctorRegion);
        regionAutoCompleteTextView.setAdapter(arrayAdapter1);

        String[] doctorSpeciality = getResources().getStringArray(R.array.Doctor_Speciality);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_item,doctorSpeciality);
        specialityAutoCompleteTextView.setAdapter(arrayAdapter2);

        emailTextInputEditText.setText(email);
        firstNameTextInputEditText.setText(firstName);
        lastNameTextInputEditText.setText(lastName);
        phoneTextInputEditText.setText(phone);
        genderTextInputEditText.setText(gender);
        regionAutoCompleteTextView.setText(region);
        passwordTextInputEditText.setText(password);
        instituteTextInputEdittext.setText(institute);
        chamberTextInputEditText.setText(chamber);
        eduQualificationTextInputEditText.setText(eduQualification);
        specialityAutoCompleteTextView.setText(speciality);
        if(imageUrl != null){
            Picasso.get().load(imageUrl).placeholder(R.drawable.profile_picture).into(profileImage);
        }
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);



        // uploading image
        storageReference = FirebaseStorage.getInstance("gs://find-your-doctor-d7ff3.appspot.com").getReference("profileImage");
        databaseReference1 = databaseReference.child("profileImage");

        profileImage.setOnClickListener(this::onClick);

        imageButton.setOnClickListener(this::onClick);


        emailTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Email cannot be changed", Toast.LENGTH_SHORT).show();
            }
        });

        specialityAutoCompleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Speciality cannnot be changed", Toast.LENGTH_SHORT).show();
            }
        });


                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isProfileImageChange || isFirstNameChanged() || isLastNameChanged() || isPasswordChanged() || isPhoneNoChanged() || isGenderChanged() || isRegionChanged() || isChamberChanged() || isInstituteChanged() || isEduQualificationChanged()) {
                            Toast.makeText(getApplicationContext(), "Data has been Updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "data not changed ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
    private void getIntentData(){
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        phone = intent.getStringExtra("phoneNumber");
        gender = intent.getStringExtra("gender");
        region = intent.getStringExtra("region");
        password = intent.getStringExtra("password");
        institute = intent.getStringExtra("institute");
        chamber = intent.getStringExtra("chamber");
        eduQualification = intent.getStringExtra("educationalQualification");
        speciality = intent.getStringExtra("speciality");
        imageUrl = intent.getStringExtra("profileImage");
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

    private boolean isRegionChanged() {
        if(!region.equals(regionAutoCompleteTextView.getText().toString())){
            databaseReference.child("region").setValue(regionAutoCompleteTextView.getText().toString());
            databaseReference2 = FirebaseDatabase.getInstance().getReference("DoctorType").child(speciality);
            Query query = databaseReference2.child(region).orderByChild("uid").equalTo(userId);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //
                }
            });

            region = regionAutoCompleteTextView.getText().toString();

            String key = databaseReference2.push().getKey();

            FirebaseDatabase.getInstance().getReference("DoctorType").child(speciality).child(region).child(key).child("uid").setValue(userId).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                       // Toast.makeText(DoctorProfileUpdateActivity.this, "okkkkkkkkkkkkkkk", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(DoctorProfileUpdateActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });

            return true;
        }else {
            return false;
        }
    }

    private boolean isInstituteChanged() {
        if(!institute.equals(instituteTextInputEdittext.getText().toString())){
            databaseReference.child("institute").setValue(instituteTextInputEdittext.getText().toString());
            institute = instituteTextInputEdittext.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isChamberChanged() {
        if(!chamber.equals(chamberTextInputEditText.getText().toString())){
            databaseReference.child("chamber").setValue(chamberTextInputEditText.getText().toString());
            chamber = chamberTextInputEditText.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isEduQualificationChanged() {
        if(!eduQualification.equals(eduQualificationTextInputEditText.getText().toString())){
            databaseReference.child("educationalQualification").setValue(eduQualificationTextInputEditText.getText().toString());
            eduQualification = eduQualificationTextInputEditText.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private void init(){

        profileImage = (ImageView)findViewById(R.id.profileImageViewId);
        imageButton = (ImageButton)findViewById(R.id.selectImageButton);
        emailTextInputEditText = findViewById(R.id.emailTxt);
        firstNameTextInputEditText = findViewById(R.id.firstNameTxt);
        lastNameTextInputEditText = findViewById(R.id.lastNameTxt);
        phoneTextInputEditText = findViewById(R.id.phoneTxt);
        genderTextInputEditText = findViewById(R.id.genderTxt);
        regionAutoCompleteTextView = findViewById(R.id.regionTxt);
        passwordTextInputEditText = findViewById(R.id.passwordTxt);
        instituteTextInputEdittext =findViewById(R.id.instituteTxt);
        chamberTextInputEditText = findViewById(R.id.chamberTxt);
        eduQualificationTextInputEditText = findViewById(R.id.educationalQualificationTxt);
        specialityAutoCompleteTextView = findViewById(R.id.specialityTxt);

        updateButton = findViewById(R.id.updateButton);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.selectImageButton){
            openFileChooser();
        }
    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
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