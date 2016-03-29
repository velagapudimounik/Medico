package com.drughub.doctor.Notification;

import android.os.Bundle;
import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

public class NotificationsettingActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationsetting);
        setTitle("Notifications Settings");
        setBackButton(true);
    }
}













