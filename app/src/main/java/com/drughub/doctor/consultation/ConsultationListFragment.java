package com.drughub.doctor.consultation;

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

import java.util.ArrayList;

class ItemConsultation
{
    public String name;
    public String vaccine;
    public String clinic;
    public String date;
    public ItemConsultation(String name, String consumed, String clinic, String date)
    {
        this.name = name;
        this.vaccine = consumed;
        this.clinic = clinic;
        this.date = date;
    }
}

public class ConsultationListFragment extends Fragment
{
    RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.consultation_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.consultation_list);
        mRecyclerView.setHasFixedSize(true);

        //mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<ItemConsultation> mDataSet = new ArrayList<>();

        for(int i=0; i<10; i++)
        {
            ItemConsultation item = new ItemConsultation("Name"+i, "Vaccine Name", "Clinic", "Date");
            mDataSet.add(item);
        }

        ConsultationListAdapter mAdapter = new ConsultationListAdapter(mDataSet, getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    public static class ConsultationListAdapter extends RecyclerView.Adapter<ConsultationListAdapter.ViewHolder>
    {
        private ArrayList<ItemConsultation> mDataSet;
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
                        sContext.startActivity(new Intent(sContext, PatientVaccineScheduleActivity.class));
                    }
                });

                textView = (TextView) v.findViewById(R.id.consultationPatientDetails);
            }

            public void setItemSelected(boolean selected)
            {
                mItemView.setSelected(selected);
            }

            public void setItemDetails(ItemConsultation item)
            {
                textView.setText(item.name);
            }
        }

        public ConsultationListAdapter(ArrayList<ItemConsultation> dataSet, FragmentActivity context)
        {
            mDataSet = dataSet;
            sContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.consultation_item, viewGroup, false);

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
