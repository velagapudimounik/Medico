package com.drughub.doctor.patient_record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.DrughubIcon;

/**
 * Created by Deepak on 3/22/2016.
 */
public class ImageFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Out Patient Prescription");
        ((BaseActivity) getActivity()).setBackButton(true);

        final View view = inflater.inflate(R.layout.fragment_imagescreen, container, false);
        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        DrughubIcon download = (DrughubIcon) view.findViewById(R.id.download);
        DrughubIcon favorite = (DrughubIcon) view.findViewById(R.id.favorite);

        download.setOnClickListener(this);
        favorite.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download:
                Toast.makeText(getActivity(), "Download Image", Toast.LENGTH_SHORT).show();
                break;
            case R.id.favorite:
                Toast.makeText(getActivity(), "Favourite Image", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
