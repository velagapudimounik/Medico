package com.drughub.doctor.MyProfile.unused;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drughub.doctor.R;
import com.drughub.doctor.model.Address;
import com.drughub.doctor.model.City;
import com.drughub.doctor.model.ServiceProvider;
import com.drughub.doctor.model.State;

import io.realm.Realm;

public class MyProfileDetailsFragment extends Fragment {

    ServiceProvider serviceProvider;
    private Realm realm;

    private TextView doctorNameDetails;
    private TextView qualification;
    private TextView adressline1;
    private TextView adressline2;
    private TextView email;
    private TextView mobile;
    private TextView specialization;
    private TextView yearsOfExperience;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.myprofile_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        doctorNameDetails = (TextView) view.findViewById(R.id.doctor_name);
        qualification = (TextView) view.findViewById(R.id.qualification);
        adressline1 = (TextView) view.findViewById(R.id.textAddressLine1);
        adressline2 = (TextView) view.findViewById(R.id.textAddressLine2);
        email = (TextView) view.findViewById(R.id.email);
        yearsOfExperience=(TextView)view.findViewById(R.id.practiceStartDate);
        mobile = (TextView) view.findViewById(R.id.mobile);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Create Realm instance for the UI thread
        realm = Realm.getDefaultInstance();
        serviceProvider = realm.where(ServiceProvider.class).findFirst();
        if (serviceProvider != null) {

            doctorNameDetails.setText("");

            if (serviceProvider.getFirstName() != null)
                doctorNameDetails.setText(serviceProvider.getFirstName());

            if (serviceProvider.getMiddleName() != null)
                doctorNameDetails.append(" "+serviceProvider.getMiddleName());

            if (serviceProvider.getLastName()!=null)
                doctorNameDetails.append(" "+serviceProvider.getLastName());

            qualification.setText("");

            if (serviceProvider.getQualificationList() != null && serviceProvider.getQualificationList().size() > 0)
                qualification.setText(serviceProvider.getQualificationList().get(0).getValue());

            if (serviceProvider.getSpecializationList()!=null && serviceProvider.getSpecializationList().size()>0)
                qualification.append(", "+serviceProvider.getSpecializationList().get(0).getValue());

            if (serviceProvider.getAddress() != null) {
                adressline1.setText(serviceProvider.getAddress().getAddressLine1());
                adressline2.setText(serviceProvider.getAddress().getAddressLine2());
            } else {
                realm.beginTransaction();
                realm.allObjects(State.class).clear();
                realm.allObjects(City.class).clear();
                serviceProvider.setAddress(realm.createObject(Address.class));
                serviceProvider.getAddress().setState(realm.createObject(State.class));
                serviceProvider.getAddress().setCity(realm.createObject(City.class));
                realm.commitTransaction();
            }
            email.setText(serviceProvider.getEmailId());
            mobile.setText(serviceProvider.getMobile());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }
}
