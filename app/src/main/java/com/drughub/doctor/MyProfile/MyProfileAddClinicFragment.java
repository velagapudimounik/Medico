package com.drughub.doctor.MyProfile;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;


public class MyProfileAddClinicFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.add_clinic_title));
        return inflater.inflate(R.layout.myprofile_addclinic_dailogbox,container,false);
    }

}
