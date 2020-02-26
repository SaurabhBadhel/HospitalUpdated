package com.pureintentions.hospital;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
import com.pureintentions.hospital.Doctor.HistoryAdapter;
import com.pureintentions.hospital.Doctor.Medicine;
import com.pureintentions.hospital.Doctor.MedicineAdapter;
import com.pureintentions.hospital.Doctor.PdfDocumentAdapter;

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

import static com.pureintentions.hospital.AddMedicine.Day;
import static com.pureintentions.hospital.AddMedicine.M;
import static com.pureintentions.hospital.DoctorSearchScreen.Name;
import static com.pureintentions.hospital.DoctorSearchScreen.Uid;

public class FragPrescription extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reference = db.collection("Prescription");
    private MedicineAdapter adapter;
    ArrayList<Medicine>medicine=new ArrayList<>();
    ArrayList<String> selection1 = new ArrayList<String>();
    ArrayList<String> selection2 = new ArrayList<String>();
    ArrayList<String> selection3 = new ArrayList<String>();
    EditText medName1, medName2, medName3, age, height, weight, day1, day2, day3, bp, patientName;
    TextView date, remark1, cliName;
    ImageView remark;
    Button sendPrescription, btnPrint,addMedi;
    FirebaseFirestore Store;
    Spinner bgSpin,genderSpin;
    private  ListView medList;
    public static final String TAG = "TAG";

    String Text;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.tab_prescription, container, false);
        //  return inflater.inflate(R.layout.tab_prescription,container,false);

        addMedi=view.findViewById(R.id.addMedi);
        date = view.findViewById(R.id.date);
        patientName = view.findViewById(R.id.patientName);
       // medName1=view.findViewById( R.id.medName1 );
        //medName2=findViewById( R.id.medName2 );
        //medName3=findViewById( R.id.medName3 );
        //day1=view.findViewById( R.id.duration1 );
        //day2=findViewById( R.id.Duration2 );
        //day3=findViewById( R.id.Duration3 );
        bgSpin=view.findViewById(R.id.spn_bg);
        genderSpin=view.findViewById(R.id.spn_gndr);
        remark = view.findViewById(R.id.remark);
        remark1 = view.findViewById(R.id.remark1);
        age = view.findViewById(R.id.age);
        bp = view.findViewById(R.id.bp);
        height = view.findViewById(R.id.height);
        weight = view.findViewById(R.id.weight);
        cliName = view.findViewById(R.id.cliName);
        btnPrint = view.findViewById(R.id.btnPrint);
        sendPrescription = view.findViewById(R.id.sendPrescription);
        Store = FirebaseFirestore.getInstance();
        medList=view.findViewById(R.id.listMed);


        sendPrescription.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        String Date = DateFormat.getDateInstance().format(calendar.getTime());
        date.setText(Date);
        remark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceRemark();
            }
        });

        addMedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),AddMedicine.class);
                startActivity(intent);
            }
        });

        Intent intent=getActivity().getIntent();
        String name=intent.getStringExtra(AddMedicine.Name);
        String time=intent.getStringExtra(AddMedicine.Day);
        String duration=intent.getStringExtra(AddMedicine.M);
        medicine.add(new Medicine(name,time,duration));
        adapter =new MedicineAdapter(getContext(),medicine);



        ArrayAdapter<CharSequence> adap=ArrayAdapter.createFromResource(getContext(),R.array.bloodGroup,android.R.layout.simple_spinner_item);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bgSpin.setAdapter(adap);
       // bgSpin.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence>adpGen=ArrayAdapter.createFromResource(getContext(),R.array.gen,android.R.layout.simple_spinner_item);
        adpGen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpin.setAdapter(adpGen);
        //genderSpin.setOnItemSelectedListener(this);


        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                btnPrint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // setPrescription();
                        createPDFFile(Common.getAppPath(getContext())+"prescription.pdf");

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

        return view;
   }

    private  void  voiceRemark(){

        Intent in=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        in.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        in.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        in.putExtra(RecognizerIntent.EXTRA_RESULTS,"Hey Speak Something");
        try {
            startActivityForResult(in, 1);
        }catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void listView(){


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
            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

            printPdf();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (DocumentException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void printPdf() {
        PrintManager printManager=(PrintManager)getActivity().getSystemService(Context.PRINT_SERVICE);
        try {
            PrintDocumentAdapter printDocumentAdapter=new PdfDocumentAdapter(getContext(),Common.getAppPath(getContext())+"prescription.pdf");
            printManager.print("Document",printDocumentAdapter,new PrintAttributes.Builder().build());

        }catch (Exception ex){
            Log.e("PUREINTENTIONS",""+ex.getMessage());
        }
    }



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
      final String Medicine1 = medName1.getText().toString().trim();
     //   final String Medicine2 = medName2.getText().toString().trim();
       // final String Medicine3 = medName3.getText().toString().trim();
        final String Day1 = day1.getText().toString().trim();
        //final String Day2 = day2.getText().toString().trim();
        //final String Day3 = day3.getText().toString().trim();
        final String Bp = bp.getText().toString().trim();
  //      final String Name= cliName.getText().toString().trim();
        final String Date = date.getText().toString().trim();
        final String Age = age.getText().toString().trim();
        final String Height = height.getText().toString().trim();
        final String Weight = weight.getText().toString().trim();
        final String Remark = remark1.getText().toString().trim();
        final String Bg = bgSpin.getSelectedItem().toString().trim();
        final String Gender = genderSpin.getSelectedItem().toString().trim();

        Intent intent1=getActivity().getIntent();
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
        prescription.put("Medicine1",Medicine1);
       // prescription.put("Medicine2",Medicine2);
        //prescription.put("Medicine3",Medicine3);
        prescription.put("Duration1",Day1);
        //prescription.put("Duration2",Day2);
        //prescription.put("Duration3",Day3);
        prescription.put("Date",Date);
        prescription.put("Blood_Group",Bg);
        prescription.put("Gender",Gender);
        prescription.put("Age",Age);
        prescription.put("Bp",Bp);
        prescription.put("Height",Height);
        prescription.put("Weight",Weight);
        prescription.put("Remark",Remark);
        prescription.put("Medicine_Time1",finalSelection1);
     //   prescription.put("Medicine_Time2",finalSelection2);
       // prescription.put("Medicine_Time3",finalSelection3);
        prescription.put("Uid",UID);

        documentReference.set(prescription).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: Prescription is send");
                //Toast.makeText(FragPrescription.this, "Successsss", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: " + e.toString());
            }
        });
        startActivity(new Intent(getActivity().getApplicationContext(),DoctorSearchScreen.class));
    }

/*
    public void selectItem1(View v) {
        boolean checked=((Switch)v).isChecked();
        switch (v.getId())
        {
            case R.id.checkMor1:
                if (checked){
                    selection1.add("Mor");}
                else{
                    selection1.remove( "Mor" );
                }
                break;

            case R.id.checkAft1:
                if (checked){
                    selection1.add("After");}
                else{
                    selection1.remove( "After" );
                }
                break;

            case R.id.checkEve1:
                if (checked){
                    selection1.add("Eve");}
                else{
                    selection1.remove( "Eve" );
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
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendPrescription:
                setPrescription();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text=parent.getItemAtPosition(position).toString();
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}