package com.drughub.doctor.MyProfile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drughub.doctor.R;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;

public class MyProfileClinicDetailsFragment extends Fragment {

    public  RecyclerView mRecyclerView;
    public RecyclerView.Adapter adapter;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.myprofile_clinic_details,container,false);
        getActivity().setTitle("Sandeep Clinic");
        mRecyclerView=(RecyclerView)view.findViewById(R.id.imagerecyclerview);
        ClinicDetailsAdapter adapter=new ClinicDetailsAdapter(this.getActivity());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().setTitle("My Profile");
    }

    public class ClinicDetailsAdapter extends RecyclerView.Adapter<ClinicDetailsAdapter.RecyclerViewHolder> {
        FragmentActivity context=null;
        public ClinicDetailsAdapter(FragmentActivity context){
            this.context=context;
        }

        @Override
        public ClinicDetailsAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view1=LayoutInflater.from(context).inflate(R.layout.myprifile_clinicimage_recyclerview,parent,false);
            return new RecyclerViewHolder(view1);
        }

        @Override
        public void onBindViewHolder(ClinicDetailsAdapter.RecyclerViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder{
            public RecyclerViewHolder(View itemView) {
                super(itemView);
            }
        }


    }
}
