package com.drughub.doctor.MyProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drughub.doctor.R;

import android.widget.Button;

public class MyclinicRecyclerView extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.my_profile_title));
        final View view = inflater.inflate(R.layout.myprofile_myclinic_fragment, container, false);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.myclinic_recyclerview);
        recycleradapter adapter=new recycleradapter(this.getActivity());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==view.findViewById(R.id.addclinic_button)){
                    getFragmentManager().beginTransaction().add(R.id.containeractivity,new MyProfileAddClinicFragment()).addToBackStack(null).commit();
                }
            }
        };
        Button addclinic=(Button)view.findViewById(R.id.addclinic_button);
        addclinic.setOnClickListener(listener);
    return view;

    }

}
