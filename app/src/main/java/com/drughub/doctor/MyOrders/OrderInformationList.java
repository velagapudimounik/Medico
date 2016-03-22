package com.drughub.doctor.MyOrders;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.app.Dialog;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.drughub.doctor.R;


public class OrderInformationList extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.order_information,container,false);
        Button cancel_order = (Button) view.findViewById(R.id.cancel_order);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.orders_list);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        OrderInformationChildView orderInformationChildView = new OrderInformationChildView(this.getActivity());
        recyclerView.setAdapter(orderInformationChildView);

        ((MyOrderContainer)getActivity()).setActionBarTitle("Order Information");

        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dailog);
                TextView dialog_txt = (TextView) dialog.findViewById(R.id.dialog_txt);
                TextView dialog_txt2 = (TextView) dialog.findViewById(R.id.dialog_txt2);

                dialog_txt.setText(" Are you Sure? ");
                dialog_txt2.setText(" You want to Cancel Order #014899. ");



                Button dialog_no = (Button) dialog.findViewById(R.id.dialog_no);
                dialog_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button dialog_yes = (Button) dialog.findViewById(R.id.dialog_yes);
                dialog_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });


        return view;
    }
}
