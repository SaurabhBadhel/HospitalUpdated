package com.pureintentions.hospital.Patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pureintentions.hospital.R;

public class FragPatientPrescription extends Fragment {


    private TextView age,bp,weight,height;
    private  TextView medc1,medc1_time,qty1;
    private  TextView medc2,medc2_time,qty2;
    private  TextView medc3,medc3_time,qty3;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.frag_patient_prescription, container, false);
        age = view.findViewById(R.id.age);
        bp = view.findViewById(R.id.bp);
        weight = view.findViewById(R.id.weight);
        height = view.findViewById(R.id.height);

        medc1 = view.findViewById(R.id.medc1);
        medc1_time = view.findViewById(R.id.medc1_time);
        qty1 = view.findViewById(R.id.qty1);

        medc2 = view.findViewById(R.id.medc2);
        medc2_time = view.findViewById(R.id.medc2_time);
        qty2 = view.findViewById(R.id.qty2);

        medc3 = view.findViewById(R.id.medc3);
        medc3_time = view.findViewById(R.id.medc3_time);
        qty3 = view.findViewById(R.id.qty3);
        return view;
    }

}
