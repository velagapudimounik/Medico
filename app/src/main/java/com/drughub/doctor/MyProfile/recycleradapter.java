package com.drughub.doctor.MyProfile;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drughub.doctor.R;
import com.drughub.doctor.utils.DrughubIcon;


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
        View v=inflater.inflate(R.layout.myclinic_recyclerview_parameters,parent,false);
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
            itemView.setOnClickListener(new
                                                View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        clinics.getSupportFragmentManager().beginTransaction().add(R.id.containeractivity,new MyProfileSandeepSpecialitiesHospital()).addToBackStack(null).commit();
                                                    }
                                                });
            hospitalname=(TextView)itemView.findViewById(R.id.hospitalname);
            hospitaladdr=(TextView)itemView.findViewById(R.id.hospitaladdress);
            imageicon=(TextView)itemView.findViewById(R.id.imageicon);
            viewicon=(TextView)itemView.findViewById(R.id.viewicon);

        }
    }
}
