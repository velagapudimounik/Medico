package com.drughub.doctor.Cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;


public class CashOnDelivery extends Fragment {

    Fragment fragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((CartActivity)getActivity()).setActionBarTitle(getString(R.string.cash_on_delivery_title));

      View view = inflater.inflate(R.layout.cash_on_delivery,container,false);
        Button confrim = (Button) view.findViewById(R.id.confirm_order);


        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = CustomDialog.showMessageDialog((BaseActivity)getActivity(), "Order ID : 205455\nhas been placed successfully.", "ok");

                Button confirm_btn = (Button) dialog.findViewById(R.id.dialogOkBtn);
                confirm_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });


        return view;
      }
    }

