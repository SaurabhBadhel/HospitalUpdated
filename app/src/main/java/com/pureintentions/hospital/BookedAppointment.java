package com.pureintentions.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookedAppointment extends AppCompatActivity   implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    Calendar calendar;
    CalendarView calendarView;
    CheckBox chkbx_call;
    RadioGroup rg_time1, rg_time2;
    RadioButton rb_mrng, rb_eve ;
    Button btn_bkd;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db  = FirebaseFirestore.getInstance();
    EditText Pname,edt_dob;
    String cal_date,apt_type;
    String apt_time;
    Spinner am_dd,pm_dd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_appointment);
        calendar = Calendar.getInstance();
        final CollectionReference reference = db.collection( "Appointment" );

        chkbx_call = findViewById(R.id.chkbx_call);
        rb_mrng = findViewById(R.id.rb_mrng);
        rb_eve = findViewById(R.id.rb_eve);
        Pname=findViewById(R.id.Pname);
        edt_dob=findViewById(R.id.edt_dob);
        btn_bkd=findViewById(R.id.btn_bkd);
        if (chkbx_call.isChecked()) {
            chkbx_call.setError("Medicine required");
            chkbx_call.requestFocus();
            return;
        }



        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.set(Calendar.YEAR, 2012);


        calendar.add(Calendar.DAY_OF_MONTH, +1);
        calendar.add(Calendar.YEAR, 1);


        calendarView = findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                String msg =    i2 + " - " + (i1 + 1) + " - " + i;
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                cal_date = msg;

                am_dd=findViewById(R.id.am_dd);
                List<String> categories = new ArrayList<String>();


                pm_dd=findViewById(R.id.pm_dd);


            }
        });

        btn_bkd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("Appointment").document().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        Map<String,Object> user = new HashMap<>();
                        user.put("Patient name",Pname.getText().toString());
                        user.put("Patient DOB",edt_dob.getText().toString());
                        user.put("Date",cal_date );
                        user.put("Type", apt_type);
                        user.put("Time", apt_time );
                        DocumentReference documentReference = db.collection("Appointment").document(FirebaseAuth.getInstance().getUid());

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(BookedAppointment.this, "saved", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent( BookedAppointment.this,PatientHomeScreen.class );
                                startActivity( intent );
                            }
                        });


                    }
                });

            }
        });



    }


    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.rb_mrng:
                if (checked)
                    Toast.makeText(this, "Morning Appointment is Selected", Toast.LENGTH_SHORT).show();
                apt_type="Morning";
                am_dd = (Spinner) findViewById(R.id.am_dd);
                am_dd.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                addListenerOnButton();
                apt_time= am_dd.getSelectedItem().toString();

                break;
            case R.id.rb_eve:
                if (checked)
                    Toast.makeText(this, "Evening Appointment is Selected", Toast.LENGTH_SHORT).show();
                apt_type="Evening";
                pm_dd = (Spinner) findViewById(R.id.pm_dd);
                pm_dd.setOnItemSelectedListener(new CustomOnItemSelectedListener());
                addListenerOnButton();
                apt_time= pm_dd.getSelectedItem().toString();

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(adapterView.getContext(),"OnItemSelectedListener : " + adapterView.getItemAtPosition(i).toString(),
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


    public void addListenerOnButton()
    {
        am_dd= (Spinner)findViewById(R.id.am_dd);
        pm_dd= (Spinner) findViewById(R.id.pm_dd);

    }


    private class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(parent.getContext(),  parent.getItemAtPosition(position).toString(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
