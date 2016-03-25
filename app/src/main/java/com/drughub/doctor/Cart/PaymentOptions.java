package com.drughub.doctor.Cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.drughub.doctor.R;


public class PaymentOptions extends Fragment {

    Fragment fragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((CartActivity)getActivity()).setActionBarTitle(getString(R.string.payment_options_title));

        View view = inflater.inflate(R.layout.cart_payment_options,container,false);
        LinearLayout cash = (LinearLayout) view.findViewById(R.id.cashon_delivery);
        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*

                Intent intent = new Intent(getContext(),CashOnDelivery.class);
                startActivity(intent);
*/

                fragment = new CashOnDelivery();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,fragment).addToBackStack(null).commit();


            }
        });

        return view;

    }
}
