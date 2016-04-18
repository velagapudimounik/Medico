package com.drughub.doctor.MyProfile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drughub.doctor.R;
import com.drughub.doctor.model.DoctorClinic;
import com.drughub.doctor.network.Globals;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;

import io.realm.Realm;

public class MyProfileClinicDetailsFragment extends Fragment {
    private Realm realm;
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter adapter;
    String mobile;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myprofile_clinic_details, container, false);
        getActivity().setTitle(Globals.selectedDoctorClinic.getClinicName());
        TextView clinicName = (TextView) view.findViewById(R.id.ClinicName);
        TextView clinicAddress = (TextView) view.findViewById(R.id.ClinicAddress);
        TextView consultataionfee = (TextView) view.findViewById(R.id.consultation_Fee);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.imagerecyclerview);
        ClinicDetailsAdapter adapter = new ClinicDetailsAdapter(this.getActivity());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        DoctorClinic clinic = new DoctorClinic();

        clinicName.setText(Globals.selectedDoctorClinic.getClinicName());
        clinicAddress.setText(Globals.selectedDoctorClinic.getAddress().getBuildingName()+", "+Globals.selectedDoctorClinic.getAddress().getDoorNumber()+
                ", "+Globals.selectedDoctorClinic.getAddress().getLandMark()+" \n"+Globals.selectedDoctorClinic.getAddress().getCity().getValue().trim()+". Mobile Number : "+Globals.selectedDoctorClinic.getPhoneNo()+"");
        consultataionfee.setText(Globals.selectedDoctorClinic.getConsultationFee()+"");

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().setTitle("My Profile");
    }

    public class ClinicDetailsAdapter extends RecyclerView.Adapter<ClinicDetailsAdapter.RecyclerViewHolder> {
        FragmentActivity context = null;

        public ClinicDetailsAdapter(FragmentActivity context) {
            this.context = context;
        }

        @Override
        public ClinicDetailsAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view1 = LayoutInflater.from(context).inflate(R.layout.myprifile_clinicimage_recyclerview, parent, false);
            return new RecyclerViewHolder(view1);
        }

        @Override
        public void onBindViewHolder(ClinicDetailsAdapter.RecyclerViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {
            public RecyclerViewHolder(View itemView) {
                super(itemView);
            }
        }


    }
}
