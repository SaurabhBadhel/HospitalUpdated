package com.pureintentions.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.util.ArrayList;

public class AddMedicine extends AppCompatActivity {

    Button addMed;
    EditText medName,days;
    ArrayList<String> selection = new ArrayList<String>();
    ArrayList<String> addList=new ArrayList<String>();

    public static final String Name="Name";
    public static final String Day="day";
    public static final String M="m";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        medName=findViewById(R.id.medName);
        addMed=findViewById(R.id.addMed);
        days=findViewById(R.id.days);
        addMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addMedicine();
            }
        });

    }


    private void addMedicine(){

        final String Medicine = medName.getText().toString().trim();
        final String Days=days.getText().toString().trim();
        String finalSelection1="";
        for (String Selection1 : selection) {
            finalSelection1 = finalSelection1 + Selection1 + "|";


         /*   addList.add(Medicine);
            addList.add(Day);
            addList.add(finalSelection1);
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(AddMedicine.this,android.R.layout.simple_list_item_1,addList);*/
            Intent intent=new Intent(AddMedicine.this,FragPrescription.class);
            intent.putExtra(Name,Medicine);
            intent.putExtra(Day,Days);
            intent.putExtra(M,finalSelection1);
         startActivity(intent);


        }
    }
    public void selectItem5(View v) {
        boolean checked=((Switch)v).isChecked();
        switch (v.getId())
        {
            case R.id.M:
                if (checked){
                    selection.add("Morning");}
                else{
                    selection.remove( "Morning" );
                }
                break;

            case R.id.A:
                if (checked){
                    selection.add("Afternoon");}
                else{
                    selection.remove( "Afternoon" );
                }
                break;

            case R.id.E:
                if (checked){
                    selection.add("Evening");}
                else{
                    selection.remove( "Evening" );
                }
                break;

            case R.id.N:
                if (checked){
                    selection.add("Night");}
                else{
                    selection.remove( "Night" );
                }
                break;

            case R.id.ES:
                if (checked){
                    selection.add("Medicine before Food");}
                else{
                    selection.remove( "Medicine before Food" );
                }
                break;
        }
    }


}
