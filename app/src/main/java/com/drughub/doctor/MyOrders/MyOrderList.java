package com.drughub.doctor.MyOrders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drughub.doctor.R;


public class MyOrderList extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle(getString(R.string.my_order_title));

        View view = inflater.inflate(R.layout.myorders_list,container,false);

        RecyclerView itemlist = (RecyclerView) view.findViewById(R.id.order_list);
        itemlist.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        itemlist.setLayoutManager(linearLayoutManager);

        MyOrderActivityAdapter adapter = new MyOrderActivityAdapter(this.getActivity());
        itemlist.setAdapter(adapter);

        return view;
    }



}
