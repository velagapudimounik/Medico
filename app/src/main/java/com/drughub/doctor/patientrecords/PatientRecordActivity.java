package com.drughub.doctor.patientrecords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.DrughubIcon;

public class PatientRecordActivity extends BaseActivity {

    public DrughubIcon bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_record_activity);
        setBackButton(true);
        setTitle(getResources().getString(R.string.patientrecords));
        bookmark = (DrughubIcon)addActionButton(R.string.icon_bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BookmarkedActivity.class));

            }
        });

        changeFragment(new PatientRecordFragment());
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_content, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }
}
