package com.pureintentions.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PatientHomeScreen extends AppCompatActivity {

    CardView prescription , bkd_apt;
    String newAppoint;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String phone , user_mobile,revisit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_patient_home_screen );
        prescription=findViewById(R.id.prescription);
        bkd_apt=findViewById(R.id.bkd_apt);

        final CollectionReference reference = db.collection( "Prescription" );
        CollectionReference user_coll = db.collection("Users");

        bkd_apt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( PatientHomeScreen.this,BookedAppointment.class );
                startActivity( intent );
            }
        });

        prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( PatientHomeScreen.this,PatientPrescription.class );
                startActivity( intent );
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.doctor_screen, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_settings:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity( new Intent( this, MainActivity.class ) );
                break;
        }
        return true;
    }
}
