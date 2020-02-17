package com.pureintentions.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pureintentions.hospital.Doctor.History;
import com.pureintentions.hospital.Doctor.HistoryAdapter;

import static com.pureintentions.hospital.DoctorSearchScreen.Name;
import static com.pureintentions.hospital.DoctorSearchScreen.Uid;


public class FragHistory extends Fragment{

    private HistoryAdapter adapter;
    public static final String Name="Name";
    public static final String Age="Age";
    public static final String Height="Height";
    public static final String Weight="Weight";
    public static final String Uid="Uid";
    public static final String Medi="Medi";
    public static final String MediTime="MediTime";
    public static final String Date="Date";
    public static final String Remark="Remark";
    public static final String Bg="Bg";
    public static final String Bp="Bp";
    public static final String Gender="Gender";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.tab_history, container, false);

        Intent intent1=getActivity().getIntent();
        String UID= intent1.getStringExtra(Uid);
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference reference=db.collection( "Prescription" );
        Query query=reference.whereEqualTo("Uid",UID).orderBy("Date",Query.Direction.ASCENDING);
        Query query1=reference.orderBy( "name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<History> options=new FirestoreRecyclerOptions.Builder<History>().setQuery( query,History.class ).build();
        adapter=new HistoryAdapter( options );
        RecyclerView recyclerView= view.findViewById(R.id.history);
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()) );
        recyclerView.setAdapter( adapter);

        adapter.setOnItemClickListener(new HistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                History history=documentSnapshot.toObject(History.class);
                String id=documentSnapshot.getId();
                String name=history.getName();
                String age=history.getAge();
                String bp=history.getBp();
                String date=history.getDate();
                String remark=history.getRemark();
                String bg=history.getBlood_Group();
                String gender=history.getGender();
                String height=history.getHeight();
                String weight=history.getWeight();
                String medicine=history.getMedicine1();
                String medicineTime=history.getMedicine_Time1();


                Intent intent=new Intent(getContext(),DrPrescriptionHistory.class);
                intent.putExtra(Uid,id);
                intent.putExtra(Name,name);
                intent.putExtra(Age,age);
                intent.putExtra(Date,date);
                intent.putExtra(Bp,bp);
                intent.putExtra(Bg,bg);
                intent.putExtra(Remark,remark);
                intent.putExtra(Gender,gender);
                intent.putExtra(Height,height);
                intent.putExtra(Weight,weight);
                intent.putExtra(Medi,medicine);
                intent.putExtra(MediTime,medicineTime);
                startActivity(intent);


            }
        });


       return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        //      adapter1.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
            adapter.stopListening();
        //  adapter1.stopListening();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
  }
