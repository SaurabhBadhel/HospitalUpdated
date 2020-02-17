package com.pureintentions.hospital;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.pureintentions.hospital.Doctor.History;
import com.pureintentions.hospital.Doctor.HistoryAdapter;
import com.pureintentions.hospital.Doctor.common;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.pureintentions.hospital.DoctorSearchScreen.Name;
import static com.pureintentions.hospital.DoctorSearchScreen.Uid;

public class DoctorScreen extends AppCompatActivity implements View.OnClickListener{

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference reference=db.collection( "Prescription" );
    private HistoryAdapter adapter;
    ArrayList<String> selection1=new ArrayList<String>(  );
    ArrayList<String>selection2=new ArrayList<String>(  );
    ArrayList<String>selection3=new ArrayList<String>(  );
    EditText medName1,medName2,medName3,age,height,weight,day1,day2,day3,bp,patientName;
    TextView date,remark1,cliName;
    ImageView remark;
    Button sendPrescription,btnPrint;
    FirebaseFirestore Store;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent1=getIntent();
        String NAME1=intent1.getStringExtra(Name);
        getSupportActionBar().setTitle(NAME1);
        toolbar.setTitle(NAME1);
        date=findViewById( R.id.date );
        patientName=findViewById(R.id.patientName);
       /* medName1=findViewById( R.id.medName1 );
        medName2=findViewById( R.id.medName2 );
        medName3=findViewById( R.id.medName3 );
        day1=findViewById( R.id.Duration1 );
        day2=findViewById( R.id.Duration2 );
        day3=findViewById( R.id.Duration3 );*/
        remark=findViewById( R.id.remark );
        remark1=findViewById( R.id.remark1 );
        age=findViewById( R.id.age );
        bp=findViewById( R.id.bp );
        height=findViewById( R.id.height );
        weight=findViewById( R.id.weight );
        cliName=findViewById( R.id.cliName );
        btnPrint=findViewById( R.id.btnPrint );
        sendPrescription=findViewById( R.id.sendPrescription );
        Store=FirebaseFirestore.getInstance();

        sendPrescription.setOnClickListener(this);

        //  setUpRecyclerViewHistory();


        Dexter.withActivity(this).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                btnPrint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPrescription();
                        createPDFFile(common.getAppPath(DoctorScreen.this)+"prescription.pdf");

                    }
                });
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

            }
        }).check();
        Intent intent=getIntent();
        String NAME=intent.getStringExtra(Name);
        cliName.setText(NAME);
        Calendar calendar=Calendar.getInstance();
        String Date = DateFormat.getDateInstance().format(calendar.getTime());
        date.setText(Date);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder()
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceRemark();
            }
        });


    }
    private  void  voiceRemark(){

        Intent in=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        in.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        in.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        in.putExtra(RecognizerIntent.EXTRA_RESULTS,"Hey Speak Something");
        try {
            startActivityForResult(in, 1);
        }catch (ActivityNotFoundException e) {
            Toast.makeText(DoctorScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:

                if (resultCode==RESULT_OK && null!=data){
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    remark1.setText(result.get(0));
                }
                break;
        }
    }
    private void createPDFFile(String Path) {

       /* final String Medicine1 = medName1.getText().toString().trim();
        final String Medicine2 = medName2.getText().toString().trim();
        final String Medicine3 = medName3.getText().toString().trim();
        final String Day1 = day1.getText().toString().trim();
        final String Day2 = day2.getText().toString().trim();
        final String Day3 = day3.getText().toString().trim();*/
        final String Date = date.getText().toString().trim();
        final String Age = age.getText().toString().trim();
        final String Bp = bp.getText().toString().trim();
        final String Height = height.getText().toString().trim();
        final String Weight = weight.getText().toString().trim();
       // final String Name= cliName.getText().toString().trim();
        final String PatientName= patientName.getText().toString().trim();
        String finalSelection1="";
        for (String Selection1 : selection1) {
            finalSelection1 = finalSelection1 + Selection1 + "|";
        }
        String finalSelection2="";
        for (String Selection2 : selection2) {
            finalSelection2 = finalSelection2 + Selection2 + "|";
        }
        String finalSelection3="";
        for (String Selection3 : selection3) {
            finalSelection3 = finalSelection3 + Selection3 + "|";
        }
        if (new File(Path).exists())
            new File(Path).delete();
        try {
            Document document=new Document();
            PdfWriter.getInstance(document,new FileOutputStream(Path));

            document.open();
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("PUREINTENTIONS");
            document.addCreator("piyush");

            BaseColor baseColor =new BaseColor(0,153,204,255);
            float fontSize=15.0f;
            float valueFontSize=16.0f;
            BaseFont fontName=BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8",BaseFont.EMBEDDED);
            Font titleFont=new Font(fontName,25.0f,Font.NORMAL,baseColor.BLACK);
            Font valeFont=new Font(fontName,fontSize,Font.NORMAL,baseColor.BLACK);
            Font valeMedFont=new Font(fontName,valueFontSize,Font.NORMAL,baseColor.BLACK);

            addLineSeperator1(document);
            addNewItem(document,"Dr. Saurabh badhel", Element.ALIGN_CENTER,titleFont);
            addLineSeperator1(document);

            addLineSeperator(document);

            addNewItem(document,"Patient Name:-"+PatientName,Element.ALIGN_LEFT,valeFont);
            addLineSeperator(document);

            addNewItemLeftAndCenterAndRight(document,"Age:-"+Age,"Height:-"+Height,"Weight:-"+Weight,valeFont,valeFont,valeFont);
            addLineSeperator(document);


            addNewItemLeftAndCenterAndRight(document,"BP:-"+Bp,"Blood Group:-","Date:-"+Date,valeFont,valeFont,valeFont);
            addLineSeperator(document);

            addLineSpace(document);
            addNewItem(document,"MEDICINE NAME", Element.ALIGN_CENTER,titleFont);
            addLineSeperator(document);

       /*     addNewItemLeftAndCenterAndRight(document,"1:-"+Medicine1,""+finalSelection1,""+Day1,valeMedFont,valeMedFont,valeMedFont);
            addLineSeperator(document);

            addNewItemLeftAndCenterAndRight(document,"2:-"+Medicine2,""+finalSelection2,""+Day2,valeMedFont,valeMedFont,valeMedFont);
            addLineSeperator(document);

            addNewItemLeftAndCenterAndRight(document,"3:-"+Medicine3,""+finalSelection3,""+Day3,valeMedFont,valeMedFont,valeMedFont);
            addLineSeperator(document);
*/

            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);
            addLineSeperator(document);



            addNewItem(document,"Signature",Element.ALIGN_RIGHT,valeFont);
            addLineSpace(document);
            addLineSeperator1(document);

            document.close();
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

          //  printPdf();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (DocumentException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
/*
    private void printPdf() {
        PrintManager printManager=(PrintManager)getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter=new PdfDocumentAdapter(DoctorScreen.this,common.getAppPath(DoctorScreen.this)+"prescription.pdf");
            printManager.print("Document",printDocumentAdapter,new PrintAttributes.Builder().build());

        }catch (Exception ex){
            Log.e("PUREINTENTIONS",""+ex.getMessage());
        }
    }
*/
    private void addNewItemLeftAndCenterAndRight(Document document, String Left, String Center, String Right, Font leftFont, Font centerFont,Font rightFont) throws DocumentException {
        Chunk chunkLeft=new Chunk(Left,leftFont);
        Chunk chunkCenter=new Chunk(Center,centerFont);
        Chunk chunkRight=new Chunk(Right,rightFont);
        Paragraph p=new Paragraph(chunkLeft);
        p.add(new Chunk(new VerticalPositionMark()));
        p.add(chunkCenter);
        p.add(new Chunk(new VerticalPositionMark()));
        p.add(chunkRight);
        document.add(p);
    }

    private void addLineSeperator(Document document) throws DocumentException {
        LineSeparator lineSeparator=new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(255,255,255));
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);
    }
    private void addLineSeperator1(Document document) throws DocumentException {
        LineSeparator lineSeparator=new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0,0,0));
        addLineSpace(document);
        document.add(new Chunk(lineSeparator));
        addLineSpace(document);
    }

    private void addLineSpace(Document document) throws DocumentException {
        document.add(new Paragraph(""));

    }

    private void addNewItem(Document document, String text, int alignCenter, Font font) throws DocumentException {
        Chunk chunk=new Chunk(text,font);
        Paragraph paragraph=new Paragraph(chunk);
        paragraph.setAlignment(alignCenter);
        document.add(paragraph);
    }



    private void setPrescription(){
     /*   final String Medicine1 = medName1.getText().toString().trim();
        final String Medicine2 = medName2.getText().toString().trim();
        final String Medicine3 = medName3.getText().toString().trim();
        final String Day1 = day1.getText().toString().trim();
        final String Day2 = day2.getText().toString().trim();
        final String Day3 = day3.getText().toString().trim();*/
        final String Bp = bp.getText().toString().trim();
        final String Name= cliName.getText().toString().trim();
        final String Date = date.getText().toString().trim();
        final String Age = age.getText().toString().trim();
        final String Height = height.getText().toString().trim();
        final String Weight = weight.getText().toString().trim();
        final String Remark = remark1.getText().toString().trim();
        Intent intent1=getIntent();
        String UID= intent1.getStringExtra(Uid);

        if (Date.isEmpty()) {
            date.setError("Date required");
            date.requestFocus();
            return;
        }
      /*  if (Medicine1.isEmpty()) {
            medName1.setError("Medicine required");
            medName1.requestFocus();
            return;
        }
        if (Medicine2.isEmpty()) {
            medName2.setError("Medicine required");
            medName2.requestFocus();
            return;
        }
        if (Medicine3.isEmpty()) {
            medName3.setError("Medicine required");
            medName3.requestFocus();
            return;
        }
        if (Day1.isEmpty()) {
            day1.setError("Quantity required");
            day1.requestFocus();
            return;
        }
*/
        if (Age.isEmpty()) {
            age.setError("Age required");
            age.requestFocus();
            return;
        }
        if (Height.isEmpty()) {
            height.setError("Height required");
            height.requestFocus();
            return;
        }

        if (Weight.isEmpty()) {
            weight.setError("Weight required");
            weight.requestFocus();
            return;
        }
      /*  if (Remark.isEmpty()) {
            remark.setError("Remark required");
            remark.requestFocus();
            return;
        }*/
        String finalSelection1="";
        for (String Selection1 : selection1) {
            finalSelection1 = finalSelection1 + Selection1 + "|";
        }
        String finalSelection2="";
        for (String Selection2 : selection2) {
            finalSelection2 = finalSelection2 + Selection2 + "|";
        }
        String finalSelection3="";
        for (String Selection3 : selection3) {
            finalSelection3 = finalSelection3 + Selection3+ "|";
        }

        DocumentReference documentReference = Store.collection("Prescription").document();
        Map<String,Object> prescription = new HashMap<>();
        prescription.put("Name",Name);
      /*  prescription.put("Medicine1",Medicine1);
        prescription.put("Medicine2",Medicine2);
        prescription.put("Medicine3",Medicine3);
        prescription.put("Duration1",Day1);
        prescription.put("Duration2",Day2);
        prescription.put("Duration3",Day3);*/
        prescription.put("Date",Date);
        prescription.put("Age",Age);
        prescription.put("Bp",Bp);
        prescription.put("Height",Height);
        prescription.put("Weight",Weight);
        prescription.put("Remark",Remark);
        prescription.put("Medicine Time1",finalSelection1);
        prescription.put("Medicine Time2",finalSelection2);
        prescription.put("Medicine Time3",finalSelection3);
        prescription.put("Uid",UID);

        documentReference.set(prescription).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Prescription is send");
                Toast.makeText(DoctorScreen.this, "Successsss", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }
        });
        startActivity(new Intent(getApplicationContext(),DoctorSearchScreen.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.doctor_screen, menu);
        Intent intent=getIntent();
        String NAME=intent.getStringExtra(Name);
        setTitle(NAME);

        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    private void setUpRecyclerViewHistory() {
        Intent intent1=getIntent();
        String UID= intent1.getStringExtra(Uid);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference reference=db.collection( "Prescription" );
        Query query=reference.whereEqualTo("Uid",UID);
        Query query1=reference.orderBy( "Name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<History> options=new FirestoreRecyclerOptions.Builder<History>().setQuery( query1,History.class ).build();
        adapter=new HistoryAdapter( options );
        RecyclerView recyclerView=findViewById( R.id.history );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this) );
        recyclerView.setAdapter( adapter);
    }
/*
    public void selectItem1(View view) {
        boolean checked=((Switch)view).isChecked();
        switch (view.getId())
        {
            case R.id.checkMor1:
                if (checked){
                    selection1.add("Morning");}
                else{
                    selection1.remove( "Morning" );
                }
                break;

            case R.id.checkAft1:
                if (checked){
                    selection1.add("Afternoon");}
                else{
                    selection1.remove( "Afternoon" );
                }
                break;

            case R.id.checkEve1:
                if (checked){
                    selection1.add("Evening");}
                else{
                    selection1.remove( "Evening" );
                }
                break;

            case R.id.checkNight1:
                if (checked){
                    selection1.add("Night");}
                else{
                    selection1.remove( "Night" );
                }
                break;

            case R.id.checkStomatch1:
                if (checked){
                    selection1.add("Medicine before Food");}
                else{
                    selection1.remove( "Medicine before Food" );
                }
                break;
        }
    }/*
    public void selectItem2(View view) {
        boolean checked=((Switch)view).isChecked();
        switch (view.getId())
        {
            case R.id.checkMor2:
                if (checked){
                    selection2.add("Morning");}
                else{
                    selection2.remove( "Morning" );
                }
                break;

            case R.id.checkAft2:
                if (checked){
                    selection2.add("Afternoon");}
                else{
                    selection2.remove( "Afternoon" );
                }
                break;

            case R.id.checkEve2:
                if (checked){
                    selection2.add("Evening");}
                else{
                    selection2.remove( "Evening" );
                }
                break;

            case R.id.checkNight2:
                if (checked){
                    selection2.add("Night");}
                else{
                    selection2.remove( "Night" );
                }
                break;

            case R.id.checkStomatch2:
                if (checked){
                    selection2.add("Medicine before Food");}
                else{
                    selection2.remove( "Medicine before Food" );
                }
                break;
        }
    }
    public void selectItem3(View view) {
        boolean checked=((Switch)view).isChecked();
        switch (view.getId())
        {
            case R.id.checkMor3:
                if (checked){
                    selection3.add("Morning");}
                else{
                    selection3.remove( "Morning" );
                }
                break;

            case R.id.checkAft3:
                if (checked){
                    selection3.add("Afternoon");}
                else{
                    selection3.remove( "Afternoon" );
                }
                break;

            case R.id.checkEve3:
                if (checked){
                    selection3.add("Evening");}
                else{
                    selection3.remove( "Evening" );
                }
                break;

            case R.id.checkNight3:
                if (checked){
                    selection3.add("Night");}
                else{
                    selection3.remove( "Night" );
                }
                break;

            case R.id.checkStomatch3:
                if (checked){
                    selection3.add("Medicine before Food");}
                else{
                    selection3.remove( "Medicine before Food" );
                }
                break;
        }
    }*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendPrescription:
                setPrescription();
                break;
        }
    }
}