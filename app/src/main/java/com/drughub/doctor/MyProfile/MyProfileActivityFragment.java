package com.drughub.doctor.MyProfile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.drughub.doctor.R;
import com.drughub.doctor.utils.DrughubIcon;

//import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivityFragment extends Fragment {
    RadioButton myprofile;
    RadioButton changepassword;
    RadioButton myclinic;
    RadioGroup profile_radiogroup;
    FrameLayout emptyimageview;
    FrameLayout addimageIcon;
    DrughubIcon editicon;
    DrughubIcon righticon;
    boolean clickCount = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.my_profile_title));
        final View view = inflater.inflate(R.layout.myprofile_activity_layout, container, false);
        profile_radiogroup = (RadioGroup) view.findViewById(R.id.myprofileradiogroup);
        editicon = (DrughubIcon) view.findViewById(R.id.Editicon);
        //editicon.setOnClickListener(this);
        righticon = (DrughubIcon) view.findViewById(R.id.rightmark);
        //righticon.setOnClickListener(this);
        //final TextView changetext=(TextView)view.findViewById(R.id.profile_changetextview);
        addimageIcon = (FrameLayout) view.findViewById(R.id.add_image_view);
        //addimageIcon.setOnClickListener(this);
        //final CircleImageView profileimage=(CircleImageView)view.findViewById(R.id.image_profile);
        emptyimageview = (FrameLayout) view.findViewById(R.id.emptyimage);
        myprofile = (RadioButton) view.findViewById(R.id.Myprofilebutton);
        final RadioButton changepassword = (RadioButton) view.findViewById(R.id.changepasswordbutton);
        final RadioButton myclinic = (RadioButton) view.findViewById(R.id.Myclinic_button);
        getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
        myprofile.setChecked(true);
        righticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
                righticon.setVisibility(View.INVISIBLE);
                editicon.setVisibility(View.VISIBLE);
                addimageIcon.setVisibility(View.INVISIBLE);
                emptyimageview.setVisibility(View.VISIBLE);
                myprofile.setPressed(true);
            }
        });
        editicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount = true;
                getFragmentManager().beginTransaction().replace(R.id.container2, new MyprofileEditFragment()).commit();
                myprofile.setChecked(true);
                changepassword.setChecked(false);
                myclinic.setChecked(false);
                System.out.println(clickCount + "clickCount");
                Toast.makeText(getActivity(), "Editmain", Toast.LENGTH_SHORT).show();
                addimageIcon.setVisibility(View.VISIBLE);
                righticon.setVisibility(View.VISIBLE);
                editicon.setVisibility(View.INVISIBLE);
                emptyimageview.setVisibility(View.INVISIBLE);
                //clickCount=false;
            }
        });
        profile_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.Myprofilebutton) {
                    getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
                    if (clickCount == true) {
                        Toast.makeText(getContext(), "Edit", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.container2, new MyprofileEditFragment()).commit();
                    }
                    clickCount = false;
                } else if (checkedId == R.id.Myclinic_button) {
                    myprofile.setChecked(false);
                    righticon.setVisibility(View.INVISIBLE);
                    addimageIcon.setVisibility(View.INVISIBLE);
                    emptyimageview.setVisibility(View.VISIBLE);
                    editicon.setVisibility(View.VISIBLE);
                    getFragmentManager().beginTransaction().replace(R.id.container2, new MyclinicRecyclerView()).commit();
                } else if (checkedId == R.id.changepasswordbutton) {
                    myprofile.setChecked(false);
                    righticon.setVisibility(View.INVISIBLE);
                    addimageIcon.setVisibility(View.INVISIBLE);
                    emptyimageview.setVisibility(View.VISIBLE);
                    editicon.setVisibility(View.VISIBLE);
                    getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileChangePasswordFragment()).commit();
                }
            }
        });
        return view;
    }

    public void editIcon() {
        righticon.setVisibility(View.INVISIBLE);
        editicon.setVisibility(View.VISIBLE);
        addimageIcon.setVisibility(View.INVISIBLE);
        emptyimageview.setVisibility(View.VISIBLE);
        myprofile.setChecked(true);

    }


}

/* private void Changetextmethod() {
                changetext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changetext.setVisibility(View.INVISIBLE);
                        profileimage.setVisibility(View.INVISIBLE);
                        addimageIcon.setVisibility(View.VISIBLE);
                        addimagemethod();
                    }
                });
            }*/
/*
            private void addimagemethod() {
                addimageIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        if (v1 == addimageIcon) {
                            addimageIcon.setVisibility(View.INVISIBLE);
                            emptyimageview.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }*/