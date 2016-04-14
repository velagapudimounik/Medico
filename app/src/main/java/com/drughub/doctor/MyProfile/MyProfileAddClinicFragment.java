package com.drughub.doctor.MyProfile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.drughub.doctor.R;
import com.drughub.doctor.model.City;
import com.drughub.doctor.model.Country;
import com.drughub.doctor.model.DoctorClinic;
import com.drughub.doctor.model.State;
import com.drughub.doctor.network.Globals;
import com.gun0912.tedpicker.ImagePickerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class MyProfileAddClinicFragment extends DialogFragment {
    private static final int SELECT_PICTURE = 12;
    ArrayList<Uri> image_urls;
    EditText from_date_picker_edt, to_date_picker_edt;
    int from_year = -1, from_month = -1, from_day = -1;
    int to_year = -1, to_month = -1, to_day = -1;
    LinearLayout imagelayout;
    Spinner spinner1, spinner2, spinner3, spinner4;
    TextView tv1;
    //public String[] country={"Country", "India", "Pakistan"};
    public String[] state = {"State", "State2", "State3"};
    public String[] district = {"District", "District2", "District3"};
    public String[] townorcity = {"Town/City"};
    int spinner_position;
    String selected;
    Button addClinic;
    RealmResults<Country> countries;
    RealmResults<State> states;
    RealmResults<City> cities;
    Realm realm;
    ProgressDialog progress;
    EditText clinic_Name, building_name, doorNo, streetName, colonyName, landmark, Mobile, consultationFee, pincode;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.addClinic));
        View view = inflater.inflate(R.layout.myprofile_addclinic_dailogbox, container, false);
        String myString = "Country";
        spinner1 = (Spinner) view.findViewById(R.id.country_spinner);
        spinner2 = (Spinner) view.findViewById(R.id.state_spinner);
        spinner3 = (Spinner) view.findViewById(R.id.district_spinner);
        spinner4 = (Spinner) view.findViewById(R.id.townorcity_spinner);
        clinic_Name = (EditText) view.findViewById(R.id.clinic_name);
        building_name = (EditText) view.findViewById(R.id.building_name);
        doorNo = (EditText) view.findViewById(R.id.door_no);
        streetName = (EditText) view.findViewById(R.id.street_name);
        colonyName = (EditText) view.findViewById(R.id.colony_name);
        Mobile = (EditText) view.findViewById(R.id.mobile);
        consultationFee = (EditText) view.findViewById(R.id.consultation_fee);
        pincode = (EditText) view.findViewById(R.id.pincode);
        landmark = (EditText) view.findViewById(R.id.landmark);
        addClinic = (Button) view.findViewById(R.id.addButton);
        // FrameLayout plusicon=(FrameLayout)view.findViewById(R.id.plusicon);
        image_urls = new ArrayList<>();
        image_urls.add(0, Uri.EMPTY);
        //ArrayAdapter<String> spinner1adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, country);
        //ArrayAdapter<String> spinner2adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, state);
        ArrayAdapter<String> spinner3adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, district);
        ArrayAdapter<String> spinner4adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, townorcity);
        // spinner1.setAdapter(spinner1adapter);
        //spinner2.setAdapter(spinner2adapter);
        spinner3.setAdapter(spinner3adapter);
       // spinner4.setAdapter(spinner4adapter);
        image_urls.add(0, Uri.EMPTY);
//        ArrayAdapter<String> spinner1adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,country);
//        ArrayAdapter<String> spinner2adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,state);
//        ArrayAdapter<String> spinner3adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,district);
//        ArrayAdapter<String> spinner4adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,townorcity);
        // CountrySpinner   LTRadapter.setDropDownViewResource(android.R.layout.myprofile_addclinic_dailogbox);

        //spinner1.setAdapter(new CountrySpinner(getActivity(),countries));
        //spinner2.setAdapter(new StateSpinner(getActivity(), state));
        spinner3.setAdapter(new DistrictSpinner(getActivity(), district));
       // spinner4.setAdapter(new TownCitySpinner(getActivity(), townorcity));
        //  spinner1.setSelection(0);
       // spinner2.setSelection(0);
        spinner3.setSelection(0);
       // spinner4.setSelection(0);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().setTitle("My Profile");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        imagelayout = (LinearLayout) view.findViewById(R.id.mainimagelayout);
        FrameLayout iconplus = (FrameLayout) view.findViewById(R.id.icon_plus);
        iconplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
                startActivityForResult(intent, SELECT_PICTURE);
            }
        });
        addClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clinicName = clinic_Name.getText().toString();
                String buildingName = building_name.getText().toString();
                String mobile = Mobile.getText().toString();
                String con_fee = consultationFee.getText().toString();
                if (!clinicName.isEmpty()) {
                    if (mobile.length() >= 10) {
                        if (!con_fee.isEmpty()) {
                            progress = ProgressDialog.show(getActivity(), "Add Clinic", "Please wait...", true);
                            DoctorClinic clinic = new DoctorClinic();
                            clinic.setClinicName(clinicName);
                            clinic.setPhoneNo(mobile);
                            clinic.setConsultationFee(con_fee);
                            clinic.AddClinic(getContext(), new Globals.VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    try {
                                        JSONObject object = new JSONObject(result);
                                        if (object.getBoolean("result")) {
                                            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), object.getString("errorMessage"), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFail(String result) {

                                }
                            });

                        } else {
                            Toast.makeText(getContext(), "Enter Consultation Fee", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getContext(), "Enter Mobile", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Enter Clinic Name", Toast.LENGTH_SHORT).show();
                }
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
        countries = realm.where(Country.class).findAllSorted("value");
        states = realm.where(State.class).findAllSorted("value");
        if (countries != null) {
            spinner1.setAdapter(new CountrySpinner(getContext(), countries));
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Context context;
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                image_urls = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
                // image_urls.add(Uri.EMPTY);
                System.out.println(image_urls + "=========1");
                for (int i = 0; i < image_urls.size(); i++) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, 120);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setImageURI(image_urls.get(i));
                    layoutParams.setMargins(10, 0, 0, 10);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    //System.out.println(i+"========i");
                    imagelayout.addView(imageView);

                }
            }
        }
    }

    public class CountrySpinner extends ArrayAdapter<Country> {
        private int position;
        private View convertView;
        private ViewGroup parent;
        private Context context;
        private int id;
        RealmResults<Country> countries;

        public CountrySpinner(Context context, RealmResults<Country> countries) {
            super(context, R.layout.myporfile_spinner_addclinic, countries);
            this.context = context;
            this.id = id;
            this.countries = countries;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View countySpinner = countrySet(position);
            int value;
            value = countries.get(position).getId();
            Globals.getState(value, new Globals.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.allObjects(State.class).clear();
                        realm.createAllFromJson(State.class, (new JSONObject(result)).getJSONArray("response").toString());
                        realm.commitTransaction();
                        realm.close();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFail(String result) {

                }
            });
            return countySpinner;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int value;
            value = countries.get(position).getId();
            View countrySpinner = countrySet(position);
            Globals.getState(value, new Globals.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.allObjects(State.class).clear();
                        realm.createAllFromJson(State.class, (new JSONObject(result)).getJSONArray("response").toString());
                        realm.commitTransaction();
                        states = realm.where(State.class).findAllSorted("value");
                        if (states != null) {
                            spinner2.setAdapter(new StateSpinner(getContext(), states));
                        }
                        realm.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFail(String result) {

                }
            });


            return countrySpinner;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }
        View countrySet(int position)
        {
            LayoutInflater inflater = getLayoutInflater(null);
            View countrySpinner = inflater.inflate(R.layout.myporfile_spinner_addclinic, null);
            TextView countryTextview = (TextView) countrySpinner.findViewById(R.id.spinner_textview);

            countryTextview.setText(countries.get(position).getValue());
            countryTextview.setTextColor(Color.DKGRAY);
            return  countrySpinner;

        }
    }

    private class StateSpinner extends ArrayAdapter<State> {
        Context context;
        RealmResults<State> states;

        public StateSpinner(Context context, RealmResults<State> states) {
            super(context, R.layout.myporfile_spinner_addclinic, states);
            this.context = context;
            this.states = states;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View stateSpinner = stateSet(position);
            int value = states.get(position).getId();
            Globals.getCity(value, new Globals.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.allObjects(City.class).clear();
                        realm.createAllFromJson(City.class, (new JSONObject(result)).getJSONArray("response").toString());
                        realm.commitTransaction();
                        cities = realm.where(City.class).findAllSorted("value");
                        if(cities != null)
                        {
                            spinner4.setAdapter(new TownCitySpinner(getContext(),cities));
                        }
                        realm.close();
                    }
                    catch (Exception e)
                    {

                    }

                }

                @Override
                public void onFail(String result) {

                }
            });
            return stateSpinner;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View stateSpinner = stateSet(position);
            int value = states.get(position).getId();
            Globals.getCity(value, new Globals.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.allObjects(City.class).clear();
                        realm.createAllFromJson(City.class, (new JSONObject(result)).getJSONArray("response").toString());
                        realm.commitTransaction();
                        cities = realm.where(City.class).findAllSorted("value");
                        if(cities != null)
                        {
                            spinner4.setAdapter(new TownCitySpinner(getContext(),cities));
                        }
                        realm.close();
                    }
                    catch (Exception e)
                    {

                    }

                }

                @Override
                public void onFail(String result) {

                }
            });

            return stateSpinner;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        View stateSet(int position)
        {
            LayoutInflater inflater = getLayoutInflater(null);
            View stateSpinner = inflater.inflate(R.layout.myporfile_spinner_addclinic, null);
            TextView state = (TextView) stateSpinner.findViewById(R.id.spinner_textview);
            state.setText(states.get(position).getValue());
            Log.i("StateFrom", states.get(position).getValue());
            state.setTextColor(Color.DKGRAY);
            return stateSpinner;
        }
    }

    private class DistrictSpinner extends ArrayAdapter<String> {
        public DistrictSpinner(Context context, String[] district) {
            super(context, R.layout.myporfile_spinner_addclinic, district);
        }
    }

    private class TownCitySpinner extends ArrayAdapter<City> {
        Context context;
        RealmResults<City> cities;

        public TownCitySpinner(Context context,  RealmResults<City> objects) {
            super(context, R.layout.myporfile_spinner_addclinic, objects);
            this.context = context;
            this.cities = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View citySpinner = citySet(position);

            return citySpinner;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View citySpinner = citySet(position);
            return citySpinner;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        View citySet(int position)
        {
            LayoutInflater inflater = getLayoutInflater(null);
            View citySpinner = inflater.inflate(R.layout.myporfile_spinner_addclinic, null);
            TextView cityTextView = (TextView) citySpinner.findViewById(R.id.spinner_textview);
            cityTextView.setText(cities.get(position).getValue());
            cityTextView.setTextColor(Color.DKGRAY);
            return citySpinner;
        }
    }

    private class CustomAdapter extends ArrayAdapter<String> {

        public CustomAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }

        @Override
        public Resources.Theme getDropDownViewTheme() {
            return super.getDropDownViewTheme();


        }
    }

}
