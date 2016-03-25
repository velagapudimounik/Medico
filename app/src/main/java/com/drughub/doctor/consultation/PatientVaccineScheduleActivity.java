package com.drughub.doctor.consultation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

import java.util.ArrayList;

class ItemVaccineSchedule
{
    public String diseaseName;
    public String scheduleDate;
    public int type;
    public ItemVaccineSchedule(String disease, String date, int type)
    {
        this.diseaseName = disease;
        this.scheduleDate = date;
        this.type = type;
    }
}

public class PatientVaccineScheduleActivity extends BaseActivity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultation_patient_vaccine_schedule);

        setTitle("Patient Name");//getString(R.string.consultations));
        setBackButton(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.patient_vaccine_schedule_list);
        mRecyclerView.setHasFixedSize(true);

        //mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this ,LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<ItemVaccineSchedule> mDataSet = new ArrayList<>();

        for(int i=0; i<10; i++)
        {
            ItemVaccineSchedule item = new ItemVaccineSchedule("Disease Name"+i+" | Cycle-"+i, "24 Feb 2016 to 28 Feb 2016", 1);
            mDataSet.add(item);
        }

        VaccineScheduleListAdapter mAdapter = new VaccineScheduleListAdapter(mDataSet, this);
        mRecyclerView.setAdapter(mAdapter);


    }

    public static class VaccineScheduleListAdapter extends RecyclerView.Adapter<VaccineScheduleListAdapter.ViewHolder>
    {
        private ArrayList<ItemVaccineSchedule> mDataSet;
        static FragmentActivity sContext;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView diseaseDetails;
            private final TextView scheduleDetails;
            private View mItemView;

            public ViewHolder(View v)
            {
                super(v);

                mItemView = v;

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        sContext.startActivity(new Intent(sContext, AvailableInventoryActivity.class));
                    }
                });

                diseaseDetails = (TextView) v.findViewById(R.id.diseaseDetails);
                scheduleDetails = (TextView) v.findViewById(R.id.scheduleDetails);
            }

            public void setItemSelected(boolean selected)
            {
                mItemView.setSelected(selected);
            }

            public void setItemDetails(ItemVaccineSchedule item)
            {
                diseaseDetails.setText(item.diseaseName);
                scheduleDetails.setText(item.scheduleDate);
            }
        }

        public VaccineScheduleListAdapter(ArrayList<ItemVaccineSchedule> dataSet, FragmentActivity context)
        {
            mDataSet = dataSet;
            sContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.consultation_patient_vaccine_schedule_item, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position)
        {
            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.setItemDetails(mDataSet.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDataSet.size();
        }
    }

}
