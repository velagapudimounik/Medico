package com.drughub.doctor.patient_record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
public class DetailOutPatientRecordFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Out Patient Prescription");
        ((BaseActivity) getActivity()).setBackButton(true);

        final View view = inflater.inflate(R.layout.fragment_out_patient_details, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.out_patient_grid);
        recyclerView.hasFixedSize();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(gridLayoutManager);

        ArrayList<String> image_urls = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            image_urls.add("" + i);
        }
        image_urls.add("dummy");

        OutPatientDetailsAdapter adapter = new OutPatientDetailsAdapter(getActivity(), image_urls);
        recyclerView.setAdapter(adapter);


        return view;

    }
}
