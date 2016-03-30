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


    public class recycleradapter extends RecyclerView.Adapter<recycleradapter.RecyclerViewHolder> {

        FragmentActivity clinics=null;
        String[] HospitalsName={"Sandeep Clinic","Harsha Hospitals"};
        String[] HospitalAddr={"Banjara Hills,RoadNo-2","Somajiguda,2nd Floor"};
        Context context;
        LayoutInflater inflater;
        TextView hospitalname,hospitaladdr,imageicon,viewicon;


        public recycleradapter(FragmentActivity context) {
            this.clinics=context;
            inflater=LayoutInflater.from(context);
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v=inflater.inflate(R.layout.myprofile_clinic_item,parent,false);
            return new RecyclerViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder holder, int position) {
            holder.hospitalname.setText(HospitalsName[position]);
            holder.hospitaladdr.setText(HospitalAddr[position]);

        }

        @Override
        public int getItemCount() {
            return HospitalsName.length;
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            TextView hospitalname,hospitaladdr;
            //  DrughubIcon imageicon,viewicon;
            public RecyclerViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clinics.getSupportFragmentManager().beginTransaction().add(R.id.containeractivity,new MyProfileClinicDetailsFragment()).addToBackStack(null).commit();
                    }
                });
                hospitalname=(TextView)itemView.findViewById(R.id.hospitalname);
                hospitaladdr=(TextView)itemView.findViewById(R.id.hospitaladdress);
                imageicon=(TextView)itemView.findViewById(R.id.imageicon);
                viewicon=(TextView)itemView.findViewById(R.id.viewicon);

            }
        }
    }
}
