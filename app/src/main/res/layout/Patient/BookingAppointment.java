package com.pureintentions.hospital.Patient;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pureintentions.hospital.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookingAppointment extends AppCompatActivity implements  View.OnClickListener{

    Button btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime,Pname,edt_dob;
    Button btn_bkd;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private FirebaseFirestore db  = FirebaseFirestore.getInstance();
    String uid = FirebaseAuth.getInstance().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_appointment);

        btnDatePicker=  findViewById(R.id.btn_date);
        btnTimePicker=  findViewById(R.id.btn_time);
        txtDate= findViewById(R.id.in_date);
        txtTime=  findViewById(R.id.in_time);
        btn_bkd=findViewById(R.id.btn_bkd);
        Pname=(EditText) findViewById(R.id.Pname);
        edt_dob= (EditText)findViewById(R.id.edt_dob);

        btnDatePicker.setOnClickListener( this);
        btnTimePicker.setOnClickListener( this);



        btn_bkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Appointment").document().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        String Patient_name = Pname.getText().toString();
                        String Patient_dob = edt_dob.getText().toString();
                        String appoint_date = txtDate.getText().toString();
                        String appoint_time = txtTime.getText().toString();

                        Map<String,Object> user = new HashMap<>();
                        user.put("Patient_name",Patient_name );
                        user.put("Patient_DOB",Patient_dob );
                        user.put("Date",appoint_date );
                        user.put("Uid", uid);
                        user.put("Time", appoint_time );
                        DocumentReference documentReference = db.collection("Appointment").document();

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(BookingAppointment.this, "saved", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent( BookingAppointment.this, PatientTab.class );
                        startActivity( intent );
                    }
                });


                    }
                });

            }
        });

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }





}
