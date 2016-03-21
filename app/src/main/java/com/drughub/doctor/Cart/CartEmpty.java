package com.drughub.doctor.Cart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;


public class CartEmpty extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.cart_empty);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ((BaseActivity)getActivity()).setBackButton(true);
        return inflater.inflate(R.layout.cart_empty,container,false);
    }



}
