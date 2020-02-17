package com.pureintentions.hospital.Prescription;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.pureintentions.hospital.R;

public class FragListPrescription extends Fragment {

    PrescriptionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View view=inflater.inflate(R.layout.frag_patient_prescription,container,false);

        Intent intent1=getActivity().getIntent();
        String UID= FirebaseAuth.getInstance().getUid();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference reference=db.collection( "Prescription" );
        Query query=reference.whereEqualTo("Uid",UID).orderBy("Date", Query.Direction.ASCENDING);
        Query query2=reference.orderBy( "Date", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<DateModel> options=new FirestoreRecyclerOptions.Builder<DateModel>().setQuery( query, DateModel.class ).build();
        adapter=new PrescriptionAdapter ( options );
        RecyclerView recyclerView=view.findViewById( R.id.pres_recycleview );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext()) );
        recyclerView.setAdapter( adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
