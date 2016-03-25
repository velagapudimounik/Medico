package com.drughub.doctor.consultation;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;

public class ConsultationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.consultation_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        getActivity().setTitle(getString(R.string.consultations));

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.consultationListContainer, new ConsultationListFragment()).commit();

        final TextView addBtn = (TextView) view.findViewById(R.id.addConsultationBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ((BaseActivity)getActivity()).setBackButton(true);
            final Dialog dialog = CustomDialog.showCustomDialog((BaseActivity)getActivity(), R.layout.consultation_add_new,
                    Gravity.BOTTOM, true, false, true);

            View addBtn = dialog.findViewById(R.id.addConsultationBtn);
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            }
        });
    }
}
