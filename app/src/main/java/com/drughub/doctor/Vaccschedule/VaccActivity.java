package com.drughub.doctor.Vaccschedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.Notification.NotificationActivity;
import com.drughub.doctor.R;

public class VaccActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccination_schedule);
        setTitle("Vaccination Schedule");
        setBackButton(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        VaccAdapter vaccAdapter = new VaccAdapter(this);
        recyclerView.setAdapter(vaccAdapter);

        View doneBtn = findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addActionButton(R.string.icon_notification);
    }

    public void onActionButtonClicked(int drughubIconRes)
    {
        super.onActionButtonClicked(drughubIconRes);

        Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
        startActivity(intent);
    }

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
}












