package com.drughub.doctor.mycalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drughub.doctor.R;
import com.drughub.doctor.consultation.PatientVaccineScheduleActivity;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;

class ItemAvailability
{
    public String clinicName;
    public ItemAvailability(String name)
    {
        clinicName = name;
    }

}

public class MyCalendarAvailabilityList extends Fragment {

    RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mycalendar_availability_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_calendar_available_list);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<ItemAvailability> mDataSet = new ArrayList<>();

        for(int i=0; i<10; i++)
        {
            ItemAvailability item = new ItemAvailability("Clinic Name"+i);
            mDataSet.add(item);
        }

        AvailabilityListAdapter mAdapter = new AvailabilityListAdapter(mDataSet, getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    public static class AvailabilityListAdapter extends RecyclerView.Adapter<AvailabilityListAdapter.ViewHolder>
    {
        private ArrayList<ItemAvailability> mDataSet;
        static FragmentActivity sContext;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private View mItemView;

            public ViewHolder(View v)
            {
                super(v);

                mItemView = v;

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                    }
                });

                textView = (TextView) v.findViewById(R.id.clinicDetails);
            }

            public void setItemSelected(boolean selected)
            {
                mItemView.setSelected(selected);
            }

            public void setItemDetails(ItemAvailability item)
            {
//                textView.setText(item.name);
            }
        }

        public AvailabilityListAdapter(ArrayList<ItemAvailability> dataSet, FragmentActivity context)
        {
            mDataSet = dataSet;
            sContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.mycalendar_availability_item, viewGroup, false);

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