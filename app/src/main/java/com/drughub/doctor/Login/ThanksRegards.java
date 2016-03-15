package com.drughub.doctor.Login;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.drughub.doctor.R;
import android.support.v4.app.Fragment;

public class ThanksRegards extends Fragment {
    //TextView toolbartitle = null;

    Fragment fragment=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thankyou, container, false);
        getActivity().setTitle("  ForgotPassword ");

        TextView textlast = (TextView) view.findViewById(R.id.textthankyou);
        Button btnok = (Button) view.findViewById(R.id.okbutton);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new LoginPage();
            android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container1,fragment).commit();
        }
        });
        return view;
    }

    public void setTitle(String title) {


    }
}
