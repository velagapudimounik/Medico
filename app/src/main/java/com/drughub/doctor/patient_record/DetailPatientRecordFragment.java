package com.drughub.doctor.patient_record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

/**
 * Created by Deepak on 3/22/2016.
 */
public class DetailPatientRecordFragment extends Fragment implements View.OnClickListener {

    RelativeLayout vaccinationLayout, outpatientLayout, diagnosticLayout, hospitalizationLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Patient Records");
        ((BaseActivity) getActivity()).setBackButton(true);

        final View view = inflater.inflate(R.layout.fragment_patient_details, container, false);

        vaccinationLayout = (RelativeLayout) view.findViewById(R.id.vaccinationLayout);
        outpatientLayout = (RelativeLayout) view.findViewById(R.id.outpatientLayout);
        diagnosticLayout = (RelativeLayout) view.findViewById(R.id.diagnosticLayout);
        hospitalizationLayout = (RelativeLayout) view.findViewById(R.id.hospitalizationLayout);


        vaccinationLayout.setOnClickListener(this);
        outpatientLayout.setOnClickListener(this);
        diagnosticLayout.setOnClickListener(this);
        hospitalizationLayout.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.vaccinationLayout:
//                changeFragment(new PatientRecordFragment());
                break;
            case R.id.outpatientLayout:
                changeFragment(new OutPatientFragment());
                break;
            case R.id.hospitalizationLayout:
//                changeFragment(new PatientRecordFragment());
                break;
            case R.id.diagnosticLayout:
                changeFragment(new DiagnosticRecordFragment());
                break;
            default:
                break;
        }

    }

    private void changeFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.activity_content, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }
}
