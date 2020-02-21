package com.pureintentions.hospital;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.pureintentions.hospital.Patient.PatientTab;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button signin, dr;
    EditText email, password;
    TextView register;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CollectionReference reference = db.collection("Users");
        firebaseAuth = FirebaseAuth.getInstance();

        register = findViewById(R.id.register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        progressBar=findViewById(R.id.progressBar);
        dr = findViewById(R.id.dr);
        dr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DoctorSearchScreen.class);
                startActivity(intent);
            }
        });


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {

                /* Six digit password*/
                if (password.getText().toString().length() == 6) {
                    userLogin(password,email,progressBar);
                }
            }
        };

        password.addTextChangedListener(afterTextChangedListener);


        if (firebaseAuth.getCurrentUser() != null) {
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), DoctorSearchScreen.class));
            db.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    //  DataSnapshot data = (DataSnapshot) documentSnapshot.getData();
                    // documentSnapshot.getData();
                    String userRole = documentSnapshot.getString("role");

                    if ("DOCTOR".equalsIgnoreCase(userRole)) {
                        Intent inDoctor = new Intent(MainActivity.this, DoctorSearchScreen.class);
                        inDoctor.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(inDoctor);
                        finish();
                    } else if ("PATIENT".equalsIgnoreCase(userRole)) {
                        Intent inPatient = new Intent(MainActivity.this, PatientTab.class);
                        inPatient.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(inPatient);
                        finish();
                    }


                }
            });
        }

        //signin=findViewById( R.id.signin );
        //signin.setOnClickListener(this );

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
            }
        });
    }

    public void userLogin(final EditText password,final EditText email, final ProgressBar progressBar) {
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        //checking if email and passwords are empty
        blockUI(progressBar,email, password, View.GONE, false);
        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(MainActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(MainActivity.this, "Please enter password", Toast.LENGTH_LONG).show();
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
                        if (task.isSuccessful()) {
                            final FirebaseUser user = firebaseAuth.getCurrentUser();
                            db.collection("Users").document(firebaseAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                    //  DataSnapshot data = (DataSnapshot) documentSnapshot.getData();
                                    // documentSnapshot.getData();
                                    String userRole = documentSnapshot.getString("role");
                                    try {
                                        if ("DOCTOR".equalsIgnoreCase(userRole)) {
                                            Intent inDoctor = new Intent(MainActivity.this, DoctorSearchScreen.class);
                                            inDoctor.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(inDoctor);
                                            finish();
                                        } else if ("PATIENT".equalsIgnoreCase(userRole)) {
                                            Intent inPatient = new Intent(MainActivity.this, PatientTab.class);
                                            inPatient.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(inPatient);
                                            finish();
                                        }
                                    }catch (Exception f) {
                                        Log.e("ERROR", "Exception in user authentication", f);
                                    }finally {
                                        //blockUI(progressBar, email, password, View.GONE, true);
                                    }

                                }

                            });
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void blockUI(ProgressBar progressBar, EditText email, EditText password, int visible, boolean b) {
        progressBar.setVisibility(visible);
        email.setEnabled(b);
        password.setEnabled(b);
    }

    @Override
    public void onClick(View v) {
        if (v == signin) {
            userLogin(password,email, progressBar);
        }
    }
}