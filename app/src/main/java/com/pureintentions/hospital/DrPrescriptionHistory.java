package com.pureintentions.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import static com.pureintentions.hospital.DoctorSearchScreen.Name;
import static com.pureintentions.hospital.DoctorSearchScreen.Uid;
import static com.pureintentions.hospital.FragHistory.Age;
import static com.pureintentions.hospital.FragHistory.Bg;
import static com.pureintentions.hospital.FragHistory.Bp;
import static com.pureintentions.hospital.FragHistory.Gender;
import static com.pureintentions.hospital.FragHistory.Height;
import static com.pureintentions.hospital.FragHistory.Remark;
import static com.pureintentions.hospital.FragHistory.Weight;

public class DrPrescriptionHistory extends AppCompatActivity {

    TextView age,weight,gender,bp,bg,height,remar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr_prescription_history);

        Intent intent=getIntent();
       // String NAME=intent.getStringExtra(Name);
        String ID=intent.getStringExtra(Uid);
        intent.putExtra("UID",ID);
        age=findViewById(R.id.age);
        weight=findViewById(R.id.weight);
        gender=findViewById(R.id.gender);
        bp=findViewById(R.id.bp);
        bg=findViewById(R.id.bg);
        height=findViewById(R.id.height);
        remar=findViewById(R.id.remar);

        String AGE=intent.getStringExtra(Age);
        String WEIGHT=intent.getStringExtra(Weight);
        String GENDER=intent.getStringExtra(Gender);
        String BP=intent.getStringExtra(Bp);
        String BG=intent.getStringExtra(Bg);
        String HEIGHT=intent.getStringExtra(Height);
        String REMARK=intent.getStringExtra(Remark);

        age.setText(AGE);
        weight.setText(WEIGHT);
        gender.setText(GENDER);
        bp.setText(BP);
        bg.setText(BG);
        height.setText(HEIGHT);
        remar.setText(REMARK);


    }
}
