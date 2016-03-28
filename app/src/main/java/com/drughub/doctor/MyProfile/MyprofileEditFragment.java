package com.drughub.doctor.MyProfile;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.drughub.doctor.R;

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
                    getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
                }
            }
        };
        Button update=(Button)view.findViewById(R.id.UpdateButton);
        update.setOnClickListener(listener);
        return view;
        }
}
