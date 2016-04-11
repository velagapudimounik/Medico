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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;
import com.drughub.doctor.utils.DrughubIcon;
import com.gun0912.tedpicker.ImagePickerActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;


public class MyProfileAddClinicFragment extends DialogFragment {
    private static final int SELECT_PICTURE = 12;
    ArrayList<Uri> image_urls;
    EditText from_date_picker_edt,to_date_picker_edt  ;
    int from_year = -1,from_month = -1,from_day = -1;
    int to_year = -1,to_month = -1,to_day = -1;
    LinearLayout imagelayout;
    Spinner spinner1,spinner2,spinner3,spinner4;
    TextView tv1;
    public String[] country={"Country", "India", "Pakistan"};
    public String[] state={"State","State2","State3"};
    public String[] district={"District","District2","District3"};
    public String[] townorcity={"Town/City"};
    int spinner_position;
    String selected;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.addClinic));
        View view= inflater.inflate(R.layout.myprofile_addclinic_dailogbox,container,false);
        String myString="Country";
        spinner1=(Spinner)view.findViewById(R.id.country_spinner);
        spinner2=(Spinner)view.findViewById(R.id.state_spinner);
        spinner3=(Spinner)view.findViewById(R.id.district_spinner);
        spinner4=(Spinner)view.findViewById(R.id.townorcity_spinner);
       // FrameLayout plusicon=(FrameLayout)view.findViewById(R.id.plusicon);
        image_urls = new ArrayList<>();
        image_urls.add(0,Uri.EMPTY);
//        ArrayAdapter<String> spinner1adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,country);
//        ArrayAdapter<String> spinner2adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,state);
//        ArrayAdapter<String> spinner3adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,district);
//        ArrayAdapter<String> spinner4adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,townorcity);
       // CountrySpinner   LTRadapter.setDropDownViewResource(android.R.layout.myprofile_addclinic_dailogbox);

        spinner1.setAdapter(new CountrySpinner(getActivity(),country));
        spinner2.setAdapter(new StateSpinner(getActivity(),state));
        spinner3.setAdapter(new DistrictSpinner(getActivity(), district));
        spinner4.setAdapter(new TownCitySpinner(getActivity(), townorcity));
        spinner1.setSelection(0);
        spinner2.setSelection(0);
        spinner3.setSelection(0);
        spinner4.setSelection(0);
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
        FrameLayout iconplus=(FrameLayout)view.findViewById(R.id.icon_plus);
        iconplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
                startActivityForResult(intent, SELECT_PICTURE);
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

    public class CountrySpinner extends ArrayAdapter<String> {
        private int position;
        private View convertView;
        private ViewGroup parent;
        private Context context;
        public CountrySpinner(Context context, String[] country) {
            super(context,R.layout.myporfile_spinner_addclinic,country);
        }
    }

    private class StateSpinner extends ArrayAdapter<String>{
        public StateSpinner(Context context, String[] state) {
            super(context,R.layout.myporfile_spinner_addclinic,state);
        }
    }

    private class DistrictSpinner extends ArrayAdapter<String> {
        public DistrictSpinner(Context context, String[] district) {
            super(context,R.layout.myporfile_spinner_addclinic,district);
        }
    }
    private class TownCitySpinner extends ArrayAdapter<String> {
        public TownCitySpinner(Context context, String[] district) {
            super(context,R.layout.myporfile_spinner_addclinic,townorcity);
        }
    }
}
