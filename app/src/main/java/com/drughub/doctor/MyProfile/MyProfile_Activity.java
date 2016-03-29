package com.drughub.doctor.MyProfile;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.Login.LoginPage;
import com.drughub.doctor.R;

public class MyProfile_Activity extends BaseActivity {
    Fragment fragment=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofileactivity_layout);
        final Button myclinicbutton=(Button)findViewById(R.id.Myclinic_button);
        fragment=new MyProfileActivityFragment();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.containeractivity, fragment).addToBackStack(null).commit();
    }/*
    private void initTabControl(){
        TabLayout tabLayout=(TabLayout)findViewById(R.id.TabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("My Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("Change Passwoord"));
        tabLayout.addTab(tabLayout.newTab().setText("My Clinics"));
    }*/

}
