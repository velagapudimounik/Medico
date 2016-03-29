package com.drughub.doctor.Vaccschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        addActionButton(R.string.icon_notification);
    }

    public void onActionButtonClicked(int drughubIconRes)
    {
        super.onActionButtonClicked(drughubIconRes);

        Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
        startActivity(intent);
    }
}












