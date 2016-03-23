package com.drughub.doctor.patient_record;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drughub.doctor.R;

import java.util.ArrayList;

/**
 * Created by Deepak on 3/22/2016.
 */
public class OutPatientAdapter extends RecyclerView.Adapter<OutPatientAdapter.DataObjectHolder> {
    Context context;
    ArrayList<OutPatientPrescription> outPatientPrescriptions;

    public OutPatientAdapter(Context context, ArrayList<OutPatientPrescription> outPatientPrescriptions) {
        this.context = context;
        this.outPatientPrescriptions = outPatientPrescriptions;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_out_patient, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        OutPatientPrescription outPatientPrescription = outPatientPrescriptions.get(position);
        holder.name.setText(outPatientPrescription.getName() + " | " + outPatientPrescription.getDisease());
        holder.date.setText(outPatientPrescription.getHospital() + " | " + outPatientPrescription.getDate());
    }

    @Override
    public int getItemCount() {
        return outPatientPrescriptions.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, date;

        public DataObjectHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.patient_name);
            date = (TextView) itemView.findViewById(R.id.patient_dob);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PatientRecordActivity activity = (PatientRecordActivity) context;
            activity.changeFragment(new DetailOutPatientRecordFragment());

        }
    }
}
