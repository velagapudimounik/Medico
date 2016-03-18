package com.drughub.doctor;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drughub.doctor.MyOrders.MyOrderActivityAdapter;
import com.drughub.doctor.MyOrders.MyOrderContainer;


public class MoreFragment extends Fragment {


    Fragment fragment = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity)getActivity()).setActionBarVisibility(false);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.more,container,false);

        LinearLayout my_orders = (LinearLayout) view.findViewById(R.id.my_orders);
        my_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyOrderContainer.class);
               // startActivity(intent);
            }
        });

        TextView share = (TextView) view.findViewById(R.id.shareapp);
        share.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        return view;



    }

}
