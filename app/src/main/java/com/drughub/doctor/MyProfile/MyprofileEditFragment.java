package com.drughub.doctor.MyProfile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.drughub.doctor.MyProfile.adapters.SpinnerAdapter;
import com.drughub.doctor.R;
import com.drughub.doctor.model.Address;
import com.drughub.doctor.model.City;
import com.drughub.doctor.model.Country;
import com.drughub.doctor.model.Qualification;
import com.drughub.doctor.model.ServiceProvider;
import com.drughub.doctor.model.Specialization;
import com.drughub.doctor.model.State;
import com.drughub.doctor.model.ValueIds;
import com.drughub.doctor.network.Globals;

import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MyprofileEditFragment extends Fragment implements View.OnClickListener {
    private Realm realm;
    ServiceProvider serviceProvider;
    private Spinner spinnerCountry;
    private Spinner spinnerState;
    private Spinner spinnerDistrict;
    private Spinner spinnerTownorCity;
    private Spinner spinnerSpecialization;
    private Spinner spinnerQualification;
    private RealmResults<Country> countries;
    private RealmResults<State> states;
    private RealmResults<City> cities;
    private RealmResults<Specialization> specializations;
    private RealmResults<Qualification> qualifications;

    @Override
    public void onStart() {
        super.onStart();
        // Create Realm instance for the UI thread
        realm = Realm.getDefaultInstance();
        serviceProvider = realm.allObjects(ServiceProvider.class).first();
        if (serviceProvider != null) {
            getEditFirstName().setText(serviceProvider.getFirstName());
            getEditMiddleName().setText(serviceProvider.getMiddleName());
            getEditLastName().setText(serviceProvider.getLastName());
            getEditEmailAddress().setText(serviceProvider.getEmailId());
            getEditMobile().setText(serviceProvider.getMobile());
            if (serviceProvider.getAddress() != null) {
                getEditBuildingName().setText(serviceProvider.getAddress().getBuildingName());
                getEditDoorNo().setText(serviceProvider.getAddress().getDoorNumber());
                getEditStreetName().setText(serviceProvider.getAddress().getStreetName());
                getEditColonyName().setText(serviceProvider.getAddress().getColonyName());
                getEditPincode().setText(serviceProvider.getAddress().getPostalCode());
            }
        }

        countries = realm.where(Country.class).findAllSorted("value");
        if (countries.size() > 0) {
            ArrayList<String> values = new ArrayList<String>();
            for (Country country : countries) {
                values.add(country.getValue());
            }
            spinnerCountry.setAdapter(new SpinnerAdapter(getContext(), values));
            if (serviceProvider.getAddress().getCountry() != null) {
                int pos = values.indexOf(serviceProvider.getAddress().getCountry().getValue());
                if (pos > 0)
                    spinnerCountry.setSelection(pos);
                else
                    spinnerCountry.setSelection(0);
            }
            spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    getStates(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        getSpecializtions();
        getQualifications();

    }

    private void getSpecializtions() {
        Globals.getSpecialization(new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    realm.beginTransaction();
                    realm.allObjects(Specialization.class).clear();
                    realm.createAllFromJson(Specialization.class, (new JSONObject(result)).getJSONArray("response").toString());
                    realm.commitTransaction();
                    specializations = realm.where(Specialization.class).findAllSorted("value");
                    ArrayList<String> values = new ArrayList<String>();
                    for (Specialization specialization : specializations) {
                        values.add(specialization.getValue());
                    }
                    spinnerSpecialization.setAdapter(new SpinnerAdapter(getContext(), values));
                    int pos = values.indexOf(serviceProvider.getSpecializationList().get(0).getValue());
                    if (pos >= 0)
                        spinnerSpecialization.setSelection(pos);
                    else
                        spinnerSpecialization.setSelection(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {

            }
        });
    }

    private void getQualifications() {

        Globals.getQualification(new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    realm.beginTransaction();
                    realm.allObjects(Qualification.class).clear();
                    realm.createAllFromJson(Qualification.class, (new JSONObject(result)).getJSONArray("response").toString());
                    realm.commitTransaction();
                    qualifications = realm.where(Qualification.class).findAllSorted("value");
                    ArrayList<String> values = new ArrayList<String>();
                    for (Qualification qualification : qualifications) {
                        values.add(qualification.getValue());
                    }
                    spinnerQualification.setAdapter(new SpinnerAdapter(getContext(), values));
                    int pos = values.indexOf(serviceProvider.getQualificationList().get(0).getValue());
                    if (pos >= 0)
                        spinnerQualification.setSelection(pos);
                    else
                        spinnerQualification.setSelection(0);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {
                Log.v("FailQl=", result);

            }
        });
    }

    private void getStates(int position) {
        Globals.getState(countries.get(position).getId(), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {

                    ValueIds valueIds = new ValueIds(serviceProvider.getAddress().getState().getValueIds());
                    realm.beginTransaction();
                    realm.allObjects(State.class).clear();
                    realm.createAllFromJson(State.class, (new JSONObject(result)).getJSONArray("response").toString());
                    State stat = realm.createOrUpdateObjectFromJson(State.class, valueIds.getValueIds());
                    serviceProvider.getAddress().setState(stat);


                    realm.commitTransaction();
                    states = realm.where(State.class).findAllSorted("value");
                    if (states.size() > 0) {
                        ArrayList<String> values = new ArrayList<String>();
                        for (State state : states) {
                            values.add(state.getValue());
                        }

                        spinnerState.setAdapter(new SpinnerAdapter(getContext(), values));
                        int pos = values.indexOf(serviceProvider.getAddress().getState().getValue());
                        if (pos > 0)
                            spinnerState.setSelection(pos);
                        else
                            spinnerState.setSelection(0);

                    }
                    spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            getCities(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {

            }
        });
    }

    private void getCities(int position) {
        Globals.getCity(states.get(position).getId(), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    ValueIds valueIds = new ValueIds(serviceProvider.getAddress().getCity().getValueIds());
                    realm.beginTransaction();
                    realm.allObjects(City.class).clear();
                    realm.createAllFromJson(City.class, (new JSONObject(result)).getJSONArray("response").toString());
                    City city1 = realm.createOrUpdateObjectFromJson(City.class, valueIds.getValueIds());
                    serviceProvider.getAddress().setCity(city1);
                    realm.commitTransaction();
                    cities = realm.where(City.class).findAllSorted("value");
                    ArrayList<String> values = new ArrayList<String>();
                    for (City city : cities) {
                        values.add(city.getValue().trim());
                    }
                    spinnerTownorCity.setAdapter(new SpinnerAdapter(getContext(), values));
                    int pos = values.indexOf(serviceProvider.getAddress().getCity().getValue());
                    if (pos >= 0)
                        spinnerTownorCity.setSelection(pos);
                    else
                        spinnerTownorCity.setSelection(0);
                    spinnerTownorCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {

            }
        });
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
        spinnerQualification = (Spinner) view.findViewById(R.id.spinnerQualification);
        spinnerSpecialization = (Spinner) view.findViewById(R.id.spinnerSpecialization);
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
        serviceProvider.setMobile(getEditMobile().getText().toString().trim());
        serviceProvider.setFirstName(getEditFirstName().getText().toString().trim());
        serviceProvider.setLastName(getEditLastName().getText().toString().trim());
        serviceProvider.setEmailId(getEditEmailAddress().getText().toString().trim());
        serviceProvider.setMiddleName(getEditMiddleName().getText().toString().trim());
        RealmList<Qualification> qualificationsList = new RealmList<>();
        qualificationsList.add(qualifications.get(spinnerQualification.getSelectedItemPosition()));
        serviceProvider.setQualificationList(qualificationsList);
        RealmList<Specialization> specializationList = new RealmList<>();
        specializationList.add(specializations.get(spinnerSpecialization.getSelectedItemPosition()));
        serviceProvider.setSpecializationList(specializationList);


        if (serviceProvider.getAddress() == null)
            serviceProvider.setAddress(realm.createObject(Address.class));
        serviceProvider.getAddress().setBuildingName(getEditBuildingName().getText().toString().trim());
        serviceProvider.getAddress().setDoorNumber(getEditDoorNo().getText().toString().trim());
        serviceProvider.getAddress().setStreetName(getEditStreetName().getText().toString().trim());
        serviceProvider.getAddress().setColonyName(getEditColonyName().getText().toString().trim());
        serviceProvider.getAddress().setPostalCode(getEditPincode().getText().toString().trim());
        serviceProvider.getAddress().setLandmark(getEditLandMark().getText().toString().trim());
        if (serviceProvider.getAddress().getCountry() == null) {
            Country country = realm.createOrUpdateObjectFromJson(Country.class, countries.get(spinnerCountry.getSelectedItemPosition()).getValueIdsCode());
            serviceProvider.getAddress().setCountry(country);
        } else {
            serviceProvider.getAddress().setCountry(countries.get(spinnerCountry.getSelectedItemPosition()));
        }
        if (serviceProvider.getAddress().getCity() == null) {
            City city = realm.createOrUpdateObjectFromJson(City.class, cities.get(spinnerTownorCity.getSelectedItemPosition()).getValueIds());
            serviceProvider.getAddress().setCity(city);
        } else {
            serviceProvider.getAddress().setCity(cities.get(spinnerTownorCity.getSelectedItemPosition()));
        }
        if (serviceProvider.getAddress().getState() == null) {
            State state = realm.createOrUpdateObjectFromJson(State.class, states.get(spinnerState.getSelectedItemPosition()).getValueIds());
            serviceProvider.getAddress().setState(state);
        } else {
            serviceProvider.getAddress().setState(states.get(spinnerState.getSelectedItemPosition()));
        }

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
        getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();

    }

}

