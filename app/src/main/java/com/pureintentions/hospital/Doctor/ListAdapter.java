package com.pureintentions.hospital.Doctor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pureintentions.hospital.R;

public class ListAdapter extends FirestoreRecyclerAdapter<List, ListAdapter.ListHolder> {

    private OnItemClickListener listener;

public ListAdapter(@NonNull FirestoreRecyclerOptions<List> options) {

            super( options );
        }
        @Override
         protected void onBindViewHolder(@NonNull ListHolder listHolder, int i, @NonNull List list) {
        listHolder.nameList1.setText( list.getName() );
        listHolder.ageList1.setText( list.getPhone());

        }
        @NonNull
        @Override
            public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.list_item,parent,false );
        return new ListHolder( v );
        }


        class ListHolder extends RecyclerView.ViewHolder{

        TextView nameList1;
        TextView ageList1;
        public ListHolder(@NonNull final View itemView) {

        super( itemView );
        nameList1=itemView.findViewById( R.id.nameList );
        ageList1=itemView.findViewById( R.id.ageList );


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION && listener!=null){
                    listener.onItemClick(getSnapshots().getSnapshot(position),position);
                }
            }
        });
    }
    }
          public  interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot,int position);

    }
        public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;

}
}