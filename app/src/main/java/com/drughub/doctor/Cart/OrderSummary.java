package com.drughub.doctor.Cart;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.app.Dialog;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;

import java.util.ArrayList;
import java.util.List;


public class OrderSummary extends Fragment {

    final  String[] spinnervalues={"Clinic Name1 |","Clinic Name2 |","Clinic Name3 |","My Address"};
    final  String[] spinneraddress={"Address1","Address2","Address3",""};

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

            Spinner myspinner = (Spinner) dialog.findViewById(R.id.change_address);
            myspinner.setAdapter(new CustomAdapter(getActivity(), spinnervalues));
            myspinner.setSelection(myspinner.getCount());

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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PaymentOptions()).addToBackStack(null).commit();

            }
        });
        return view;

    }

    private class CustomAdapter  extends ArrayAdapter<String> {

        public CustomAdapter(Context context,  String[] objects) {
            super(context, R.layout.custom_spinner, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater =getLayoutInflater(null);
            View mySpinner = inflater.inflate(R.layout.custom_spinner, null);

            TextView clinicname = (TextView) mySpinner.findViewById(R.id.string1);
            clinicname.setText(spinnervalues[position]);
            TextView Address = (TextView) mySpinner.findViewById(R.id.string2);
            Address.setText(spinneraddress[position]);

            return mySpinner;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getLayoutInflater(null);
            View mySpinner=inflater.inflate(R.layout.custom_spinner, null);

            TextView clinicname=(TextView)mySpinner.findViewById(R.id.string1);
            clinicname.setText(spinnervalues[position]);
            TextView Address=(TextView)mySpinner.findViewById(R.id.string2);
            Address.setText(spinneraddress[position]);

            if(getCount() == position)
                clinicname.setTextColor(Color.LTGRAY);

            return  mySpinner;
        }

        @Override
        public int getCount() {
            return super.getCount()-1; // you dont display last item. It is used as hint.
        }

    }
}
