package com.drughub.doctor.MyProfile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

public class MyProfileActivity extends BaseActivity {
    Fragment fragment=null;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_activity);
        final Button myclinicbutton=(Button)findViewById(R.id.Myclinic_button);
        setBackButton(true);
        fragment=new MyProfileFragment();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.containeractivity, fragment).commit();
    }
}
