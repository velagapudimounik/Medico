package com.drughub.doctor;

import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends BaseActivity  {

    private TextView appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        setTitle(getString(R.string.about_info));
        setBackButton(true);

        appVersion = (TextView) findViewById(R.id.appVersion);
    }

}
