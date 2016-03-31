package com.drughub.doctor.MyProfile;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.drughub.doctor.R;

public class MyProfileChangePasswordFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myprofile_changepassword_fragment, container, false);

        EditText currentPassword = (EditText)view.findViewById(R.id.currentPassword);
        currentPassword.setTypeface(Typeface.DEFAULT);
        EditText newPassword = (EditText)view.findViewById(R.id.newPassword);
        newPassword.setTypeface(Typeface.DEFAULT);
        EditText confirmPassword = (EditText)view.findViewById(R.id.confirmPassword);
        confirmPassword.setTypeface(Typeface.DEFAULT);

        return view;
    }
}
