package com.drughub.doctor.MyProfile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drughub.doctor.R;
import com.drughub.doctor.model.ClinicCalendar;
import com.drughub.doctor.model.ServiceProvider;

public class MyProfileDetailsFragment extends Fragment {

    ServiceProvider serviceProvider = new ServiceProvider();
    ClinicCalendar clinicCalendar = new ClinicCalendar();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myprofile_details_fragment,container,false);

        TextView doctor_name = (TextView) view.findViewById(R.id.doctor_name);
        TextView qualification = (TextView) view.findViewById(R.id.qualification);
        TextView experience = (TextView) view.findViewById(R.id.experience);
        TextView city = (TextView) view.findViewById(R.id.city);
        TextView country = (TextView) view.findViewById(R.id.country);
        TextView email = (TextView) view.findViewById(R.id.email);
        TextView mobile = (TextView) view.findViewById(R.id.mobile);


        doctor_name.setText(serviceProvider.getFirstName() + " " + serviceProvider.getLastName());
        qualification.setText(serviceProvider.getQualification());
        experience.setText(serviceProvider.getExperienceInYears()+" year(s) experience");
        city.setText(serviceProvider.getCity());
        country.setText(serviceProvider.getCountry());
        email.setText(serviceProvider.getEmail());
        mobile.setText(serviceProvider.getPhone());

        Log.i("Service_", serviceProvider.toUpdateServiceProvider());


        return view;
    }
}
