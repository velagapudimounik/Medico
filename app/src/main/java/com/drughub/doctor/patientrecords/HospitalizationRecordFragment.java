package com.drughub.doctor.patientrecords;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;

public class HospitalizationRecordFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.hospitalization_reports));
        ((BaseActivity) getActivity()).setBackButton(true);
        final View view = inflater.inflate(R.layout.patient_record_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.patient_records_list);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        ArrayList<PatientRecord> patientRecords = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            patientRecords.add(new PatientRecord("Amar " + i, "D.O.B : 24th Nov 1998", "16"));
        }

        HospitalizationRecordAdpater adapter = new HospitalizationRecordAdpater(getActivity(), patientRecords);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
class HospitalizationRecordAdpater extends RecyclerView.Adapter<HospitalizationRecordAdpater.DataHolder> {
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
            activity.changeFragment(new DetailHospitalizationRecordFragment());
        }
    }
}
