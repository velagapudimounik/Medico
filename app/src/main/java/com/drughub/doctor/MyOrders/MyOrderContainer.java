package com.drughub.doctor.MyOrders;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;


public class MyOrderContainer extends BaseActivity {

    Fragment fragment = null;
    boolean value = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorders_containter);

        setActionBarTitle(getString(R.string.my_orders));
        setBackButton(true);
        setActionBarVisibility(true);

        if(value)
        {
            fragment = new MyOrderList();
        }
        else {
            fragment = new MyOrderEmpty();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.order_container,fragment).commit();

    }

    public void setActionBarTitle(String title)
    {
        setTitle(title);
    }


}
