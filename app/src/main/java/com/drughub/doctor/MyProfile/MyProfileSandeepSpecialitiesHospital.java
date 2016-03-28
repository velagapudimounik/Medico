package com.drughub.doctor.MyProfile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drughub.doctor.R;

public class MyProfileSandeepSpecialitiesHospital extends Fragment {
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Sandeep Clinic");
        return inflater.inflate(R.layout.myprofile_sandeepspecialities_fragment,container,false);
    }
}
