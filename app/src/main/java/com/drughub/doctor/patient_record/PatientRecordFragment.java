package com.drughub.doctor.patient_record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

import java.util.ArrayList;

/**
 * Created by Deepak on 3/22/2016.
 */
public class PatientRecordFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Patient Records");
        ((BaseActivity)getActivity()).setBackButton(true);

        final View view = inflater.inflate(R.layout.fragment_patient_record,container,false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.patient_records_list);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<PatientRecord> patientRecords = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            patientRecords.add(new PatientRecord("Amar "+i,"D.O.B : 24th Nov 1998","16"));
        }

        PatientRecordsAdapter adapter = new PatientRecordsAdapter(getActivity(), patientRecords);
        recyclerView.setAdapter(adapter);


        return view;

    }
}
