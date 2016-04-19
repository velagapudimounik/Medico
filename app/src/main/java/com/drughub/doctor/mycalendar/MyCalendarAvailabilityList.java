package com.drughub.doctor.mycalendar;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.model.ClinicCalendar;
import com.drughub.doctor.model.ConsultationTiming;
import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;
import com.drughub.doctor.utils.CustomDialog;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyCalendarAvailabilityList extends Fragment {

    private Realm realm;
    View mView;
    RecyclerView mRecyclerView;
    EditText mCalendarSearch;
    private RealmResults<ClinicCalendar> calendarList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mycalendar_availability_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mView = view;

        mCalendarSearch = (EditText) view.findViewById(R.id.myCalendarAvailabilitySearch);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_calendar_available_list);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        realm = Realm.getDefaultInstance();
        calendarList = realm.where(ClinicCalendar.class).findAll();

        AvailabilityListAdapter mAdapter = new AvailabilityListAdapter(calendarList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        updateView();
    }

    public void updateView()
    {
        if(calendarList.size() == 0)
            mView.setVisibility(View.GONE);
        else
            mView.setVisibility(View.VISIBLE);

        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public class AvailabilityListAdapter extends RecyclerSwipeAdapter<AvailabilityListAdapter.ViewHolder>
    {
        private List<ClinicCalendar> mDataSet;
        FragmentActivity sContext;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView clinicDetails;
            private final TextView timeSlots;
            SwipeLayout swipeLayout;
            View rescheduleBtn;
            View deleteBtn;
            View settingsBtn;
            View expandBtn;
            TextView moreInfo;

            public ViewHolder(View v)
            {
                super(v);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        swipeLayout.close(true);
                    }
                });

                clinicDetails = (TextView) v.findViewById(R.id.clinicDetails);
                timeSlots = (TextView) v.findViewById(R.id.timeSlots);
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

                expandBtn = v.findViewById(R.id.expandBtn);
                expandBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (moreInfo.getVisibility() != View.VISIBLE) {
                            moreInfo.setVisibility(View.VISIBLE);
                            expandBtn.setRotation(180);
                        } else {
                            moreInfo.setVisibility(View.GONE);
                            expandBtn.setRotation(0);
                        }
                    }
                });

                moreInfo = (TextView) v.findViewById(R.id.moreInfo);
            }

            public void setItemDetails(ClinicCalendar item)
            {
                if(item.getClinic().getAddress() == null || item.getClinic().getAddress().getStreetName() == null)
                    clinicDetails.setText(item.getClinic().getClinicName());
                else
                    clinicDetails.setText(item.getClinic().getClinicName() + " | " + item.getClinic().getAddress().getStreetName());

                Map<String, String> dayTimeMap = new HashMap<>();
                for (ConsultationTiming timeSlot: item.getConsultationTimings()) {
                    String slot = Globals.to12HourFormat(timeSlot.getFromTime()) + " to " + Globals.to12HourFormat(timeSlot.getToTime());
                    if (dayTimeMap.containsKey(timeSlot.getDayOfWeek()))
                        slot = dayTimeMap.get(timeSlot.getDayOfWeek()) + " | " + slot;

                    dayTimeMap.put(timeSlot.getDayOfWeek(), slot);
                }

                String currentDay = Globals.getCurrentDayOfWeek();
                timeSlots.setText("Today : ");
                if(dayTimeMap.containsKey(currentDay))
                    timeSlots.append(dayTimeMap.get(currentDay));
                else
                    timeSlots.append("Not Available");

                moreInfo.setText("");
                for(Map.Entry<Integer, String> dayOfWeek : MyCalendarActivity.sDaysOfWeek.entrySet()) {
                    if(moreInfo.getText().length() > 0)
                        moreInfo.append("\n");

                    if(dayTimeMap.containsKey(dayOfWeek.getValue()))
                        moreInfo.append(dayOfWeek.getValue()+" : "+dayTimeMap.get(dayOfWeek.getValue()));
                    else
                        moreInfo.append(dayOfWeek.getValue()+" : Not Available");
                }

                 moreInfo.setVisibility(View.GONE);
                expandBtn.setRotation(0);
            }
        }

        public AvailabilityListAdapter(List<ClinicCalendar> dataSet, FragmentActivity context)
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

            viewHolder.rescheduleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MyCalendarActivity) getActivity()).loadAndShowEditCalendarDialog(mDataSet.get(position).getClinicId());
                    mItemManger.closeAllItems();
                }
            });

            viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = CustomDialog.showQuestionDialog((BaseActivity) sContext, sContext.getResources().getString(R.string.deleteCalendarMessage));

                    View noBtn = dialog.findViewById(R.id.dialogNoBtn);
                    noBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            mItemManger.closeAllItems();
                        }
                    });

                    View yesBtn = dialog.findViewById(R.id.dialogYesBtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                            Globals.DELETE(Urls.CLINIC + "/" + mDataSet.get(position).getClinicId() + Urls.CALENDAR, null, new Globals.VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    try {
                                        JSONObject object = new JSONObject(result);
                                        if (object.getBoolean("result")) {
                                            realm.beginTransaction();
                                            mDataSet.remove(position);
                                            realm.commitTransaction();

                                            mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                                            notifyItemRemoved(position);
                                            notifyItemRangeChanged(position, mDataSet.size());
                                            mItemManger.closeAllItems();

                                            if(mDataSet.size() == 0)
                                                mView.setVisibility(View.GONE);
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFail(String result) {
                                }
                            }, "");
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
