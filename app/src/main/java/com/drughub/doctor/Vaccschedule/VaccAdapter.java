package com.drughub.doctor.Vaccschedule;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drughub.doctor.R;


public class VaccAdapter extends RecyclerView.Adapter<VaccAdapter.adptrinfo> {


    Context context;


    VaccAdapter(Context l_context) {
        this.context = l_context;
    }


    @Override
    public adptrinfo onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccination_schedule_item, parent, false);
        return new adptrinfo(v);
    }

    @Override
    public void onBindViewHolder(adptrinfo holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 5;
    }


    public class adptrinfo extends RecyclerView.ViewHolder {

        public adptrinfo(View itemView) {
            super(itemView);


        }
    }


}
