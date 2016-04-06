package com.drughub.doctor.MyProfile;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;
import com.drughub.doctor.utils.DrughubIcon;
import com.gun0912.tedpicker.ImagePickerActivity;

import java.util.ArrayList;
import java.util.Calendar;


public class MyProfileAddClinicFragment extends DialogFragment {
    private static final int SELECT_PICTURE = 12;
    ArrayList<Uri> image_urls;
    EditText from_date_picker_edt,to_date_picker_edt  ;
    int from_year = -1,from_month = -1,from_day = -1;
    int to_year = -1,to_month = -1,to_day = -1;
    LinearLayout imagelayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.addClinic));
        View view= inflater.inflate(R.layout.myprofile_addclinic_dailogbox,container,false);

       // FrameLayout plusicon=(FrameLayout)view.findViewById(R.id.plusicon);
        image_urls = new ArrayList<>();
        image_urls.add(0,Uri.EMPTY);
        return view;
    }
    @Override
    public void onPause() {
        super.onPause();
        getActivity().setTitle("My Profile");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        imagelayout=(LinearLayout)view.findViewById(R.id.mainimagelayout);
        from_date_picker_edt = (EditText) view.findViewById(R.id.from_date_picker);
        to_date_picker_edt = (EditText) view.findViewById(R.id.to_date_picker);
        from_date_picker_edt.setKeyListener(null);
        to_date_picker_edt.setKeyListener(null);
        from_date_picker_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int local_year, int monthOfYear, int dayOfMonth) {
                        from_date_picker_edt.setText(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", (monthOfYear + 1)) + "/" + local_year);
                        from_date_picker_edt.setTextColor(Color.GRAY);
                        from_day = dayOfMonth;
                        from_month = monthOfYear;
                        from_year = local_year;
                    }
                };
                CustomDialog.showDatePicker((BaseActivity) getActivity(), onDateSetListener, from_day, from_month, from_year);
            }
        });

        to_date_picker_edt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int local_year, int monthOfYear, int dayOfMonth) {
                        to_date_picker_edt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + local_year);
                        to_date_picker_edt.setTextColor(Color.GRAY);
                        to_day = dayOfMonth;
                        to_month = monthOfYear;
                        to_year = local_year;
                    }
                };
                CustomDialog.showDatePicker((BaseActivity) getActivity(), onDateSetListener, to_day, to_month, to_year);

            }
        });
        FrameLayout iconplus=(FrameLayout)view.findViewById(R.id.icon_plus);
        iconplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ImagePickerActivity.class);
                startActivityForResult(intent,SELECT_PICTURE);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Context context;
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                image_urls = data.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
                // image_urls.add(Uri.EMPTY);
                System.out.println(image_urls + "=========1");
                for (int i=0;i<image_urls.size();i++) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120,120);
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setImageURI(image_urls.get(i));
                    layoutParams.setMargins(10,0,0,10);
                    imageView.setLayoutParams(layoutParams);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    //System.out.println(i+"========i");
                    imagelayout.addView(imageView);

                }
            }
        }
    }
}
