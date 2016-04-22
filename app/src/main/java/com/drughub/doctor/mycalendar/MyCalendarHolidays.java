package com.drughub.doctor.mycalendar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.drughub.doctor.R;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyCalendarHolidays extends Fragment {
    RecyclerView mRecyclerView;
    MaterialCalendarView mCalendarView;

    CalendarDay selectedDay = null;
    CalendarDay currentDay = null;
    List<CalendarDay> holidayList = new ArrayList<>();
    private SelectedDayDecorator selectedDayDecorator;
    private HolidayDecorator holidayDecorator;
    private CurrentWeekDecorator currentWeekDecorator;
    private CurrentDayDecorator currentDayDecorator;

    View clinicsView;
    View editView;
    View submitButton;
    View deleteButton;
    View saveButton;

    boolean refresh = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mycalendar_myholidays, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.myHolidaysClinicList);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<String> mDataSet = new ArrayList<>();
        ArrayList<Boolean> mDataSetChecked = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            mDataSet.add("Clinic " + i);
            mDataSetChecked.add(false);
        }

        ClinicListAdapter mAdapter = new ClinicListAdapter(mDataSet, mDataSetChecked, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        clinicsView = view.findViewById(R.id.myHolidaysClinicsView);
        clinicsView.setVisibility(View.VISIBLE);

        editView = view.findViewById(R.id.myHolidaysEditView);
        editView.setVisibility(View.VISIBLE);

        submitButton = view.findViewById(R.id.submitButton);
        submitButton.setVisibility(View.VISIBLE);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holidayList.add(selectedDay);
                refresh = true;
                mCalendarView.invalidateDecorators();
                editView.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.GONE);
            }
        });

        deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holidayList.remove(selectedDay);
                refresh = true;
                mCalendarView.invalidateDecorators();
                editView.setVisibility(View.GONE);
                submitButton.setVisibility(View.VISIBLE);
            }
        });

        saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mCalendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);

        ViewGroup topBar = (ViewGroup) mCalendarView.getChildAt(0); //getting top bar
        topBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        topBar.setPadding(pixels, 0, pixels, 0);

        View rightArrow = topBar.getChildAt(2); //getting right arrow
        rightArrow.setRotation(180);

        currentDay = CalendarDay.today();
        currentWeekDecorator = new CurrentWeekDecorator();
        currentDayDecorator = new CurrentDayDecorator();
        holidayDecorator = new HolidayDecorator(holidayList);
        selectedDayDecorator = new SelectedDayDecorator();

        mCalendarView.addDecorators(
                new MySelectorDecorator(),
                currentWeekDecorator,
                currentDayDecorator,
                holidayDecorator,
                selectedDayDecorator
        );

        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {

            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                if (currentWeekDecorator.isDecorated(date) || holidayDecorator.isDecorated(date)) {
                    refresh = true;
                    selectedDay = date;
                    mCalendarView.invalidateDecorators();
                } else if (refresh) {
                    refresh = false;
                    selectedDay = null;
                    mCalendarView.invalidateDecorators();
                }

                selectedDay = date;

                if(holidayDecorator.isDecorated(selectedDay)) {
                    clinicsView.setVisibility(View.VISIBLE);
                    if (currentDay.isAfter(selectedDay)) {
                        editView.setVisibility(View.GONE);
                        submitButton.setVisibility(View.GONE);
                    }
                    else {
                        editView.setVisibility(View.VISIBLE);
                        submitButton.setVisibility(View.GONE);
                    }
                }
                else {
                    if (currentDay.isAfter(selectedDay)) {
                        clinicsView.setVisibility(View.GONE);
                        editView.setVisibility(View.GONE);
                        submitButton.setVisibility(View.GONE);
                    }
                    else {
                        clinicsView.setVisibility(View.VISIBLE);
                        editView.setVisibility(View.GONE);
                        submitButton.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    public class MySelectorDecorator implements DayViewDecorator {

        private final Drawable drawable;

        public MySelectorDecorator() {
            drawable = ContextCompat.getDrawable(getActivity(), R.drawable.my_selector);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return true;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(drawable);
        }
    }

    public class CurrentWeekDecorator implements DayViewDecorator {

        private final Calendar calendar = Calendar.getInstance();
        private final Drawable highlightDrawable;
        private final int year;
        private final int week;

        public CurrentWeekDecorator() {
            highlightDrawable = new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.colorDecoration));
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            year = calendar.get(Calendar.YEAR);
            week = calendar.get(Calendar.WEEK_OF_YEAR);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            day.copyTo(calendar);
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            return year == calendar.get(Calendar.YEAR) && week == calendar.get(Calendar.WEEK_OF_YEAR)
                    && !day.equals(selectedDay);
        }

        public boolean isDecorated(CalendarDay day) {
            day.copyTo(calendar);
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
            return year == calendar.get(Calendar.YEAR) && week == calendar.get(Calendar.WEEK_OF_YEAR);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(highlightDrawable);
        }
    }

    public class CurrentDayDecorator implements DayViewDecorator {

        private final CalendarDay calendarDay;
        private final Drawable highlightDrawable;

        public CurrentDayDecorator() {
            highlightDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.my_holidays_current_day_bkg);
            calendarDay = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(calendarDay) && !day.equals(selectedDay);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(highlightDrawable);
        }
    }

    public class HolidayDecorator implements DayViewDecorator {

        private List<CalendarDay> days = null;
        private final Drawable highlightDrawable;

        public HolidayDecorator(List<CalendarDay> days) {
            highlightDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.my_holiday_bkg);
            this.days = days;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return days != null && days.contains(day) && !day.equals(selectedDay);
        }

        public boolean isDecorated(CalendarDay day) {
            return days != null && days.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(highlightDrawable);
        }

        /**
         * We're changing the internals, so make sure to call {@linkplain MaterialCalendarView#invalidateDecorators()}
         */
        public void setDays(List<CalendarDay> days) {
            this.days = days;
        }

        public void addDay(CalendarDay day)
        {
            if(!days.contains(day))
                days.add(day);
        }
    }

    public class SelectedDayDecorator implements DayViewDecorator {
        private final Drawable highlightDrawable;

        public SelectedDayDecorator() {
            highlightDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.fill_round_rect_orange);
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return day.equals(selectedDay);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(highlightDrawable);
        }
    }


    public static class ClinicListAdapter extends RecyclerView.Adapter<ClinicListAdapter.ViewHolder> {
        static FragmentActivity sContext;
        private ArrayList<String> mDataSet;
        private ArrayList<Boolean> mDataSetChecked;

        public ClinicListAdapter(ArrayList<String> dataSet, ArrayList<Boolean> dataSetChecked, FragmentActivity context) {
            mDataSet = dataSet;
            mDataSetChecked = dataSetChecked;
            sContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.mycalendar_myholidays_clinic_item, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            viewHolder.setItemDetails(mDataSet.get(position), mDataSetChecked.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            CheckBox checkBox;

            public ViewHolder(View v) {
                super(v);
                textView = (TextView) v.findViewById(R.id.clinicDetails);
                checkBox = (CheckBox) v.findViewById(R.id.clinicSelected);
            }

            public void setItemDetails(String name, Boolean checked) {
                textView.setText(name);
                checkBox.setChecked(checked);
            }
        }
    }
}
