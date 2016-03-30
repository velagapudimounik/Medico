package com.drughub.doctor.patientrecords;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

public class HospitalizationRecordFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.hospitalization_reports));
        ((BaseActivity) getActivity()).setBackButton(true);
        final View view = inflater.inflate(R.layout.patient_record_fragment, container, false);

        return view;
    }
}
