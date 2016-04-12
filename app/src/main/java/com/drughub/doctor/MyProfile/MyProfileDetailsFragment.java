package com.drughub.doctor.MyProfile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drughub.doctor.R;
import com.drughub.doctor.model.ServiceProvider;

import io.realm.Realm;

public class MyProfileDetailsFragment extends Fragment {

    ServiceProvider serviceProvider;
    private Realm realm;

    private TextView doctorName;
    private TextView qualification;
    private TextView experience;
    private TextView city;
    private TextView country;
    private TextView email;
    private TextView mobile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myprofile_details_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doctorName = (TextView) view.findViewById(R.id.doctor_name);
        qualification = (TextView) view.findViewById(R.id.qualification);
        experience = (TextView) view.findViewById(R.id.experience);
        city = (TextView) view.findViewById(R.id.city);
        country = (TextView) view.findViewById(R.id.country);
        email = (TextView) view.findViewById(R.id.email);
        mobile = (TextView) view.findViewById(R.id.mobile);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Create Realm instance for the UI thread
        realm = Realm.getDefaultInstance();
        serviceProvider = realm.where(ServiceProvider.class).findFirst();
        if (serviceProvider != null) {

        }

    }
}
