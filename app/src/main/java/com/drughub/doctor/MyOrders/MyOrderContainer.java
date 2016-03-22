package com.drughub.doctor.MyOrders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.Cart.CartActivity;
import com.drughub.doctor.R;


public class MyOrderContainer extends BaseActivity {

    Fragment fragment = null;
    boolean value = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorder_containter);

        setActionBarTitle("My Orders");
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
