package com.drughub.doctor.MyProfile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.drughub.doctor.R;
import com.drughub.doctor.utils.DrughubIcon;

import java.util.zip.Inflater;

public class MyprofileEditFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.my_profile_title));
       final View view= inflater.inflate(R.layout.myprofile_edit_fragment,container,false);
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==view.findViewById(R.id.UpdateButton)){
                    getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).addToBackStack(null).commit();
                    MyProfileActivityFragment fragment_frag=(MyProfileActivityFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.containeractivity);
                    fragment_frag.editIcon();
                }
            }
        };
        Button update=(Button)view.findViewById(R.id.UpdateButton);
        update.setOnClickListener(listener);
        return view;
        }


}
