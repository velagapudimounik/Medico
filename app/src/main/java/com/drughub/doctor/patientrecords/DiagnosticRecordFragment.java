package com.drughub.doctor.patientrecords;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;

public class DiagnosticRecordFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle(getResources().getString(R.string.diagnostic_report));
        ((BaseActivity) getActivity()).setBackButton(true);

        final View view = inflater.inflate(R.layout.patient_record_fragment, container, false);

        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.testTabs);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
        frameLayout.setVisibility(View.VISIBLE);


        RadioButton urineTests = (RadioButton) view.findViewById(R.id.radioButton1);
        RadioButton bloodTests = (RadioButton) view.findViewById(R.id.radioButton2);
        RadioButton radiologicalTests = (RadioButton) view.findViewById(R.id.radioButton3);

//        urineTests.setOnCheckedChangeListener(this);
//        bloodTests.setOnCheckedChangeListener(this);
//        radiologicalTests.setOnCheckedChangeListener(this);
//        Typeface ttf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/drughub-mobile-doctor.ttf");
//        urineTests.setTypeface(ttf);
//        bloodTests.setTypeface(ttf);
//        radiologicalTests.setTypeface(ttf);


        EditText editText = (EditText) view.findViewById(R.id.patientRecordSearch);
        editText.setHint(getResources().getString(R.string.hintDiagnosticSearch));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.patient_records_list);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));


        ArrayList<OutPatientPrescription> outPatientPrescriptions = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            outPatientPrescriptions.add(new OutPatientPrescription("Amar " + i, "Fever", "Suriya Nursing Home", "Date : 24th Oct 2015"));
        }

        OutPatientAdapter adapter = new OutPatientAdapter(getActivity(), outPatientPrescriptions);
        recyclerView.setAdapter(adapter);


        return view;


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.radioButton1:
//                bloodLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.colorAccent));
//                radiologicalLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.colorAccent));
                break;
            case R.id.radioButton2:
//                urineLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.colorAccent));
//                radiologicalLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.colorAccent));
                break;
            case R.id.radioButton3:
//                bloodLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.colorAccent));
//                urineLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.colorAccent));
                break;

        }

    }
}

