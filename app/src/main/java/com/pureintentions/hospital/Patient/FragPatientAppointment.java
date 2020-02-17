package com.pureintentions.hospital.Patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pureintentions.hospital.R;

public class FragPatientAppointment extends Fragment  {

    TimePicker timepicker;
    CalendarView calendarView;
    TextView textView10,textView9;
    EditText Pname,edt_dob;
    CheckBox chkbx_call;
    Button btn_bkd;
    RecyclerView recyclerView2;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      super.onCreateView(inflater, container, savedInstanceState);

        final View view =  inflater.inflate(R.layout.frag_booked_appoint,container,false);
       //timepicker= view.findViewById(R.id.timepicker);

        textView10= view.findViewById(R.id.textView10);
        textView9= view.findViewById(R.id.textView9);
        Pname= view.findViewById(R.id.Pname);
        edt_dob= view.findViewById(R.id.edt_dob);
        chkbx_call= view.findViewById(R.id.chkbx_call);
        btn_bkd= view.findViewById(R.id.btn_bkd);


        recyclerView2=view.findViewById(R.id.recyclerView2);






        FloatingActionButton fab = view. findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inDoctor = new Intent(v.getContext(), BookingAppointment.class);
                 startActivity(inDoctor);

            }
        });













   /*     btn_bkd.setOnClickListener(new View.OnClickListener() {
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
                            Toast.makeText(FragPatientAppointment.this, "saved", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent( BookedAppointment.this,PatientHomeScreen.class );
                            startActivity( intent );
                        }
                    });


                }
            });

        }
    });

        */
        return view;
    }

}
