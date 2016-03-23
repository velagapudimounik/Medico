package com.drughub.doctor.patient_record;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

/**
 * Created by Deepak on 3/22/2016.
 */
public class PatientRecordActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record);
        setBackButton(true);
        setTitle("Patient Records");

        changeFragment(new PatientRecordFragment());
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
