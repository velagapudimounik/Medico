package com.drughub.doctor.MyProfile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.drughub.doctor.R;
import com.drughub.doctor.utils.DrughubIcon;

//import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivityFragment extends Fragment {
    //RadioButton myprofile,changepassword,myclinic;
    //FrameLayout  emptyimageview,addimageIcon;
    //DrughubIcon editicon,righticon;
    boolean clickCount=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("My Profile");
        final View view=inflater.inflate(R.layout.myprofile_activity_layout, container, false);
        final DrughubIcon editicon=(DrughubIcon)view.findViewById(R.id.Editicon);
        //editicon.setOnClickListener(this);
        final DrughubIcon righticon=(DrughubIcon)view.findViewById(R.id.rightmark);
        //righticon.setOnClickListener(this);
        //final TextView changetext=(TextView)view.findViewById(R.id.profile_changetextview);
        final FrameLayout addimageIcon=(FrameLayout)view.findViewById(R.id.add_image_view);
        //addimageIcon.setOnClickListener(this);
        //final CircleImageView profileimage=(CircleImageView)view.findViewById(R.id.image_profile);
        final FrameLayout emptyimageview=(FrameLayout)view.findViewById(R.id.emptyimage);
        final RadioButton myprofile=(RadioButton)view.findViewById(R.id.Myprofilebutton);
        final RadioButton changepassword=(RadioButton)view.findViewById(R.id.changepasswordbutton);
        final RadioButton myclinic=(RadioButton)view.findViewById(R.id.Myclinic_button);
        getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
        myprofile.setPressed(true);
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
                changepassword.setPressed(false);
                myclinic.setPressed(false);
                getFragmentManager().beginTransaction().replace(R.id.container2,new MyprofileEditFragment()).commit();
                clickCount=true;
                addimageIcon.setVisibility(View.VISIBLE);
                righticon.setVisibility(View.VISIBLE);
                editicon.setVisibility(View.INVISIBLE);
                emptyimageview.setVisibility(View.INVISIBLE);
                myprofile.setPressed(true);
                clickCount=false;
            }
        });
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==view.findViewById(R.id.Myprofilebutton)){
                    getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
                }
                else if(v==view.findViewById(R.id.Myclinic_button)){
                    myprofile.setPressed(false);
                    righticon.setVisibility(View.INVISIBLE);
                    addimageIcon.setVisibility(View.INVISIBLE);
                    emptyimageview.setVisibility(View.VISIBLE);
                    editicon.setVisibility(View.VISIBLE);
                    getFragmentManager().beginTransaction().replace(R.id.container2,new MyclinicRecyclerView()).commit();
                    if(clickCount){
                       // changepassword.setPressed(false);
                        getFragmentManager().beginTransaction().replace(R.id.container2,new MyprofileEditFragment()).commit();
                        addimageIcon.setVisibility(View.VISIBLE);
                        righticon.setVisibility(View.VISIBLE);
                        editicon.setVisibility(View.INVISIBLE);
                        emptyimageview.setVisibility(View.INVISIBLE);
                       // myprofile.setPressed(true);

                    }
                    clickCount=false;
                }
                else if (v==view.findViewById(R.id.changepasswordbutton)){
                    myprofile.setPressed(false);
                    righticon.setVisibility(View.INVISIBLE);
                    addimageIcon.setVisibility(View.INVISIBLE);
                    emptyimageview.setVisibility(View.VISIBLE);
                    editicon.setVisibility(View.VISIBLE);
                    //righticon.setEnabled(false);
                    getFragmentManager().beginTransaction().replace(R.id.container2,new MyProfileChangePasswordFragment()).commit();
                    if (clickCount){
                        //myclinic.setPressed(false);
                        getFragmentManager().beginTransaction().replace(R.id.container2,new MyprofileEditFragment()).commit();
                        addimageIcon.setVisibility(View.VISIBLE);
                        righticon.setVisibility(View.VISIBLE);
                        editicon.setVisibility(View.INVISIBLE);
                        emptyimageview.setVisibility(View.INVISIBLE);
                       // myprofile.setPressed(true);
                    }
                    clickCount=false;
                }
            }
        };
        myprofile.setOnClickListener(listener);
        changepassword.setOnClickListener(listener);
        myclinic.setOnClickListener(listener);
        return view;
    }

    public void editIcon() {


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