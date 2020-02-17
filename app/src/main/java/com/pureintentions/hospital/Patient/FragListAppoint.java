package com.pureintentions.hospital.Patient;

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


public class FragListAppoint extends Fragment {


    AppointmentAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.frag_appointment_history, container, false);

            Intent intent1=getActivity().getIntent();
            String UID= FirebaseAuth.getInstance().getUid();
            FirebaseFirestore db=FirebaseFirestore.getInstance();
            CollectionReference reference=db.collection( "Appointment" );
            Query query=reference.whereEqualTo("Uid",UID);
            Query query1=reference.orderBy( "Date", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<Appointment> options=new FirestoreRecyclerOptions.Builder<Appointment>().setQuery( query1, Appointment.class ).build();
            adapter=new AppointmentAdapter( options );
            RecyclerView recyclerView=view.findViewById( R.id.appointhistory );
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
