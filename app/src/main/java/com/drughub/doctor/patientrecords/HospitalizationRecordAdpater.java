package com.drughub.doctor.patientrecords;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drughub.doctor.R;

import java.util.ArrayList;


public class HospitalizationRecordAdpater extends RecyclerView.Adapter<HospitalizationRecordAdpater.DataHolder> {
    Context context;
    ArrayList<PatientRecord> patientRecords;

    public HospitalizationRecordAdpater(Context context, ArrayList<PatientRecord> patientRecords) {
        this.context = context;
        this.patientRecords = patientRecords;
    }


    @Override
    public HospitalizationRecordAdpater.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_record_out_patient_item, parent, false);

        return new DataHolder(view);

    }

    @Override
    public void onBindViewHolder(HospitalizationRecordAdpater.DataHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return patientRecords.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, dob, records;

        public DataHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.patient_name);
            dob = (TextView) itemView.findViewById(R.id.patient_dob);
            records = (TextView) itemView.findViewById(R.id.records_count);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PatientRecordActivity activity = (PatientRecordActivity) context;
            activity.changeFragment(new DetailOutPatientRecordFragment());
        }
    }
}
