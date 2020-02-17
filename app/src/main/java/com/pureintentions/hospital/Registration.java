package com.pureintentions.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;



    public class Registration extends AppCompatActivity implements View.OnClickListener{

        EditText cliName1,cliAge,cliSex,cliEmail,cliDOB,cliPass,cliPhone;
        Button btnRegister;

        String userID;
        public static final String TAG = "TAG";
        private FirebaseAuth mAuth;
        FirebaseFirestore mStore;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_registration );
            cliName1=findViewById( R.id.cliName1 );
            cliAge=findViewById( R.id.cliAge );
            cliSex=findViewById( R.id.cliSex );
            cliEmail=findViewById( R.id.cliEmail );
            cliDOB=findViewById( R.id.cliDOB );
            cliPass=findViewById( R.id.cliPass );
            btnRegister=findViewById( R.id.btnRegister );
            cliPhone=findViewById( R.id.cliPhone );

            mStore=FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();



            btnRegister.setOnClickListener(this);
        }
        @Override
        protected void onStart() {
            super.onStart();

            if (mAuth.getCurrentUser() != null) {
                //handle the already login user
                startActivity(new Intent(getApplicationContext(),PatientHomeScreen.class));
                finish();
            }
        }
        private void registerUser() {
            final String name = cliName1.getText().toString().trim();
            final String age = cliAge.getText().toString().trim();
            final String sex = cliSex.getText().toString().trim();
            final String email = cliEmail.getText().toString().trim();
            final String phone = cliPhone.getText().toString().trim();
            final String dob = cliDOB.getText().toString().trim();
            String password = cliPass.getText().toString().trim();


            if (name.isEmpty()) {
                cliName1.setError(getString(R.string.input_error_name));
                cliName1.requestFocus();
                return;
            }
            if (age.isEmpty()) {
                cliAge.setError(getString(R.string.input_error_age));
                cliAge.requestFocus();
                return;
            }
            if (sex.isEmpty()) {
                cliSex.setError(getString(R.string.input_error_sex));
                cliSex.requestFocus();
                return;
            }
            if (dob.isEmpty()) {
                cliDOB.setError(getString(R.string.input_error_dob));
                cliDOB.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                cliEmail.setError(getString(R.string.input_error_email));
                cliEmail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                cliEmail.setError(getString(R.string.input_error_email_invalid));
                cliEmail.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                cliPass.setError(getString(R.string.input_error_password));
                cliPass.requestFocus();
                return;
            }

            if (password.length() < 6) {
                cliPass.setError(getString(R.string.input_error_password_length));
                cliPass.requestFocus();
                return;
            }

            if (phone.isEmpty()) {
                cliPhone.setError(getString(R.string.input_error_phone));
                cliPhone.requestFocus();
                return;
            }

            if (phone.length() != 10) {
                cliPhone.setError(getString(R.string.input_error_phone_invalid));
                cliPhone.requestFocus();
                return;
            }

            mAuth.createUserWithEmailAndPassword(phone+"@user.com",password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Registration.this, "User Created.", Toast.LENGTH_SHORT).show();
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = mStore.collection("Users").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("name",name);
                        user.put("email",email);
                        user.put("phone",phone);
                        user.put("age",age);
                        user.put("dob",dob);
                        user.put("sex",sex);
                        user.put("Role","Patient");
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.toString());
                            }
                        });
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));

                    }else {
                        Toast.makeText(Registration.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        // progressBar.setVisibility(View.GONE);
                    }
                }
            });



        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnRegister:
                    registerUser();
                    break;
            }
        }

    }