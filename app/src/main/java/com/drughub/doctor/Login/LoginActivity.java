package com.drughub.doctor.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.Nullable;


import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

public class LoginActivity extends BaseActivity {
    Fragment fragment=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main_layout);

        fragment=new LoginPage();
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.replace(R.id.container1, fragment).commit();
    }

}
