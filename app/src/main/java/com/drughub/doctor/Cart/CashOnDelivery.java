package com.drughub.doctor.Cart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.drughub.doctor.R;


public class CashOnDelivery extends Fragment {

    Fragment fragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ((CartActivity)getActivity()).setActionBarTitle("Cash On Delivery");

      View view = inflater.inflate(R.layout.cash_on_delivery,container,false);
        Button confrim = (Button) view.findViewById(R.id.confirm_order);


        confrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.confirm_order_custom_dialog);
                TextView confirm_txt = (TextView) dialog.findViewById(R.id.confirm_dialog_txt);
                confirm_txt.setText("Order ID : 205455");
                dialog.getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
                dialog.show();

                Button confirm_btn = (Button) dialog.findViewById(R.id.dialog_yes);
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

