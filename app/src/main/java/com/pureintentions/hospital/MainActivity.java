package com.pureintentions.hospital;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.pureintentions.hospital.Doctor.LoggedInUser;
import com.pureintentions.hospital.Doctor.LoginRepository;
import com.pureintentions.hospital.Patient.PatientTab;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button signin,dr;
    EditText email,password;
    TextView register;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db  = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CollectionReference reference = db.collection( "Users" );
        firebaseAuth = FirebaseAuth.getInstance();
        dr=findViewById( R.id.dr );
        dr.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( MainActivity.this,PatientTab.class );
                startActivity( intent );
            }
        } );
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), DoctorSearchScreen.class)); db.collection( "Users" ).document( firebaseAuth.getCurrentUser().getUid() ).addSnapshotListener( new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    //  DataSnapshot data = (DataSnapshot) documentSnapshot.getData();
                    // documentSnapshot.getData();
                    String userRole = documentSnapshot.getString("role");

                    if("DOCTOR".equalsIgnoreCase(userRole))
                    {
                        Intent inDoctor = new Intent(MainActivity.this, DoctorSearchScreen.class);
                        inDoctor.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(inDoctor);
                        finish();
                    }
                    else
                    if("PATIENT".equalsIgnoreCase(userRole))
                    {
                        Intent inPatient = new Intent(MainActivity.this, PatientTab.class);
                        inPatient.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(inPatient);
                        finish();
                    }


                }
            } );
        }
        register=findViewById( R.id.register );
        email=findViewById( R.id.email );
        password=findViewById( R.id.password );
        signin=findViewById( R.id.signin );
        signin.setOnClickListener(this );

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
            }
        });
    }
    public void userLogin(){
        String mail = email.getText().toString().trim();
        String pass  = password.getText().toString().trim();
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(mail)){
            Toast.makeText(MainActivity.this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(MainActivity.this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        //if the email and password are not empty
        //displaying a progress dialog
        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if the task is successful
                        if(task.isSuccessful()){
                            final FirebaseUser user = firebaseAuth.getCurrentUser();
                            db.collection( "Users" ).document( firebaseAuth.getCurrentUser().getUid() ).addSnapshotListener( new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                    //  DataSnapshot data = (DataSnapshot) documentSnapshot.getData();
                                   // documentSnapshot.getData();
                                    String userRole = documentSnapshot.getString("role");

                                    if("DOCTOR".equalsIgnoreCase(userRole))
                                    {
                                        Intent inDoctor = new Intent(MainActivity.this, DoctorSearchScreen.class);
                                        inDoctor.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(inDoctor);
                                        finish();
                                    }
                                    else
                                    if("PATIENT".equalsIgnoreCase(userRole))
                                    {
                                        Intent inPatient = new Intent(MainActivity.this, PatientTab.class);
                                        inPatient.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(inPatient);
                                        finish();
                                    }


                                }
                            } );
                              }
                        else {
                            Toast.makeText( MainActivity.this, "Invalid ", Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {
        if(v == signin){
            userLogin();
        }
    }
}