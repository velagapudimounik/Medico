package com.drughub.doctor.MyOrders;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Dialog;
import android.widget.Button;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;
import com.drughub.doctor.utils.StringUtils;



public class OrderInformationList extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.myorders_order_information,container,false);
        Button cancel_order = (Button) view.findViewById(R.id.cancel_order);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.orders_list);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        OrderInformationChildView orderInformationChildView = new OrderInformationChildView(this.getActivity());
        recyclerView.setAdapter(orderInformationChildView);

        ((MyOrderContainer)getActivity()).setActionBarTitle(getString(R.string.order_information_title));

        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String value = "#1223311";

                final Dialog dialog = CustomDialog.showQuestionDialog((BaseActivity) getContext(), StringUtils.findAndReplace(getString(R.string.orderinfo_dialog_message), "{Order ID}", value));

                Button dialog_no = (Button) dialog.findViewById(R.id.dialogNoBtn);
                dialog_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                Button dialog_yes = (Button) dialog.findViewById(R.id.dialogYesBtn);
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
