package com.drughub.doctor.MyProfile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drughub.doctor.R;


public class DeleteClinicDailogBox extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater factory=LayoutInflater.from(this.getActivity());
        //View view=inflater.inflate()
        final View deleteDailogview=factory.inflate(R.layout.myprofile_removeclinicdialogbox, null);
        final AlertDialog deleteDailog=new AlertDialog.Builder(this.getActivity()).create();
        deleteDailog.setView(deleteDailogview);
        deleteDailogview.findViewById(R.id.yesbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDailog.dismiss();
            }
        });
        deleteDailogview.findViewById(R.id.Nobutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDailog.dismiss();
            }
        });
        deleteDailog.show();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
