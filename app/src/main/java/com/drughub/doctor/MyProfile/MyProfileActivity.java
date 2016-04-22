package com.drughub.doctor.MyProfile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.MyProfile.adapters.SpinnerAdapter;
import com.drughub.doctor.R;
import com.drughub.doctor.model.Address;
import com.drughub.doctor.model.AllCity;
import com.drughub.doctor.model.AllState;
import com.drughub.doctor.model.City;
import com.drughub.doctor.model.Country;
import com.drughub.doctor.model.DoctorClinic;
import com.drughub.doctor.model.Qualification;
import com.drughub.doctor.model.ServiceProvider;
import com.drughub.doctor.model.Specialization;
import com.drughub.doctor.model.State;
import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;
import com.drughub.doctor.utils.CustomDialog;
import com.drughub.doctor.utils.DrughubIcon;
import com.drughub.doctor.utils.PrefUtils;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MyProfileActivity extends BaseActivity implements View.OnClickListener {

    // My Profile Objects
    private ImageView profileImage;
    private TextView profileIcon;
    private View imageView;
    private RadioButton myProfile;
    private RadioGroup profile_radiogroup;
    private DrughubIcon editicon, righticon;
    private TextView doctorName, doctorDHCode, doctorEmail;
    private boolean editMode = false;
    private static final int SELECT_PICTURE = 120;
    public static final int PICK_IMAGE = 110;
    public static final int CROP_IMAGE = 200;
    private String fileName;
    private Uri outputUri;
    int day = -1, month = -1, year = -1;


    // ProfileDetails
    private TextView doctorNameDetails;
    private TextView qualification;
    private TextView adressline1;
    private TextView adressline2;
    private TextView email;
    private TextView mobile;
    private TextView yearsOfExperience;


    // ProfileEditDetails
    private Spinner spinnerCountry;
    private Spinner spinnerState;
    private Spinner spinnerDistrict;
    private Spinner spinnerTownorCity;
    private Spinner spinnerSpecialization;
    private Spinner spinnerQualification;
    private RealmResults<Country> countries;
    private RealmResults<AllState> states;
    private RealmResults<AllCity> cities;
    private RealmResults<Specialization> specializations;
    private RealmResults<Qualification> qualifications;
    private EditText editFirstName, editMiddleName, editLastName, editBuildNumber, editDoorNumber, editStreetName, editAreaName, editPinCode, editLandMark, editEmailID, editMobile, editPractiseStartDate;
    private ArrayList<String> stateValues, cityValues;
    private final String HINT_COUNTRY = "Country";
    private final String HINT_STATE = "State";
    private final String HINT_CITY = "City";

    // ChangePassword
    EditText currentPassword, newPassword, confirmPassword;

    // MyClinics

    private View itemView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private RealmResults<DoctorClinic> doctorClinics;


    Fragment fragment = null;
    Realm realm;
    private ServiceProvider serviceProvider;
    LinearLayout detailsLayout;
    LinearLayout changePasswordLayout;
    RelativeLayout myClinicsLayout;
    LinearLayout editLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_activity);
        detailsLayout = (LinearLayout) findViewById(R.id.detailsLayout);
        changePasswordLayout = (LinearLayout) findViewById(R.id.changePasswordLayout);
        myClinicsLayout = (RelativeLayout) findViewById(R.id.myClinicsLayout);
        editLayout = (LinearLayout) findViewById(R.id.myProfileEditLayout);

        realm = Realm.getDefaultInstance();
        setBackButton(true);
        getData();

        initMyProfile();
        initProfileDetails();
        initProfileEditDetails();
        initChangePassword();
        initMyClinics();


    }

    private void initMyClinics() {
        mRecyclerView = (RecyclerView) findViewById(R.id.myclinic_recyclerview);
        itemView = (LinearLayout) findViewById(R.id.no_items);
        findViewById(R.id.addClinicButton).setOnClickListener(this);
    }

    private void initChangePassword() {
        currentPassword = (EditText) findViewById(R.id.currentPassword);
        currentPassword.setTypeface(Typeface.DEFAULT);
        newPassword = (EditText) findViewById(R.id.newPassword);
        newPassword.setTypeface(Typeface.DEFAULT);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        confirmPassword.setTypeface(Typeface.DEFAULT);
        findViewById(R.id.buttonSubmit).setOnClickListener(this);

    }

    private void initProfileEditDetails() {
        spinnerCountry = (Spinner) findViewById(R.id.spinnerCountry);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);
        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        spinnerTownorCity = (Spinner) findViewById(R.id.spinnerTownorCity);
        spinnerQualification = (Spinner) findViewById(R.id.spinnerQualification);
        spinnerSpecialization = (Spinner) findViewById(R.id.spinnerSpecialization);

        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editMiddleName = (EditText) findViewById(R.id.editMiddleName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editBuildNumber = (EditText) findViewById(R.id.editBuildingName);
        // editYearsOfExp = (EditText) findViewById(R.id.editYearsOfExperience);
        editDoorNumber = (EditText) findViewById(R.id.editDoorNo);
        editStreetName = (EditText) findViewById(R.id.editStreetName);
        editAreaName = (EditText) findViewById(R.id.editAreaName);
        editPinCode = (EditText) findViewById(R.id.editPincode);
        editLandMark = (EditText) findViewById(R.id.editLandMark);
        editEmailID = (EditText) findViewById(R.id.editEmailAddress);
        editMobile = (EditText) findViewById(R.id.editMobile);
        editPractiseStartDate = (EditText) findViewById(R.id.editPracticeStartDate);
        editPractiseStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int local_year, int monthOfYear, int dayOfMonth) {
                        editPractiseStartDate.setText(local_year+ "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth) );
                        editPractiseStartDate.setTextColor(Color.GRAY);
                        day = dayOfMonth;
                        month = monthOfYear;
                        year = local_year;
                    }
                };
                CustomDialog.showDatePicker(MyProfileActivity.this, onDateSetListener, day, month, year);

            }
        });

        findViewById(R.id.buttonUpdate).setOnClickListener(this);

    }

    private void initProfileDetails() {
        doctorNameDetails = (TextView) findViewById(R.id.doctor_name);
        qualification = (TextView) findViewById(R.id.qualification);
        adressline1 = (TextView) findViewById(R.id.textadressLine1);
        adressline2 = (TextView) findViewById(R.id.textadressLine2);
        email = (TextView) findViewById(R.id.email);
        yearsOfExperience = (TextView) findViewById(R.id.experience);
        mobile = (TextView) findViewById(R.id.mobile);
        setDetails();
    }

    private void setDetails() {
        if (serviceProvider != null) {
            email.setText(serviceProvider.getEmailId());
            mobile.setText(serviceProvider.getMobile());
            doctorNameDetails.setText("");
            if (serviceProvider.getFirstName() != null)
                doctorNameDetails.setText(serviceProvider.getFirstName());
            if (serviceProvider.getMiddleName() != null)
                doctorNameDetails.append(" " + serviceProvider.getMiddleName());
            if (serviceProvider.getLastName() != null)
                doctorNameDetails.append(" " + serviceProvider.getLastName());
            qualification.setText("");
            if (serviceProvider.getQualificationList() != null && serviceProvider.getQualificationList().size() > 0)
                qualification.setText(serviceProvider.getQualificationList().get(0).getValue());
            if (serviceProvider.getSpecializationList() != null && serviceProvider.getSpecializationList().size() > 0)
                qualification.append(", " + serviceProvider.getSpecializationList().get(0).getValue());
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
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            yearsOfExperience.setText(simpleDateFormat.format(serviceProvider.getPractiseStartDate()));
            yearsOfExperience.setText((serviceProvider.getPractiseStartDate()));
        }

    }

    private void initMyProfile() {
        profile_radiogroup = (RadioGroup) findViewById(R.id.myProfileRadiogroup);
        doctorName = (TextView) findViewById(R.id.doctorName);
        doctorDHCode = (TextView) findViewById(R.id.doctorID);
        doctorEmail = (TextView) findViewById(R.id.doctorEmail);
        editicon = (DrughubIcon) findViewById(R.id.Editicon);
        righticon = (DrughubIcon) findViewById(R.id.rightmark);
        imageView = findViewById(R.id.image_view);
        profileIcon = (DrughubIcon) findViewById(R.id.profile_icon);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        imageView.setEnabled(false);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                profileIcon.setVisibility(View.INVISIBLE);
                profileImage.setVisibility(View.VISIBLE);
            }
        });

        myProfile = (RadioButton) findViewById(R.id.myProfileButton);
        final RadioButton changepassword = (RadioButton) findViewById(R.id.changepasswordbutton);
        final RadioButton myclinic = (RadioButton) findViewById(R.id.Myclinic_button);
//        getSupportFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
        myProfile.setChecked(true);
        righticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
        editicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAllViews();


//                getSupportFragmentManager().beginTransaction().replace(R.id.container2, new MyprofileEditFragment()).commit();
                if (!myProfile.isChecked()) {
                    editMode = true;
                    myProfile.setChecked(true);
                    changepassword.setChecked(false);
                    myclinic.setChecked(false);
                    imageView.setEnabled(true);
                }
                setEditDetails();
                editLayout.setVisibility(View.VISIBLE);
                profileIcon.setText(getResources().getText(R.string.icon_foradd_image));
                righticon.setVisibility(View.VISIBLE);
                editicon.setVisibility(View.INVISIBLE);
            }
        });
        profile_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                hideAllViews();
                switch (checkedId) {
                    case R.id.myProfileButton:
                        if (!editMode)
                            detailsLayout.setVisibility(View.VISIBLE);
//                            getSupportFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
                        editMode = false;

                        break;
                    case R.id.changepasswordbutton:
                        myProfile.setChecked(false);
                        righticon.setVisibility(View.INVISIBLE);
                        editicon.setVisibility(View.VISIBLE);
                        profileIcon.setText(getResources().getText(R.string.icon_noimage));
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileChangePasswordFragment()).commit();
                        changePasswordLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.Myclinic_button:
                        myProfile.setChecked(false);
                        righticon.setVisibility(View.INVISIBLE);
                        editicon.setVisibility(View.VISIBLE);
                        profileIcon.setText(getResources().getText(R.string.icon_noimage));
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container2, new MyClinicsFragment()).commit();
                        myClinicsLayout.setVisibility(View.VISIBLE);
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

                        loadClinics();
                        break;
                }
            }
        });
    }

    private void setEditDetails() {
        if (serviceProvider != null) {
            editFirstName.setText(serviceProvider.getFirstName());
            editMiddleName.setText(serviceProvider.getMiddleName());
            editLastName.setText(serviceProvider.getLastName());
            editEmailID.setText(serviceProvider.getEmailId());
            editEmailID.setEnabled(false);
            editMobile.setText(serviceProvider.getMobile());
            editMobile.setEnabled(false);
            if (serviceProvider.getPractiseStartDate() != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                editPractiseStartDate.setText(simpleDateFormat.format(serviceProvider.getPractiseStartDate()));
            }
            if (serviceProvider.getAddress() != null) {
                editBuildNumber.setText(serviceProvider.getAddress().getBuildingName());
                editDoorNumber.setText(serviceProvider.getAddress().getDoorNumber());
                editStreetName.setText(serviceProvider.getAddress().getStreetName());
                editAreaName.setText(serviceProvider.getAddress().getAreaName());
                editPinCode.setText(serviceProvider.getAddress().getPostalCode());
                editLandMark.setText(serviceProvider.getAddress().getLandMark());
            }
            countries = realm.where(Country.class).findAllSorted("value");
            if (countries.size() > 0) {
                final ArrayList<String> values = new ArrayList<String>();
                for (Country country : countries) {
                    values.add(country.getValue());
                }
                values.add(HINT_COUNTRY);
                spinnerCountry.setAdapter(new SpinnerAdapter(getApplicationContext(), values));
                spinnerCountry.setSelection(values.size() - 1);

                spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (!spinnerCountry.getSelectedItem().toString().equalsIgnoreCase(HINT_COUNTRY))
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

            getSpecializations();
            getQualifications();
            stateValues = new ArrayList<String>();
            stateValues.add(HINT_STATE);
            stateValues.add(HINT_STATE);
            spinnerState.setAdapter(new SpinnerAdapter(getApplicationContext(), stateValues));
            cityValues = new ArrayList<>();
            cityValues.add(HINT_CITY);
            cityValues.add(HINT_CITY);
            spinnerTownorCity.setAdapter(new SpinnerAdapter(getApplicationContext(), cityValues));
        }
    }

    private void hideAllViews() {
        detailsLayout.setVisibility(View.GONE);
        changePasswordLayout.setVisibility(View.GONE);
        myClinicsLayout.setVisibility(View.GONE);
        editLayout.setVisibility(View.GONE);
    }

    private void getData() {

        Globals.getCountries(new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    realm.beginTransaction();
                    realm.allObjects(Country.class).clear();
                    realm.createAllFromJson(Country.class, (new JSONObject(result)).getJSONArray("response").toString());
                    realm.commitTransaction();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {

            }
        });

        Globals.GET(Urls.SERVICE_PROVIDER + PrefUtils.getUserName(getApplicationContext()), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    Log.v("SP GET result", result);
                    JSONObject object = new JSONObject(result);
                    if (object.getBoolean("result")) {
                        realm.beginTransaction();
                        realm.allObjects(ServiceProvider.class).clear();
                        realm.createOrUpdateObjectFromJson(ServiceProvider.class, object.getJSONObject("response"));
                        realm.commitTransaction();
                    }
                    serviceProvider = realm.where(ServiceProvider.class).findFirst();
                    onUpdateProfile();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.containeractivity, new MyProfileFragment()).commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {
            }
        }, "");

    }

    private void selectImage() {
        fileName = "temp_" + System.currentTimeMillis();
        //SyncStateContract.Constants.iscamera=true;
        final CharSequence[] items = {"Take a photo", "Select from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfileActivity.this);
        builder.setTitle("Add a Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take a photo")) {
                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(android.os.Environment.getExternalStorageDirectory(), fileName);
                    camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(camera, PICK_IMAGE);
                } else if (items[item].equals("Select from Gallery")) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PICTURE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(requestCode + "====requestcode");
        System.out.println(resultCode + "====resultcode");
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                outputUri = selectedImageUri;
                Crop.of(selectedImageUri, outputUri).asSquare().start(MyProfileActivity.this);
            } else if (requestCode == PICK_IMAGE) {
                File file = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : file.listFiles()) {
                    if (temp.getName().equals(fileName)) {
                        file = temp;
                        break;
                    }
                }
                try {
                    Uri uri = Uri.fromFile(file);
                    outputUri = uri;
                    Crop.of(uri, outputUri).asSquare().start(MyProfileActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == Crop.REQUEST_CROP) {
                System.out.println(requestCode + "=====requestcode2");
//                Toast.makeText(getActivity(), "Crop", Toast.LENGTH_SHORT).show();
                profileImage.setImageURI(outputUri);
                System.out.println(profileImage + "====p=====");
            }
        }
    }

    void onUpdateProfile() {
        hideAllViews();
        editMode = false;
        imageView.setEnabled(false);
        myProfile.setChecked(true);
        if (serviceProvider != null) {
            doctorName.setText("Dr." + serviceProvider.getFirstName());
            doctorDHCode.setText("" + serviceProvider.getSpProfileId());
            doctorEmail.setText(serviceProvider.getEmailId());
            setDetails();
        }
        detailsLayout.setVisibility(View.VISIBLE);
        righticon.setVisibility(View.INVISIBLE);
        editicon.setVisibility(View.VISIBLE);
        profileIcon.setText(getResources().getText(R.string.icon_noimage));
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
//        realm.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonUpdate:
                updateProfile();
                break;
            case R.id.buttonSubmit:
                changePassword();
                break;
            case R.id.addClinicButton:
                addClinic();
                break;
        }
    }

    private void addClinic() {
        MyProfileAddClinicFragment fragment = new MyProfileAddClinicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("doctorClinic", "addClinic");
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.containeractivity, fragment).addToBackStack(null).commit();
    }

    private void changePassword() {
        String currentpwd = currentPassword.getText().toString();
        String newPwd = newPassword.getText().toString();
        String confirmpwd = confirmPassword.getText().toString();
        if (currentpwd.isEmpty())
            Toast.makeText(getApplicationContext(), "Please enter current password", Toast.LENGTH_SHORT).show();
        else if (newPwd.isEmpty())
            Toast.makeText(getApplicationContext(), "Please enter your new password", Toast.LENGTH_SHORT).show();
        else if (confirmpwd.isEmpty())
            Toast.makeText(getApplicationContext(), "Please confirm your new password", Toast.LENGTH_SHORT).show();
        else {
            if (newPwd.equals(confirmpwd)) {
                JSONObject object = new JSONObject();
                try {
                    String newPass = Globals.encryptString(newPwd);
                    object.put("oldpassword", Globals.encryptString(currentPassword.getText().toString().trim()));
                    object.put("newpassword", newPass);
                    object.put("confirmpassword", newPass);
                    //object.put("email", PrefUtils.getUserName(getActivity()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Globals.POST(Urls.CHANGE_PASSWORD, object.toString(), new Globals.VolleyCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            if (object.getBoolean("result")) {
                                currentPassword.setText("");
                                newPassword.setText("");
                                confirmPassword.setText("");
                                Toast.makeText(getApplicationContext(), "Your password changed successfully", Toast.LENGTH_SHORT).show();
                                Log.v("result==change", result);
                            } else {
                                Toast.makeText(getApplicationContext(), "" + object.getString("errorMessage"), Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFail(String result) {
                        Toast.makeText(getApplicationContext(), "Unable to process your request, please try again", Toast.LENGTH_SHORT).show();
                        Log.v("Result===", result);
                    }
                }, "");

            }
        }
    }

    public void updateProfile() {

        if (editFirstName.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
        else if (editMiddleName.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter Middle Name", Toast.LENGTH_SHORT).show();
        else if (editLastName.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT).show();
        else if (editBuildNumber.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter Building Number", Toast.LENGTH_SHORT).show();
        else if (editPractiseStartDate.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter Years of Experience", Toast.LENGTH_SHORT).show();
        else if (editDoorNumber.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter Door Number", Toast.LENGTH_SHORT).show();
        else if (editStreetName.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter Street Name", Toast.LENGTH_SHORT).show();
        else if (editAreaName.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter Area Name", Toast.LENGTH_SHORT).show();
        else if (spinnerCountry.getSelectedItem().toString().equalsIgnoreCase(HINT_COUNTRY))
            Toast.makeText(getApplicationContext(), "Enter Country Name", Toast.LENGTH_SHORT).show();
        else if (spinnerState.getSelectedItem().toString().equalsIgnoreCase(HINT_STATE))
            Toast.makeText(getApplicationContext(), "Select State Name", Toast.LENGTH_SHORT).show();
        else if (spinnerTownorCity.getSelectedItem().toString().equalsIgnoreCase(HINT_CITY))
            Toast.makeText(getApplicationContext(), "Select City Name", Toast.LENGTH_SHORT).show();
        else if (editPinCode.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter pincode", Toast.LENGTH_SHORT).show();
        else if (editLandMark.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter landmark ", Toast.LENGTH_SHORT).show();
        else if (editEmailID.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter Email ID", Toast.LENGTH_SHORT).show();
        else if (mobile.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter Mobile Number", Toast.LENGTH_SHORT).show();
        else {
            realm.beginTransaction();
            serviceProvider.setMobile(editMobile.getText().toString().trim());
            serviceProvider.setFirstName(editFirstName.getText().toString().trim());
            serviceProvider.setLastName(editLastName.getText().toString().trim());
            serviceProvider.setEmailId(editEmailID.getText().toString().trim());
            serviceProvider.setMiddleName(editMiddleName.getText().toString().trim());
            RealmList<Qualification> qualificationsList = new RealmList<>();
            qualificationsList.add(qualifications.get(spinnerQualification.getSelectedItemPosition()));
            serviceProvider.setQualificationList(qualificationsList);
            RealmList<Specialization> specializationList = new RealmList<>();
            specializationList.add(specializations.get(spinnerSpecialization.getSelectedItemPosition()));
            serviceProvider.setSpecializationList(specializationList);
            //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("");
            serviceProvider.setPractiseStartDate(editPractiseStartDate.getText().toString());

            if (serviceProvider.getAddress() == null) {
                serviceProvider.setAddress(realm.createObject(Address.class));
                serviceProvider.getAddress().setAddressId(-1);
            }
            serviceProvider.getAddress().setBuildingName(editBuildNumber.getText().toString());
            serviceProvider.getAddress().setDoorNumber(editDoorNumber.getText().toString());
            serviceProvider.getAddress().setStreetName(editStreetName.getText().toString());
            serviceProvider.getAddress().setAreaName(editAreaName.getText().toString());
            serviceProvider.getAddress().setPostalCode(editPinCode.getText().toString());
            serviceProvider.getAddress().setLandMark(editLandMark.getText().toString());
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

            serviceProvider.UpdateServiceProvider(getApplicationContext(), new Globals.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    onUpdateProfile();
                    Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                    Log.v("update SP response", result);
                }

                @Override
                public void onFail(String result) {
                    Log.v("update SP fail response", result);
                }
            });
        }
    }


    private void getSpecializations() {
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
                    spinnerSpecialization.setAdapter(new SpinnerAdapter(getApplicationContext(), values));
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
                    spinnerQualification.setAdapter(new SpinnerAdapter(getApplicationContext(), values));
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

                    realm.beginTransaction();
                    if (states != null)
                        states.clear();
                    realm.allObjects(AllState.class).clear();
                    realm.createOrUpdateAllFromJson(AllState.class, (new JSONObject(result)).getJSONArray("response").toString());
                    realm.commitTransaction();
                    states = realm.where(AllState.class).findAllSorted("value");
                    if (states.size() > 0) {
                        stateValues = new ArrayList<String>();
                        for (AllState state : states) {
                            stateValues.add(state.getValue());
                        }
                        stateValues.add(HINT_STATE);
                        spinnerState.setAdapter(new SpinnerAdapter(getApplicationContext(), stateValues));
                        spinnerState.setSelection(stateValues.size() - 1);
                        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (!spinnerState.getSelectedItem().toString().equalsIgnoreCase(HINT_STATE))
                                    getCities(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        int pos = stateValues.indexOf(serviceProvider.getAddress().getState().getValue());
                        if (pos > 0)
                            spinnerState.setSelection(pos);
                    } else {
                        stateValues = new ArrayList<String>();
                        stateValues.add(HINT_STATE);
                        stateValues.add(HINT_STATE);
                        spinnerState.setAdapter(new SpinnerAdapter(getApplicationContext(), stateValues));
                        cityValues = new ArrayList<String>();
                        cityValues.add(HINT_CITY);
                        cityValues.add(HINT_CITY);
                        spinnerTownorCity.setAdapter(new SpinnerAdapter(getApplicationContext(), cityValues));
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
                    realm.beginTransaction();
                    if (cities != null)
                        cities.clear();
                    realm.allObjects(AllCity.class).clear();
                    realm.createOrUpdateAllFromJson(AllCity.class, (new JSONObject(result)).getJSONArray("response").toString());
                    realm.commitTransaction();
                    cities = realm.where(AllCity.class).findAllSorted("value");
                    cityValues = new ArrayList<>();
                    for (AllCity city : cities) {
                        cityValues.add(city.getValue());
                    }
                    cityValues.add(HINT_CITY);
                    if (cityValues.size() == 1) {
                        cityValues.add(HINT_CITY);
                    }
                    spinnerTownorCity.setAdapter(new SpinnerAdapter(getApplicationContext(), cityValues));
                    spinnerTownorCity.setSelection(cityValues.size() - 1);
                    spinnerTownorCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    int pos = cityValues.indexOf(serviceProvider.getAddress().getCity().getValue());
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

    public void loadClinics() {
        if (doctorClinics != null && doctorClinics.size() > 0) {
            getClinicsFromRealm();
        } else {
            Globals.GET(Urls.CLINIC, new Globals.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject object = new JSONObject(result);
                        if (object.getBoolean("result")) {
                            realm.beginTransaction();
                            realm.allObjects(DoctorClinic.class).clear();
                            realm.createOrUpdateAllFromJson(DoctorClinic.class, object.getJSONArray("response"));
                            Log.i("Clinic_Response", object.getJSONArray("response").toString());
                            realm.commitTransaction();
                            getClinicsFromRealm();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(String result) {
                }
            }, "");
        }

    }

    public void getClinicsFromRealm() {
        doctorClinics = realm.allObjects(DoctorClinic.class);
        for (DoctorClinic doctorClinic : doctorClinics) {
            if (doctorClinic.getAddress() == null) {
                doctorClinic.setAddress(realm.createObject(Address.class));
                doctorClinic.getAddress().setState(realm.createObject(State.class));
                doctorClinic.getAddress().setCity(realm.createObject(City.class));
                doctorClinic.getAddress().setCountry(realm.createObject(Country.class));
            }
        }
        addValuesToRecyclerView();

    }

    private void addValuesToRecyclerView() {
        MyClinicsListAdapter adapter = new MyClinicsListAdapter(getApplicationContext(), doctorClinics);
        if (doctorClinics.size() > 0)
            itemView.setVisibility(View.GONE);
        else
            itemView.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(adapter);
    }

    public class MyClinicsListAdapter extends RecyclerSwipeAdapter<MyClinicsListAdapter.RecyclerViewHolder> {

        Context context = null;
        RealmResults<DoctorClinic> doctorClinics;

        public MyClinicsListAdapter(Context context, RealmResults<DoctorClinic> doctorClinics) {
            this.context = context;
            this.doctorClinics = doctorClinics;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.myprofile_clinic_item, parent, false);

            return new RecyclerViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewHolder viewHolder, final int position) {
            final DoctorClinic doctorClinic = doctorClinics.get(position);

            viewHolder.hospitalName.setText(doctorClinic.getClinicName());
            viewHolder.hospitalAddress.setText(doctorClinic.getAddress().getStreetName() + ", " + doctorClinic.getAddress().getBuildingName());

            viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = CustomDialog.showQuestionDialog(MyProfileActivity.this, getResources().getString(R.string.deleteClinicMessage));

                    View noBtn = dialog.findViewById(R.id.dialogNoBtn);
                    noBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    View yesBtn = dialog.findViewById(R.id.dialogYesBtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("clinicID", doctorClinics.get(position).getAddress().getCountry().getId() + "");
                            doctorClinic.DeleteClinic(doctorClinics.get(position).getClinicId(), new Globals.VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    realm.beginTransaction();
                                    doctorClinic.removeFromRealm();
                                    realm.commitTransaction();
                                    //notifyDataSetChanged();

                                    mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, doctorClinics.size());
                                    mItemManger.closeAllItems();

                                    if (doctorClinics.size() > 0)
                                        itemView.setVisibility(View.GONE);
                                    else
                                        itemView.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onFail(String result) {

                                }
                            });
                            dialog.dismiss();
                        }
                    });
                }
            });

            viewHolder.updatebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Globals.selectedDoctorClinic = doctorClinic;
                    MyProfileAddClinicFragment fragment = new MyProfileAddClinicFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("doctorClinic", "fromEdit");
                    notifyDataSetChanged();
                    Log.i("clinicID", Globals.selectedDoctorClinic.getAddress().getState().getValue() + "");
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.containeractivity, fragment).addToBackStack(null).commit();
                }
            });
            viewHolder.myClinicItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Globals.selectedDoctorClinic = doctorClinic;
                    getSupportFragmentManager().beginTransaction().add(R.id.containeractivity, new MyProfileClinicDetailsFragment()).addToBackStack(null).commit();
                }
            });

            mItemManger.bindView(viewHolder.itemView, position);
        }

        @Override
        public int getItemCount() {
            return doctorClinics.size();
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {

            SwipeLayout swipeLayout;
            View deleteBtn, updatebtn;
            TextView hospitalName, hospitalAddress;
            View myClinicItem;

            public RecyclerViewHolder(View itemView) {
                super(itemView);


                myClinicItem = itemView.findViewById(R.id.myClinicItem);


                deleteBtn = itemView.findViewById(R.id.deleteClinic);
                updatebtn = itemView.findViewById(R.id.editClinic);
                hospitalName = (TextView) itemView.findViewById(R.id.hospitalName);
                hospitalAddress = (TextView) itemView.findViewById(R.id.hospitalAddress);
                swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            }
        }
    }
}

