package com.drughub.doctor.consultation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

import java.util.ArrayList;

class ItemVaccineSchedule {
    public String diseaseName;
    public String scheduleDate;
    public int type;

    public ItemVaccineSchedule(String disease, String date, int type) {
        this.diseaseName = disease;
        this.scheduleDate = date;
        this.type = type;
    }
}

public class PatientVaccineScheduleActivity extends BaseActivity {

    RecyclerView mRecyclerView;
    int clicked_type = 0;
    String upcoming_check ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultation_patient_vaccine_schedule);
        RadioButton missed = (RadioButton) findViewById(R.id.missedSchedule);
        RadioButton administeredSchedules = (RadioButton) findViewById(R.id.administeredSchedules);
        RadioButton today_schedule = (RadioButton) findViewById(R.id.todaysSchedule);

        String today = getIntent().getStringExtra("today");
        upcoming_check = getIntent().getStringExtra("check");
        //Log.i("today" , today);
        if (today.equals("today")) {
            missed.setVisibility(View.GONE);
            administeredSchedules.setVisibility(View.GONE);
            today_schedule.setVisibility(View.VISIBLE);
        } else {
            missed.setVisibility(View.VISIBLE);
            administeredSchedules.setVisibility(View.VISIBLE);
        }


        setTitle("Patient Name");
        setBackButton(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.patient_vaccine_schedule_list);
        mRecyclerView.setHasFixedSize(true);

        //mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<ItemVaccineSchedule> mDataSet = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            String value = "<font color=\"#ff5722\">Cycle -" + i + "</font>";
            ItemVaccineSchedule item = new ItemVaccineSchedule("Disease Name " + i + " | " + value, "24 Feb 2016 to 28 Feb 2016", 1);
            mDataSet.add(item);
        }

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.toggle);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.todaysSchedule:
                        Log.i("Today", "Todayschedule");
                        clicked_type = 0;

                        break;
                    case R.id.upcomingSchedule:
                        Log.i("upcoming", "upcoming");
                        clicked_type = 1;

                        break;
                    case R.id.missedSchedule:
                        Log.i("missed", "missedSchedule");
                        clicked_type = 2;
                        break;
                    case R.id.administeredSchedule:
                        Log.i("administrated", "administrated_schedule");
                        clicked_type = 3;
                        break;
                }
                VaccineScheduleListAdapter mAdapter = new VaccineScheduleListAdapter(mDataSet, PatientVaccineScheduleActivity.this, clicked_type, upcoming_check);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
        VaccineScheduleListAdapter mAdapter = new VaccineScheduleListAdapter(mDataSet, PatientVaccineScheduleActivity.this, clicked_type, upcoming_check);
        mRecyclerView.setAdapter(mAdapter);


    }

    public static class VaccineScheduleListAdapter extends RecyclerView.Adapter<VaccineScheduleListAdapter.ViewHolder> {
        private ArrayList<ItemVaccineSchedule> mDataSet;
        static FragmentActivity sContext;
        int clicked;
        String local_check;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView diseaseDetails;
            private final TextView scheduleDetails;
            private final com.drughub.doctor.utils.DrughubIcon upcoming_icon;
            private final TextView upcoming_text;
            private View mItemView;


            public ViewHolder(View v) {
                super(v);

                mItemView = v;

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sContext.startActivity(new Intent(sContext, AvailableInventoryActivity.class));
                    }
                });

                diseaseDetails = (TextView) v.findViewById(R.id.diseaseDetails);
                scheduleDetails = (TextView) v.findViewById(R.id.scheduleDetails);
                upcoming_icon = (com.drughub.doctor.utils.DrughubIcon) v.findViewById(R.id.inventory_icon);
                upcoming_text = (TextView) v.findViewById(R.id.inventory);
            }

            public void setItemSelected(boolean selected) {
                mItemView.setSelected(selected);
            }

            public void setItemDetails(ItemVaccineSchedule item, int clicked, String check) {
                diseaseDetails.setText(Html.fromHtml(item.diseaseName));
                scheduleDetails.setText(item.scheduleDate);
                Log.i("upcoming_Value",check);
                if (clicked == 1) {
                    if (check.equals("true")) {
                        upcoming_text.setText("Booked");
                        upcoming_icon.setText(sContext.getString(R.string.icon_reschedule));
                    }
                }
            }
        }

        public VaccineScheduleListAdapter(ArrayList<ItemVaccineSchedule> dataSet, FragmentActivity context, int clicked_type, String check) {
            mDataSet = dataSet;
            sContext = context;
            clicked = clicked_type;
            local_check = check;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.consultation_patient_vaccine_schedule_item, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.setItemDetails(mDataSet.get(position), clicked, local_check);
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
    }

}
