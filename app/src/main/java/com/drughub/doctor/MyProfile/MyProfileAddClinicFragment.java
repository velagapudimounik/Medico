package com.drughub.doctor.MyProfile;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;

import java.util.Calendar;


public class MyProfileAddClinicFragment extends DialogFragment {
    EditText from_date_picker_edt,to_date_picker_edt  ;
    int from_year = -1,from_month = -1,from_day = -1;
    int to_year = -1,to_month = -1,to_day = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.addClinic));
        return inflater.inflate(R.layout.myprofile_addclinic_dailogbox,container,false);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().setTitle("My Profile");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        from_date_picker_edt = (EditText) view.findViewById(R.id.from_date_picker);
        to_date_picker_edt = (EditText) view.findViewById(R.id.to_date_picker);
        from_date_picker_edt.setKeyListener(null);
        to_date_picker_edt.setKeyListener(null);
        from_date_picker_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener()
                {

                    @Override
                    public void onDateSet(DatePicker view, int local_year, int monthOfYear, int dayOfMonth) {
                        from_date_picker_edt.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (monthOfYear+1)) + "/" + local_year);
                        from_date_picker_edt.setTextColor(Color.GRAY);
                        from_day = dayOfMonth;
                        from_month = monthOfYear;
                        from_year = local_year;
                    }
                };
                CustomDialog.showDatePicker((BaseActivity)getActivity(), onDateSetListener, from_day, from_month, from_year);
            }
        });

        to_date_picker_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener()
                {

                    @Override
                    public void onDateSet(DatePicker view, int local_year, int monthOfYear, int dayOfMonth) {
                        to_date_picker_edt.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + local_year);
                        to_date_picker_edt.setTextColor(Color.GRAY);
                        to_day = dayOfMonth;
                        to_month = monthOfYear;
                        to_year = local_year;
                    }
                };
                CustomDialog.showDatePicker((BaseActivity)getActivity(),onDateSetListener , to_day ,to_month ,to_year);

            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

}
