package com.drughub.doctor.consultation;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.drughub.doctor.R;

public class ConsultationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consultation, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        getActivity().setTitle(getString(R.string.consultations));

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.consultationListContainer, new ConsultationListFragment()).commit();

        TextView addBtn = (TextView) view.findViewById(R.id.addConsultationBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.add_consultation_db);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x88000000));
//
//                View addBtn = dialog.findViewById(R.id.addConsultationBtn);
//                addBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();

                Intent intent = new Intent(getContext(), AddConsultationActivity.class);
                startActivity(intent);
            }
        });
    }
}
