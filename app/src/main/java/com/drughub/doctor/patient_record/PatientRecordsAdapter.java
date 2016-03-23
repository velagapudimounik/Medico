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
public class PatientRecordsAdapter extends RecyclerView.Adapter<PatientRecordsAdapter.DataObjectHolder> {
    Context context;
    ArrayList<PatientRecord> patientRecords;

    public PatientRecordsAdapter(Context context, ArrayList<PatientRecord> patientRecords) {
        this.context = context;
        this.patientRecords = patientRecords;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_patient_record, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        PatientRecord patientRecord = patientRecords.get(position);
        holder.name.setText(patientRecord.getName());
        holder.dob.setText(patientRecord.getDob());
        holder.records.setText(patientRecord.getRecords());
    }

    @Override
    public int getItemCount() {
        return patientRecords.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, dob, records;

        public DataObjectHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.patient_name);
            dob = (TextView) itemView.findViewById(R.id.patient_dob);
            records = (TextView) itemView.findViewById(R.id.records_count);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PatientRecordActivity activity = (PatientRecordActivity)context;
            activity.changeFragment(new DetailPatientRecordFragment());
        }
    }
}
