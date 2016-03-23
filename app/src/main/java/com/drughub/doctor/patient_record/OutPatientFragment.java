package com.drughub.doctor.patient_record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

import java.util.ArrayList;

/**
 * Created by Deepak on 3/22/2016.
 */
public class OutPatientFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle("Out Patient Prescription");
        ((BaseActivity) getActivity()).setBackButton(true);

        final View view = inflater.inflate(R.layout.fragment_patient_record, container, false);

        EditText editText = (EditText) view.findViewById(R.id.patientRecordSearch);
        editText.setHint("Docter Name | Clinic");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.patient_records_list);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        ArrayList<OutPatientPrescription> outPatientPrescriptions = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            outPatientPrescriptions.add(new OutPatientPrescription("Amar " + i, "Fever", "Suriya Nursing Home", "Date : 24th Oct 2015"));
        }

        OutPatientAdapter adapter = new OutPatientAdapter(getActivity(), outPatientPrescriptions);
        recyclerView.setAdapter(adapter);


        return view;


    }
}
