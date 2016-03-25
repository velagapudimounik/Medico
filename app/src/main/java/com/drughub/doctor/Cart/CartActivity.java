package com.drughub.doctor.Cart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

public class CartActivity extends BaseActivity {


    Fragment fragment = null;
    boolean value = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_container);
        setActionBarTitle(getString(R.string.cart_title));




        if(value)
        {
            fragment = new CartListFragment();

        }
        else
        {
            fragment = new CartEmpty();
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();

    }
    public void setActionBarTitle(String title)
    {
        setTitle(title);
    }

 }
