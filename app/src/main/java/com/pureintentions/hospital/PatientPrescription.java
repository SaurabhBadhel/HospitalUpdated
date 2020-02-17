package com.pureintentions.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pureintentions.hospital.Prescription.PrescriptionModel;

public class PatientPrescription extends AppCompatActivity {

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference reff=db.collection( "Prescription" );
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    private TextView age,bp,weight,height;
    private  TextView medc1,medc1_time,qty1;
    private  TextView medc2,medc2_time,qty2;
    private  TextView medc3,medc3_time,qty3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_patient_prescription );

        age=findViewById(R.id.age);
        bp=findViewById(R.id.bp);
        weight=findViewById(R.id.weight);
        height=findViewById(R.id.height);

        medc1= findViewById(R.id.medc1);
        medc1_time=findViewById(R.id.medc1_time);
        qty1=findViewById(R.id.qty1);

        medc2= findViewById(R.id.medc2);
        medc2_time=findViewById(R.id.medc2_time);
        qty2=findViewById(R.id.qty2);

        medc3= findViewById(R.id.medc3);
        medc3_time=findViewById(R.id.medc3_time);
        qty3=findViewById(R.id.qty3);



        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();



        db.collection("Prescription").whereEqualTo("uid",FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot snap : queryDocumentSnapshots) {
                    PrescriptionModel.age=snap.getString("Age");
                    age.setText(PrescriptionModel.age);
                    PrescriptionModel.bp=snap.getString("Bp");
                    bp.setText(PrescriptionModel.bp);
                    PrescriptionModel.height=snap.getString("Height");
                    height.setText(PrescriptionModel.height);
                    PrescriptionModel.weight=snap.getString("Weight");
                    weight.setText(PrescriptionModel.weight);

                  /*  PrescriptionModel.medicine_1=snap.getString("Medicine1");
                    medc1.setText(PrescriptionModel.medicine_1);
                    PrescriptionModel.quantity_1=snap.getString("Duration1");
                    qty1.setText(PrescriptionModel.quantity_1);
                    PrescriptionModel.medicine_time1=snap.getString("Medicine Time1");
                    medc1_time.setText(PrescriptionModel.medicine_time1);

                    PrescriptionModel.medicine_2=snap.getString("Medicine2");
                    medc2.setText(PrescriptionModel.medicine_2);
                    PrescriptionModel.quantity_2=snap.getString("Duration2");
                    qty2.setText(PrescriptionModel.quantity_2);
                    PrescriptionModel.medicine_time2=snap.getString("Medicine Time2");
                    medc2_time.setText(PrescriptionModel.medicine_time2);

                    PrescriptionModel.medicine_3=snap.getString("Medicine3");
                    medc3.setText(PrescriptionModel.medicine_3);
                    PrescriptionModel.quantity_3=snap.getString("Duration3");
                    qty3.setText(PrescriptionModel.quantity_3);
                    PrescriptionModel.medicine_time3=snap.getString("Medicine Time3");
                    medc3_time.setText(PrescriptionModel.medicine_time3);*/


                }

            }
        });

    }
}
