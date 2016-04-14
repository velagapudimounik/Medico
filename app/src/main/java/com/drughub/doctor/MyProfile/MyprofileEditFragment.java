package com.drughub.doctor.MyProfile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.drughub.doctor.R;
import com.drughub.doctor.model.Address;
import com.drughub.doctor.model.ServiceProvider;
import com.drughub.doctor.network.Globals;

import io.realm.Realm;

public class MyprofileEditFragment extends Fragment implements View.OnClickListener {
    private Realm realm;
    ServiceProvider serviceProvider;
    private Spinner spinnerCountry;
    private Spinner spinnerState;
    private Spinner spinnerDistrict;
    private Spinner spinnerTownorCity;


    @Override
    public void onStart() {
        super.onStart();
        // Create Realm instance for the UI thread
        realm = Realm.getDefaultInstance();
        serviceProvider = realm.where(ServiceProvider.class).findFirst();
        if (serviceProvider != null) {
            getEditFirstName().setText(serviceProvider.getFirstName());
            getEditMiddleName().setText(serviceProvider.getMiddleName());
            getEditLastName().setText(serviceProvider.getLastName());
            if (serviceProvider.getAddress() != null) {
                getEditBuildingName().setText(serviceProvider.getAddress().getBuildingName());
                getEditDoorNo().setText(serviceProvider.getAddress().getDoorNumber());
                getEditStreetName().setText(serviceProvider.getAddress().getStreetName());
                getEditColonyName().setText(serviceProvider.getAddress().getColonyName());
                getEditPincode().setText(serviceProvider.getAddress().getPostalCode());
            }
            getEditEmailAddress().setText(serviceProvider.getEmailId());
            getEditMobile().setText(serviceProvider.getMobile());
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.myprofile_edit_fragment, null);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerCountry = (Spinner) view.findViewById(R.id.spinnerCountry);
        spinnerState = (Spinner) view.findViewById(R.id.spinnerState);
        spinnerDistrict = (Spinner) view.findViewById(R.id.spinnerDistrict);
        spinnerTownorCity = (Spinner) view.findViewById(R.id.spinnerTownorCity);
        view.findViewById(R.id.buttonUpdate).setOnClickListener(this);
    }

    private EditText getEditFirstName() {
        return (EditText) getView().findViewById(R.id.editFirstName);
    }

    private EditText getEditMiddleName() {
        return (EditText) getView().findViewById(R.id.editMiddleName);
    }

    private EditText getEditLastName() {
        return (EditText) getView().findViewById(R.id.editLastName);
    }

    private EditText getEditDesignation() {
        return (EditText) getView().findViewById(R.id.editDesignation);
    }

    private EditText getEditSpecialization() {
        return (EditText) getView().findViewById(R.id.editSpecialization);
    }

    private EditText getEditYearsofExperience() {
        return (EditText) getView().findViewById(R.id.editYearsofExperience);
    }

    private EditText getEditBuildingName() {
        return (EditText) getView().findViewById(R.id.editBuildingName);
    }

    private EditText getEditDoorNo() {
        return (EditText) getView().findViewById(R.id.editDoorNo);
    }

    private EditText getEditStreetName() {
        return (EditText) getView().findViewById(R.id.editStreetName);
    }

    private EditText getEditColonyName() {
        return (EditText) getView().findViewById(R.id.editColonyName);
    }

    private EditText getEditPincode() {
        return (EditText) getView().findViewById(R.id.editPincode);
    }

    private EditText getEditLandMark() {
        return (EditText) getView().findViewById(R.id.editLandMark);
    }

    private EditText getEditEmailAddress() {
        return (EditText) getView().findViewById(R.id.editEmailAddress);
    }

    private EditText getEditMobile() {
        return (EditText) getView().findViewById(R.id.editMobile);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonUpdate:
                updateProfile();
                break;
        }
    }

    private void updateProfile() {
        realm.beginTransaction();
        serviceProvider.setMobile(getEditMobile().getText().toString());
        serviceProvider.setFirstName(getEditFirstName().getText().toString());
        serviceProvider.setLastName(getEditLastName().getText().toString());
        if (serviceProvider.getAddress() == null)
            serviceProvider.setAddress(realm.createObject(Address.class));
        serviceProvider.getAddress().setBuildingName(getEditBuildingName().getText().toString());
        serviceProvider.getAddress().setDoorNumber(getEditDoorNo().getText().toString());
        serviceProvider.getAddress().setStreetName(getEditStreetName().getText().toString());
        serviceProvider.getAddress().setColonyName(getEditColonyName().getText().toString());
        serviceProvider.getAddress().setPostalCode(getEditPincode().getText().toString());
        serviceProvider.getAddress().setLandmark(getEditLandMark().getText().toString());
        serviceProvider.setEmailId(getEditEmailAddress().getText().toString());
        serviceProvider.setMobile(getEditMobile().getText().toString());
        realm.commitTransaction();

        serviceProvider.UpdateServiceProvider(getActivity(), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.v("update SP response", result);

            }

            @Override
            public void onFail(String result) {
                Log.v("update SP fail response", result);

            }
        });
    }

}

