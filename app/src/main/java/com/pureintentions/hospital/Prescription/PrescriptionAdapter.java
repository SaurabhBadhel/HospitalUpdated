package com.pureintentions.hospital.Prescription;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pureintentions.hospital.Doctor.ListAdapter;
import com.pureintentions.hospital.R;

public class PrescriptionAdapter extends FirestoreRecyclerAdapter<DateModel, PrescriptionAdapter.PrescriptionHolder> {
    private PrescriptionAdapter.OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PrescriptionAdapter(@NonNull FirestoreRecyclerOptions<DateModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PrescriptionHolder holder, int position, @NonNull DateModel model) {
        holder.prescription_name.setText(model.getName());
        holder.prescription_date.setText(model.getDate());

    }

    @NonNull
    @Override
    public PrescriptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.prescription_list,parent,false );
        return new  PrescriptionHolder(v);
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(ListAdapter.OnItemClickListener listener){
        this.listener= (OnItemClickListener) listener;

    }

    public class PrescriptionHolder extends RecyclerView.ViewHolder {
        TextView prescription_name,prescription_date;

        public PrescriptionHolder(@NonNull View itemView) {
            super(itemView);
            prescription_name=itemView.findViewById(R.id.prescription_name);
            prescription_date=itemView.findViewById(R.id.prescription_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION && listener!=null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }



    }




}
