package com.drughub.doctor.MyProfile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drughub.doctor.R;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;

import android.widget.Button;
import android.widget.TextView;

public class MyClinicsFragment extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.myprofile_myclinic_fragment, container, false);

        mRecyclerView=(RecyclerView)view.findViewById(R.id.myclinic_recyclerview);
        MyClinicsListAdapter adapter = new MyClinicsListAdapter(this.getActivity());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==view.findViewById(R.id.addclinic_button)){
                    getFragmentManager().beginTransaction().add(R.id.containeractivity,new MyProfileAddClinicFragment()).addToBackStack(null).commit();
                }
            }
        };

        Button addClinic = (Button)view.findViewById(R.id.addclinic_button);
        addClinic.setOnClickListener(listener);

        return view;
    }


    public class MyClinicsListAdapter extends RecyclerView.Adapter<MyClinicsListAdapter.RecyclerViewHolder> {

        FragmentActivity context=null;

        public MyClinicsListAdapter(FragmentActivity context) {
            this.context = context;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.myprofile_clinic_item, parent, false);
            return new RecyclerViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {

            public RecyclerViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.getSupportFragmentManager().beginTransaction().add(R.id.containeractivity,new MyProfileClinicDetailsFragment()).addToBackStack(null).commit();
                    }
                });
            }
        }
    }
}
