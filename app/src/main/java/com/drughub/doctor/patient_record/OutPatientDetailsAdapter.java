package com.drughub.doctor.patient_record;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.drughub.doctor.R;

import java.util.ArrayList;

/**
 * Created by Deepak on 3/22/2016.
 */
public class OutPatientDetailsAdapter extends RecyclerView.Adapter<OutPatientDetailsAdapter.DataObjectHolder> {
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
                Toast.makeText(context, "Add new", Toast.LENGTH_SHORT).show();
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
