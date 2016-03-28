package com.drughub.doctor.mycalendar;

import android.app.Dialog;
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

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.consultation.PatientVaccineScheduleActivity;
import com.drughub.doctor.utils.CustomDialog;
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

    public static class AvailabilityListAdapter extends RecyclerSwipeAdapter<AvailabilityListAdapter.ViewHolder>
    {
        private ArrayList<ItemAvailability> mDataSet;
        static FragmentActivity sContext;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private View mItemView;
            SwipeLayout swipeLayout;
            View rescheduleBtn;
            View deleteBtn;
            View settingsBtn;

            public ViewHolder(View v)
            {
                super(v);

                mItemView = v;

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        swipeLayout.close(true);
                    }
                });

                textView = (TextView) v.findViewById(R.id.clinicDetails);
                swipeLayout = (SwipeLayout) v.findViewById(R.id.swipe);
                swipeLayout.setSwipeEnabled(false);
                settingsBtn = v.findViewById(R.id.settingsBtn);
                settingsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        swipeLayout.open(true);
                    }
                });
                rescheduleBtn = v.findViewById(R.id.reschedule);
                deleteBtn = v.findViewById(R.id.deleteCalendar);
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
        public void onBindViewHolder(final ViewHolder viewHolder, final int position)
        {
            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.setItemDetails(mDataSet.get(position));

            viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = CustomDialog.showQuestionDialog((BaseActivity) sContext, sContext.getResources().getString(R.string.deleteCalendarMessage));

                    View noBtn = dialog.findViewById(R.id.dialogNoBtn);
                    noBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    View yesBtn = dialog.findViewById(R.id.dialogYesBtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                            mDataSet.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mDataSet.size());
                            mItemManger.closeAllItems();
                        }
                    });
                }
            });


            mItemManger.bindView(viewHolder.itemView, position);
        }

        @Override
        public int getItemCount()
        {
            return mDataSet.size();
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }
    }
}
