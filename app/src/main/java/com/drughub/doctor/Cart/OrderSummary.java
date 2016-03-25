package com.drughub.doctor.Cart;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Dialog;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;

import java.util.ArrayList;
import java.util.List;


public class OrderSummary extends Fragment {

    Fragment fragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((CartActivity)getActivity()).setActionBarTitle(getString(R.string.order_summary_title));

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cart_order_summary,container,false);


       final TextView edit = (TextView) view.findViewById(R.id.edit);
        Button confirm_order = (Button) view.findViewById(R.id.confirm_order);
        edit.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            final Dialog dialog = CustomDialog.showCustomDialog((BaseActivity)getContext(), R.layout.cart_change_shipping_address,
                    Gravity.BOTTOM, true, true, false);

            Spinner spinner = (Spinner) dialog.findViewById(R.id.change_address);

            List<String> categories = new ArrayList<>();
            categories.add("My Address");
            categories.add("Clinic1 | Address");
            categories.add("Clinic2 | Address");

            ArrayAdapter<String> data = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1 ,categories);
            data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(data);

            Button change_address = (Button) dialog.findViewById(R.id.change_address_btn);
            change_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            }
        });

        confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getContext(),PaymentOptions.class);
                startActivity(intent);
*/
                fragment = new PaymentOptions();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();




            }
        });
        return view;

    }



}
