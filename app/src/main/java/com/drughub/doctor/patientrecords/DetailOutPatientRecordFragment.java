package com.drughub.doctor.patientrecords;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;

import java.util.ArrayList;

public class DetailOutPatientRecordFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.outpatientPrescription));
        ((BaseActivity) getActivity()).setBackButton(true);

        final View view = inflater.inflate(R.layout.patient_record_out_patient_details_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.out_patient_grid);
        recyclerView.hasFixedSize();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        ArrayList<String> image_urls = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            image_urls.add("" + i);
        }
        image_urls.add("dummy");

        OutPatientDetailsAdapter adapter = new OutPatientDetailsAdapter(getActivity(), image_urls);
        recyclerView.setAdapter(adapter);


        return view;

    }
}

class OutPatientDetailsAdapter extends RecyclerView.Adapter<OutPatientDetailsAdapter.DataObjectHolder> {
    Context context;
    ArrayList<String> imageUrls;

    public OutPatientDetailsAdapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        if (imageUrl.equalsIgnoreCase("dummy")) {
            holder.image.setImageResource(R.drawable.plus);
        } else {
            holder.image.setImageResource(R.drawable.mahesh);
        }
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;

        public DataObjectHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (imageUrls.get(getPosition()).equalsIgnoreCase("dummy")) {
                final Dialog dialog = CustomDialog.showCustomDialog((BaseActivity) context, R.layout.patient_record_upload_dialog,
                        Gravity.CENTER, true, false, true);
            } else {
                PatientRecordActivity activity = (PatientRecordActivity) context;
                Bundle mBundle = new Bundle();
                mBundle.putString("image_url", imageUrls.get(getPosition()));
                ImageFragment fragment = new ImageFragment();
                fragment.setArguments(mBundle);
                activity.changeFragment(fragment);
            }

        }
    }
}
