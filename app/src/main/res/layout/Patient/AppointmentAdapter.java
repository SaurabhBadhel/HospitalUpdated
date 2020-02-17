package com.pureintentions.hospital.Patient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.pureintentions.hospital.R;

public class AppointmentAdapter extends FirestoreRecyclerAdapter<Appointment, AppointmentAdapter.AppointmentHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AppointmentAdapter(@NonNull FirestoreRecyclerOptions<Appointment> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AppointmentHolder holder, int position, @NonNull Appointment model) {

        holder.app_name.setText(model.getPatient_name());
        holder.app_dob.setText(model.getPatient_DOB());
        holder.app_date.setText(model.getDate());
        holder.app_time.setText(model.getTime());

    }

    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v = LayoutInflater.from( parent.getContext() ).inflate( R.layout.appointment_list,parent,false );
        return new AppointmentHolder( v );
    }

     public class AppointmentHolder extends RecyclerView.ViewHolder {

        TextView app_name,app_dob,app_date,app_time;

        public AppointmentHolder(@NonNull View itemView) {
            super(itemView);
            app_name=itemView.findViewById(R.id.app_name);
            app_dob=itemView.findViewById(R.id.app_dob);
            app_date=itemView.findViewById(R.id.app_date);
            app_time=itemView.findViewById(R.id.app_time);
        }
    }
}
