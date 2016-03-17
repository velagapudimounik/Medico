package com.drughub.doctor.Login;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.drughub.doctor.R;

public class ThanksRegards extends Fragment {
    //TextView toolbartitle = null;

    Fragment fragment=null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thankyou, container, false);
        getActivity().setTitle("  ForgotPassword ");

        TextView textlast = (TextView) view.findViewById(R.id.textThankYou);
        Button btnok = (Button) view.findViewById(R.id.okButton);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new LoginPage();
            android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container1,fragment).commit();
                getActivity().getSupportFragmentManager().popBackStack(null, android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        });
        return view;
    }

    public void setTitle(String title) {


    }
}
