package com.pureintentions.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.load.model.Model;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pureintentions.hospital.Doctor.List;
import com.pureintentions.hospital.Doctor.ListAdapter;

public class DoctorSearchScreen extends AppCompatActivity {
    private SearchView searchView;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference reff=db.collection( "Users" );
    private ListAdapter adapter;
    public static final String Uid="Uid";
    public static final String Name="Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_search_screen);


        searchView = (SearchView) findViewById(R.id.actionSearch);
        //mSearchBtn = (ImageButton) findViewById(R.id.search_btn);

        mResultList = (RecyclerView) findViewById(R.id.list);
        mResultList.setHasFixedSize(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchText) {
                // adapter.startListening();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {
                setUpRecyclerViewList(searchText);
                adapter.startListening();
                return false;
            }
        });


    }


    private void setUpRecyclerViewList(String searchText){


        String a= searchText.toLowerCase();
        // Query query = reff.whereEqualTo("name", Query.Direction.ASCENDING).startAt(searchText).endAt(searchText + "\uf8ff");
        Query query1=reff.orderBy("name",Query.Direction.ASCENDING).startAt(a).endAt(a + "\uf8ff");
        FirestoreRecyclerOptions<List> options=new FirestoreRecyclerOptions.Builder<List>().setQuery( query1,List.class ).build();
        adapter=new ListAdapter( options );
        RecyclerView recyclerView=findViewById( R.id.list );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this));
        recyclerView.setAdapter( adapter);

        adapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                List list = documentSnapshot.toObject(List.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                String name = list.getName();
                Intent intent=new Intent(getApplicationContext(),DoctorTab.class);
                intent.putExtra(Uid,id);
                intent.putExtra(Name,name);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.doctor_screen, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_settings:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity( new Intent( this, MainActivity.class ) );
                break;
        }
        return true;
    }
}
