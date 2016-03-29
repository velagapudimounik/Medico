package com.drughub.doctor.Notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;


public class NotificationActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        setTitle("Notifications");
        setBackButton(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler2);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        NotificationAdapter notificationAdapter = new NotificationAdapter(this);
        recyclerView.setAdapter(notificationAdapter);

        addActionButton(R.string.icon_setting);
    }

    public void onActionButtonClicked(int drughubIconRes) {
        super.onActionButtonClicked(drughubIconRes);

        Intent intent = new Intent(NotificationActivity.this, NotificationsettingActivity.class);
        startActivity(intent);
    }
}
