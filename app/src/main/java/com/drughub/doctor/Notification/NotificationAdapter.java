package com.drughub.doctor.Notification;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drughub.doctor.R;
import com.drughub.doctor.Vaccschedule.VaccActivity;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Adptrinfo> {


    Context context;

    NotificationAdapter(Context l_context) {
        this.context = l_context;
    }


    @Override
    public Adptrinfo onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new Adptrinfo(v);

    }

    @Override
    public void onBindViewHolder(Adptrinfo holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class Adptrinfo extends RecyclerView.ViewHolder {

        public Adptrinfo(View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VaccActivity.class);
                      context.startActivity(intent);

                }
            });
        }
    }
}





