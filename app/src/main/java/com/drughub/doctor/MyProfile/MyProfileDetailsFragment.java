package com.drughub.doctor.MyProfile;


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

    private TextView doctorName;
    private TextView qualification;
    private TextView experience;
    private TextView adressline1;
    private TextView adressline2;
    private TextView email;
    private TextView mobile;

    public TextView getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TextView specialization) {
        this.specialization = specialization;
    }

    public TextView getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(TextView yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    private TextView specialization;
    private TextView yearsOfExperience;


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
        adressline1 = (TextView) view.findViewById(R.id.textadressLine1);
        adressline2 = (TextView) view.findViewById(R.id.textadressLine2);
        email = (TextView) view.findViewById(R.id.email);
        yearsOfExperience=(TextView)view.findViewById(R.id.experience);
        mobile = (TextView) view.findViewById(R.id.mobile);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Create Realm instance for the UI thread
        realm = Realm.getDefaultInstance();
        serviceProvider = realm.where(ServiceProvider.class).findFirst();
        if (serviceProvider != null) {
            if (serviceProvider.getFirstName()!=null)
            doctorName.setText(serviceProvider.getFirstName());
            else
            doctorName.setText("");
            if (serviceProvider.getMiddleName()!=null)
                doctorName.append(serviceProvider.getMiddleName());
            else
            doctorName.append("");
            if (serviceProvider.getLastName()!=null)
                doctorName.append(serviceProvider.getLastName());
            else
            doctorName.append("");
            if (serviceProvider.getQualificationList()!=null && serviceProvider.getQualificationList().size()>0){
                qualification.setText(serviceProvider.getQualificationList().get(0).getValue());
            }
            else
            qualification.setText("");
            if (serviceProvider.getSpecializationList()!=null && serviceProvider.getSpecializationList().size()>0){
                qualification.append(serviceProvider.getSpecializationList().get(0).getValue());
            }
            else
            qualification.append("");
// yearsOfExperience.setText(serviceProvider.ge);
            if (serviceProvider.getAddress() != null) {
                if (serviceProvider.getAddress().getBuildingName()!=null){
                    adressline1.setText(serviceProvider.getAddress().getBuildingName());
                }
                else
                adressline1.setText("");
                if (serviceProvider.getAddress().getDoorNumber()!=null)
                    adressline1.append(serviceProvider.getAddress().getDoorNumber());
                else
                adressline1.append("");
                if (serviceProvider.getAddress().getStreetName()!=null)
                    adressline1.append(serviceProvider.getAddress().getStreetName());
                else
                adressline1.append("");
                if (serviceProvider.getAddress().getColonyName()!=null)
                    adressline1.append(serviceProvider.getAddress().getColonyName());
                else
                adressline1.append("");
                adressline2.setText(serviceProvider.getAddress().getCity()+serviceProvider.getAddress().getPostalCode());
            }else {
                realm.beginTransaction();
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
