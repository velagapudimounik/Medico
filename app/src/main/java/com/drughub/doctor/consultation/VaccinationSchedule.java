package com.drughub.doctor.consultation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.util.Attributes;
import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.Notification.NotificationActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;

public class VaccinationSchedule extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccination_schedule);
        setTitle("Vaccination Schedule");
        setBackButton(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        VaccinationScheduleAdapter adapter = new VaccinationScheduleAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        View doneBtn = findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
                Intent intent = new Intent(getApplicationContext(),PatientVaccineScheduleActivity.class);
                startActivity(intent);

            }
        });

        addActionButton(R.string.icon_notification);
    }

    public void onActionButtonClicked(int drughubIconRes)
    {
        super.onActionButtonClicked(drughubIconRes);

        Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
        startActivity(intent);
    }

    public class VaccinationScheduleAdapter extends RecyclerSwipeAdapter<VaccinationScheduleAdapter.ViewHolder> {

        Context context;

        VaccinationScheduleAdapter(Context context) {
            this.context = context;
            setMode(Attributes.Mode.Multiple);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccination_schedule_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.missedSchedule);
            mItemManger.bindView(viewHolder.itemView, position);
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            SwipeLayout swipeLayout;
            View administeredSchedule;
            View missedSchedule;

            public ViewHolder(View v)
            {
                super(v);
                swipeLayout = (SwipeLayout) v.findViewById(R.id.swipe);
                administeredSchedule = v.findViewById(R.id.administeredSchedule);
                missedSchedule = v.findViewById(R.id.missedSchedule);
            }
        }
    }
}












