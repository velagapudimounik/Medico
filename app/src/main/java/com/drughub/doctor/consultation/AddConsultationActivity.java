package com.drughub.doctor.consultation;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

public class AddConsultationActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_consultation_db);

        setTitle(getString(R.string.consultations));

        //getWindow().setBackgroundDrawable(new ColorDrawable(0x88000000));
        setBackButton(true);
    }
}
