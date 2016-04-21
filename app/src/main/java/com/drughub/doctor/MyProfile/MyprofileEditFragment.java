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
import android.widget.Toast;

import com.drughub.doctor.MyProfile.adapters.SpinnerAdapter;
import com.drughub.doctor.R;
import com.drughub.doctor.model.Address;
import com.drughub.doctor.model.AllCity;
import com.drughub.doctor.model.AllState;
import com.drughub.doctor.model.City;
import com.drughub.doctor.model.Country;
import com.drughub.doctor.model.Qualification;
import com.drughub.doctor.model.ServiceProvider;
import com.drughub.doctor.model.Specialization;
import com.drughub.doctor.model.State;
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
    ArrayList<String> values;
    private RealmResults<Country> countries;
    private RealmResults<AllState> states;
    private RealmResults<AllCity> cities;
    private RealmResults<Specialization> specializations;
    private RealmResults<Qualification> qualifications;
    EditText editfirstName, editmiddleName, editlastName, edityearsofexp, buildNumber, doorNumber, streetName, colonyName, pincode, landMark, emailID, mobile;

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
            getEditEmailAddress().setText(serviceProvider.getEmailId());
            getEditEmailAddress().setEnabled(false);
            getEditMobile().setText(serviceProvider.getMobile());
            getEditMobile().setEnabled(false);
            //getEditYearsofExperience().setText(serviceProvider.get);
            if (serviceProvider.getAddress() != null) {
                getEditBuildingName().setText(serviceProvider.getAddress().getBuildingName());
                getEditDoorNo().setText(serviceProvider.getAddress().getDoorNumber());
                getEditStreetName().setText(serviceProvider.getAddress().getStreetName());
                getEditColonyName().setText(serviceProvider.getAddress().getAreaName());
                getEditPincode().setText(serviceProvider.getAddress().getPostalCode());
            }
        }

        countries = realm.where(Country.class).findAllSorted("value");
        if (countries.size() > 0) {
            final ArrayList<String> values = new ArrayList<String>();
            for (Country country : countries) {
                values.add(country.getValue());
            }
            values.add("Country");
            spinnerCountry.setAdapter(new SpinnerAdapter(getContext(), values));
            spinnerCountry.setSelection(values.size() - 1);

            spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != values.size() - 1)
                        getStates(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            if (serviceProvider.getAddress().getCountry() != null) {
                int pos = values.indexOf(serviceProvider.getAddress().getCountry().getValue());
                if (pos > 0)
                    spinnerCountry.setSelection(pos);
            }
        }

        getSpecializtions();
        getQualifications();
        final ArrayList<String> statevalues = new ArrayList<String>();
        statevalues.add("State");
        statevalues.add("State");
        spinnerState.setAdapter(new SpinnerAdapter(getContext(), statevalues));
        final ArrayList<String> cityvalues = new ArrayList<>();
        cityvalues.add("City");
        cityvalues.add("City");
        spinnerTownorCity.setAdapter(new SpinnerAdapter(getContext(), cityvalues));

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
                    ArrayList<String> values = new ArrayList<>();
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

//                    ValueIds valueIds = new ValueIds(serviceProvider.getAddress().getState().getValueIds());
                    realm.beginTransaction();
                    if (states != null)
                        states.clear();
                    realm.allObjects(AllState.class).clear();
                    realm.createOrUpdateAllFromJson(AllState.class, (new JSONObject(result)).getJSONArray("response").toString());
//                    State stat = realm.createOrUpdateObjectFromJson(State.class, valueIds.getValueIds());
//                    serviceProvider.getAddress().setState(stat);


                    realm.commitTransaction();

                    states = realm.where(AllState.class).findAllSorted("value");


                    if (states.size() > 0) {

                        values = new ArrayList<String>();
                        for (AllState state : states) {
                            values.add(state.getValue());
                        }
                        values.add("State");

                        spinnerState.setAdapter(new SpinnerAdapter(getContext(), values));
                        spinnerState.setSelection(values.size() - 1);
                        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (!spinnerState.getSelectedItem().toString().equalsIgnoreCase("state"))
                                    getCities(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        int pos = values.indexOf(serviceProvider.getAddress().getState().getValue());
                        if (pos > 0)
                            spinnerState.setSelection(pos);
                    } else {
                        final ArrayList<String> statevalues = new ArrayList<String>();
                        statevalues.add("State");
                        statevalues.add("State");
                        spinnerState.setAdapter(new SpinnerAdapter(getContext(), statevalues));
                        final ArrayList<String> cityvalues = new ArrayList<String>();
                        cityvalues.add("City");
                        cityvalues.add("City");
                        spinnerTownorCity.setAdapter(new SpinnerAdapter(getContext(), cityvalues));
                    }

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

//                    ValueIds valueIds = new ValueIds(serviceProvider.getAddress().getCity().getValueIds());
                    realm.beginTransaction();
                    if (cities != null)
                        cities.clear();
                    realm.allObjects(AllCity.class).clear();
                    realm.createOrUpdateAllFromJson(AllCity.class, (new JSONObject(result)).getJSONArray("response").toString());
//                    City city1 = realm.createOrUpdateObjectFromJson(City.class, valueIds.getValueIds());
//                    serviceProvider.getAddress().setCity(city1);
                    realm.commitTransaction();
                    cities = realm.where(AllCity.class).findAllSorted("value");
                    ArrayList<String> values = new ArrayList<>();
                    for (AllCity city : cities) {
                        values.add(city.getValue());
                    }
                    values.add("City");
                    if (values.size() == 1) {
                        values.add("City");
                    }
                    spinnerTownorCity.setAdapter(new SpinnerAdapter(getContext(), values));
                    spinnerTownorCity.setSelection(values.size() - 1);
                    spinnerTownorCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    int pos = values.indexOf(serviceProvider.getAddress().getCity().getValue());
                    if (pos >= 0)
                        spinnerTownorCity.setSelection(pos);
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

        editfirstName = (EditText) getView().findViewById(R.id.editFirstName);
        editmiddleName = (EditText) getView().findViewById(R.id.editMiddleName);
        editlastName = (EditText) getView().findViewById(R.id.editLastName);
        buildNumber = (EditText) getView().findViewById(R.id.editBuildingName);
        edityearsofexp = (EditText) getView().findViewById(R.id.editYearsofExperience);
        doorNumber = (EditText) getView().findViewById(R.id.editDoorNo);
        streetName = (EditText) getView().findViewById(R.id.editStreetName);
        colonyName = (EditText) getView().findViewById(R.id.editColonyName);
        pincode = (EditText) getView().findViewById(R.id.editPincode);
        landMark = (EditText) getView().findViewById(R.id.editLandMark);
        emailID = (EditText) getView().findViewById(R.id.editEmailAddress);
        mobile = (EditText) getView().findViewById(R.id.editMobile);

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

    public void updateProfile() {

        if (editfirstName.getText().toString().isEmpty())
            Toast.makeText(getContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
        else if (editmiddleName.getText().toString().isEmpty())
            Toast.makeText(getContext(), "Enter Middle Name", Toast.LENGTH_SHORT).show();
        else if (editlastName.getText().toString().isEmpty())
            Toast.makeText(getContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
        else if (buildNumber.getText().toString().isEmpty())
            Toast.makeText(getContext(), "Enter Building Number", Toast.LENGTH_SHORT).show();
        else if (edityearsofexp.getText().toString().isEmpty())
            Toast.makeText(getContext(), "Enter Years of Experience", Toast.LENGTH_SHORT).show();
        else if (doorNumber.getText().toString().isEmpty())
            Toast.makeText(getContext(), "Enter Door Number", Toast.LENGTH_SHORT).show();
        else if (streetName.getText().toString().isEmpty())
            Toast.makeText(getContext(), "Enter Street Name", Toast.LENGTH_SHORT).show();
        else if (colonyName.getText().toString().isEmpty())
            Toast.makeText(getContext(), "Enter Colony Name", Toast.LENGTH_SHORT).show();
        else if (spinnerCountry.getSelectedItem() == null)
            Toast.makeText(getContext(), "Enter Country Name", Toast.LENGTH_SHORT).show();
        else if (spinnerState.getSelectedItem() == null)
            Toast.makeText(getContext(), "Enter State Name", Toast.LENGTH_SHORT).show();
        else if (spinnerTownorCity.getSelectedItem() == null)
            Toast.makeText(getContext(), "Enter City Name", Toast.LENGTH_SHORT).show();
        else if (pincode.getText().toString().isEmpty())
            Toast.makeText(getContext(), "Enter pincode", Toast.LENGTH_SHORT).show();
        else if (landMark.getText().toString().isEmpty())
            Toast.makeText(getContext(), "Enter landmark ", Toast.LENGTH_SHORT).show();
        else if (emailID.getText().toString().isEmpty())
            Toast.makeText(getContext(), "Enter Email ID", Toast.LENGTH_SHORT).show();
        else if (mobile.getText().toString().isEmpty())
            Toast.makeText(getContext(), "Enter Mobile Number", Toast.LENGTH_SHORT).show();
        else {
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

            if (serviceProvider.getAddress() == null) {
                serviceProvider.setAddress(realm.createObject(Address.class));
                serviceProvider.getAddress().setAddressId(-1);
            }
            serviceProvider.getAddress().setBuildingName(getEditBuildingName().getText().toString());
            serviceProvider.getAddress().setDoorNumber(getEditDoorNo().getText().toString());
            serviceProvider.getAddress().setStreetName(getEditStreetName().getText().toString());
            serviceProvider.getAddress().setAreaName(getEditColonyName().getText().toString());
            serviceProvider.getAddress().setPostalCode(getEditPincode().getText().toString());
            serviceProvider.getAddress().setLandmark(getEditLandMark().getText().toString());
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
                serviceProvider.getAddress().setCity(realm.createOrUpdateObjectFromJson(City.class, cities.get(spinnerTownorCity.getSelectedItemPosition()).getValueIds()));

                Log.i("City_-", cities.get(spinnerTownorCity.getSelectedItemPosition()).getValueIds() + "");
            }
            if (serviceProvider.getAddress().getState() == null) {
                State state = realm.createOrUpdateObjectFromJson(State.class, states.get(spinnerState.getSelectedItemPosition()).getValueIds());
                serviceProvider.getAddress().setState(state);
            } else {
                serviceProvider.getAddress().setState((realm.createOrUpdateObjectFromJson(State.class, states.get(spinnerState.getSelectedItemPosition()).getValueIds())));
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
            ((MyProfileFragment) getFragmentManager().findFragmentById(R.id.containeractivity)).onUpdateProfile();
        }
    }
}

