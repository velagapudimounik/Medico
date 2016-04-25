package com.drughub.doctor.mycalendar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.Notification.NotificationActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.model.ClinicCalendar;
import com.drughub.doctor.model.ConsultationTiming;
import com.drughub.doctor.model.DoctorClinic;
import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;
import com.drughub.doctor.utils.CustomDialog;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

enum CLOCK_PICKER {
    HOURS,
    MINUTES,
    MERIDIEM,
}

public class MyCalendarActivity extends BaseActivity {

    private TextView non_working_days_txt;
    private CheckBox day_wise;
    private RecyclerView mRecyclerView;
    private int from_Hour, from_Minute = 1, to_Hour, to_Minute = 1;
    private String fromMeridian = "AM", toMeridian = "AM";
    //private CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private CalenderAdapter calenderAdapter;
    private ClinicCalendar mClinicCalendar;
    private ClinicCalendar mClinicCalendarBkp;
    private List<DoctorClinic> clinicList;
    private MyCalendarAvailabilityList myCalendarAvailabilityList = null;
    private boolean clinicsLoaded = false;
    private boolean addClinicCalendar;
    private int selectedClinic;
    private String selectedDay;
    private Dialog dialog = null;

    public static final Map<Integer, String> sDaysOfWeek;
    static
    {
        sDaysOfWeek = new LinkedHashMap<>();
        sDaysOfWeek.put(R.id.monday, "Monday");
        sDaysOfWeek.put(R.id.tuesday, "Tuesday");
        sDaysOfWeek.put(R.id.wednesday ,"Wednesday");
        sDaysOfWeek.put(R.id.thursday, "Thursday");
        sDaysOfWeek.put(R.id.friday, "Friday");
        sDaysOfWeek.put(R.id.saturday, "Saturday");
        sDaysOfWeek.put(R.id.sunday, "Sunday");
    }

    public static NumberPicker initNumberPicker(NumberPicker numberPicker, CLOCK_PICKER pickerType) {
        String[] displayValues = null;

        switch (pickerType) {
            case HOURS: {
                displayValues = new String[12];
                for (int i = 0; i < 12; i++)
                    displayValues[i] = String.format("%02d", i+1);

                numberPicker.setMaxValue(12);
                numberPicker.setMinValue(1);
            }
            break;
            case MINUTES: {
                displayValues = new String[60];
                for (int i = 0; i < 60; i++)
                    displayValues[i] = String.format("%02d", i);

                numberPicker.setMaxValue(59);
                numberPicker.setMinValue(0);
            }
            break;
            case MERIDIEM: {
                displayValues = new String[2];
                displayValues[0] = "AM";
                displayValues[1] = "PM";

                numberPicker.setMaxValue(1);
                numberPicker.setMinValue(0);
            }
            break;
        }

        numberPicker.setDisplayedValues(displayValues);
        setNumberPickerTextColor(numberPicker, 0xFFFFFFFF);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        return numberPicker;
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Class<?> numberPickerClass = numberPicker.getClass();
                    Field selectorWheelPaintField = numberPickerClass.getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    child.setPadding(0, 0, 0, 0);

                    Field selectionDivider = numberPickerClass.getDeclaredField("mSelectionDivider");
                    selectionDivider.setAccessible(true);
                    selectionDivider.set(numberPicker, null);

                    numberPicker.invalidate();
                    return true;
                } catch (Exception e) {
                    Log.w("setNumberPickerColor", e);
                }
            }
        }
        return false;
    }

    Realm realm;

    @Override
    public void onActionButtonClicked(int drughubIconsRes) {
        super.onActionButtonClicked(drughubIconsRes);

        Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycalendar_activity);

        setTitle(getString(R.string.my_calendar));
        setBackButton(true);
        addActionButton(R.string.icon_notification);

        if(myCalendarAvailabilityList == null)
            myCalendarAvailabilityList = new MyCalendarAvailabilityList();

        realm = Realm.getDefaultInstance();

        Globals.GET(Urls.CLINIC + Urls.CALENDAR, new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getBoolean("result")) {
                        realm.beginTransaction();
                        realm.createOrUpdateAllFromJson(ClinicCalendar.class, object.getJSONArray("response"));
                        realm.commitTransaction();

                        getSupportFragmentManager().beginTransaction().replace(R.id.my_calendar_container, myCalendarAvailabilityList).commit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {
            }
        }, "");

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.availability)
                    getSupportFragmentManager().beginTransaction().replace(R.id.my_calendar_container, myCalendarAvailabilityList).commit();
                else if (checkedId == R.id.booked)
                    getSupportFragmentManager().beginTransaction().replace(R.id.my_calendar_container, new MyCalendarBookedList()).commit();
                else if (checkedId == R.id.myHolidays)
                    getSupportFragmentManager().beginTransaction().replace(R.id.my_calendar_container, new MyCalendarHolidays()).commit();
            }
        });
    }

    void addConsultationTiming(String fromTime, String toTime)
    {
        realm.beginTransaction();
        ConsultationTiming timing = realm.createObject(ConsultationTiming.class);
        timing.setFromTime(Globals.to24HourFormat(fromTime));
        timing.setToTime(Globals.to24HourFormat(toTime));
        timing.setClinicId(selectedClinic);
        timing.setDayOfWeek(selectedDay);
        mClinicCalendar.getConsultationTimings().add(timing);
        realm.commitTransaction();

        calenderAdapter.refresh();
    }

    int prevPosition = -1;

    public void loadAndShowEditCalendarDialog(final int clinic)
    {
        if(clinicsLoaded)
            showEditCalendarDialog(clinic);
        else
            Globals.GET(Urls.CLINIC, new Globals.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject object = new JSONObject(result);
                        if (object.getBoolean("result")) {
                            realm.beginTransaction();
                            realm.createOrUpdateAllFromJson(DoctorClinic.class, object.getJSONArray("response"));
                            realm.commitTransaction();

                            showEditCalendarDialog(clinic);
                            clinicsLoaded = true;
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

    public void showEditCalendarDialog(int clinic){

        selectedClinic = clinic;
        selectedDay = "Monday";
        addClinicCalendar = (selectedClinic == -1);

        prevPosition = -1;

        dialog = CustomDialog.showCustomDialog(MyCalendarActivity.this, R.layout.mycalendar_create_new,
                Gravity.BOTTOM, true, true, false);

        TextView titleText = (TextView) dialog.findViewById(R.id.titleText);
        View add_time = dialog.findViewById(R.id.add_calender);
        day_wise = (CheckBox) dialog.findViewById(R.id.date_check_box);
        non_working_days_txt = (TextView) dialog.findViewById(R.id.non_working_days);

        mRecyclerView = (RecyclerView) dialog.findViewById(R.id.date_show);
        mRecyclerView.hasFixedSize();
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layout);

        Button addBtn = (Button)dialog.findViewById(R.id.addBtn);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (addClinicCalendar) {
                    realm.beginTransaction();
                    mClinicCalendar.removeFromRealm();
                    realm.commitTransaction();
                }
                else {
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(mClinicCalendarBkp);
                    realm.commitTransaction();
                }
            }
        });

        final Spinner clinicSelection = (Spinner) dialog.findViewById(R.id.addressSelection);

        if(selectedClinic != -1)
            clinicList = realm.copyFromRealm(realm.where(DoctorClinic.class).equalTo("clinicId", selectedClinic).findAll());
        else
            clinicList = realm.copyFromRealm(realm.where(DoctorClinic.class).findAll());

        clinicList.add(null);//adding hint item
        final CustomAdapter spinnerAdapter = new CustomAdapter(getApplicationContext(), clinicList);
        clinicSelection.setAdapter(spinnerAdapter);

        if(selectedClinic != -1 || clinicList.size() == 1) {
            clinicSelection.setEnabled(false);
            clinicSelection.setSelection(0);
        }
        else {
            clinicSelection.setSelection(spinnerAdapter.getCount());
        }

        prevPosition = spinnerAdapter.getCount();

        clinicSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!addClinicCalendar)
                    return;

                if (position == spinnerAdapter.getCount() || clinicList.size() == 1)
                    return;

                DoctorClinic doctorClinic = spinnerAdapter.getItem(position);
                ClinicCalendar clinicCalendar = realm.where(ClinicCalendar.class).equalTo("clinicId", doctorClinic.getClinicId()).findFirst();

                if (clinicCalendar == null) {
                    realm.beginTransaction();
                    doctorClinic = realm.where(DoctorClinic.class).equalTo("clinicId", doctorClinic.getClinicId()).findFirst();
                    mClinicCalendar.setClinic(doctorClinic);
                    mClinicCalendar.setClinicId(doctorClinic.getClinicId());
                    realm.commitTransaction();

                    selectedClinic = mClinicCalendar.getClinicId();
                    spinnerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MyCalendarActivity.this, "Calendar already exists for this clinic", Toast.LENGTH_SHORT).show();
                    if (prevPosition != -1) {
                        clinicSelection.setSelection(prevPosition);
                        position = prevPosition;
                    }
                }

                prevPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if(addClinicCalendar) {
            realm.beginTransaction();
            mClinicCalendar = realm.createObject(ClinicCalendar.class);
            mClinicCalendar.setClinicId(selectedClinic);
            realm.commitTransaction();
        }
        else
        {
            mClinicCalendar = realm.where(ClinicCalendar.class).equalTo("clinicId", selectedClinic).findFirst();

            mClinicCalendarBkp = realm.copyFromRealm(mClinicCalendar);

            RealmResults<ConsultationTiming> timingList = null;
            boolean dayWise = false;
            for(Map.Entry<Integer, String> dayOfWeek : sDaysOfWeek.entrySet()) {
                RealmResults<ConsultationTiming> nextList = mClinicCalendar.getConsultationTimings().where().equalTo("dayOfWeek", dayOfWeek.getValue()).findAll();
                if(timingList != null && timingList.size() > 0 && nextList.size() > 0) {
                    if(timingList.size() != nextList.size()) {
                        dayWise = true;
                        break;
                    }
                    for(ConsultationTiming timeSlot : timingList) {
                        ConsultationTiming slot = nextList.where().equalTo("fromTime", timeSlot.getFromTime()).equalTo("toTime", timeSlot.getToTime()).findFirst();
                        if(slot == null) {
                            dayWise = true;
                            break;
                        }
                    }
                    if(!dayWise)
                    for(ConsultationTiming timeSlot : nextList) {
                        ConsultationTiming slot = timingList.where().equalTo("fromTime", timeSlot.getFromTime()).equalTo("toTime", timeSlot.getToTime()).findFirst();
                        if(slot == null) {
                            dayWise = true;
                            break;
                        }
                    }
                    if(dayWise)
                        break;
                } else if(nextList.size() > 0)
                    timingList = nextList;
            }

            titleText.setText("Edit Calendar");
            addBtn.setText("Edit");
            if(dayWise) {
                day_wise.setChecked(true);
                day_wise.setVisibility(View.GONE);
                non_working_days_txt.setVisibility(View.GONE);
                ((CheckBox) dialog.findViewById(R.id.monday)).setChecked(true);
            }
            else {
                for (Map.Entry<Integer, String> dayOfWeek : sDaysOfWeek.entrySet()) {
                    timingList = mClinicCalendar.getConsultationTimings().where().equalTo("dayOfWeek", dayOfWeek.getValue()).findAll();
                    if (timingList.size() == 0)
                        ((CheckBox) dialog.findViewById(dayOfWeek.getKey())).setChecked(true);
                    else
                        selectedDay = dayOfWeek.getValue();
                }
                timingList = mClinicCalendar.getConsultationTimings().where().equalTo("dayOfWeek", selectedDay).findAll();

                selectedDay = "Monday";

                List<ConsultationTiming> list = realm.copyFromRealm(timingList);
                for(ConsultationTiming timing:list)
                    timing.setDayOfWeek(selectedDay);

                realm.beginTransaction();
                mClinicCalendar.getConsultationTimings().clear();
                mClinicCalendar.getConsultationTimings().addAll(list);
                realm.commitTransaction();
            }
        }

        RealmResults<ConsultationTiming> timingList = mClinicCalendar.getConsultationTimings().where().equalTo("dayOfWeek", selectedDay).findAll();

        calenderAdapter = new CalenderAdapter(timingList, MyCalendarActivity.this);
        mRecyclerView.setAdapter(calenderAdapter);
        calenderAdapter.refresh();

        day_wise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    for(Map.Entry<Integer, String> dayOfWeek : sDaysOfWeek.entrySet()) {
                        ((CheckBox) dialog.findViewById(dayOfWeek.getKey())).setChecked(false);
                        dialog.findViewById(dayOfWeek.getKey()).setEnabled(true);
                    }
                    non_working_days_txt.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    selectedDay = "Monday";
                    ((CheckBox) dialog.findViewById(R.id.monday)).setChecked(true);
                    dialog.findViewById(R.id.monday).setEnabled(false);

                    realm.beginTransaction();
                    mClinicCalendar.getConsultationTimings().clear();
                    realm.commitTransaction();
                } else {
                    for(Map.Entry<Integer, String> dayOfWeek : sDaysOfWeek.entrySet()) {
                        ((CheckBox) dialog.findViewById(dayOfWeek.getKey())).setChecked(false);
                        dialog.findViewById(dayOfWeek.getKey()).setEnabled(true);
                    }
                    non_working_days_txt.setVisibility(View.VISIBLE);
                    selectedDay = "Monday";

                    realm.beginTransaction();
                    mClinicCalendar.getConsultationTimings().clear();
                    realm.commitTransaction();
                }

                RealmResults<ConsultationTiming> timingList = mClinicCalendar.getConsultationTimings().where().equalTo("dayOfWeek", selectedDay).findAll();
                calenderAdapter.setDataSet(timingList);
            }
        });

        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (day_wise.isChecked() && isChecked) {

                    for(Map.Entry<Integer, String> dayOfWeek : sDaysOfWeek.entrySet()) {
                        if(buttonView.getId() != dayOfWeek.getKey()) {
                            ((CheckBox) dialog.findViewById(dayOfWeek.getKey())).setChecked(false);
                            dialog.findViewById(dayOfWeek.getKey()).setEnabled(true);
                        }
                    }

                    buttonView.setEnabled(false);

                    if(sDaysOfWeek.containsKey(buttonView.getId())) {
                        selectedDay = sDaysOfWeek.get(buttonView.getId());
                        RealmResults<ConsultationTiming> timingList = mClinicCalendar.getConsultationTimings().where().equalTo("dayOfWeek", selectedDay).findAll();
                        calenderAdapter.setDataSet(timingList);
                    }
                }
            }
        };

        for(Map.Entry<Integer, String> dayOfWeek : sDaysOfWeek.entrySet()) {
            ((CheckBox) dialog.findViewById(dayOfWeek.getKey())).setOnCheckedChangeListener(listener);
        }

        add_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showEditTimeSlotDialog(null, null, new AddTimeSlotListener() {
                    @Override
                    public void onAddTimeSlot(String fromTime, String toTime) {
                        addConsultationTiming(fromTime, toTime);
                    }
                });
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mClinicCalendar.getClinicId() == -1)
                    Toast.makeText(MyCalendarActivity.this, "Select clinic to proceed", Toast.LENGTH_SHORT).show();
                else if(mClinicCalendar.getConsultationTimings().size() == 0)
                    Toast.makeText(MyCalendarActivity.this, "Add at least one time slot", Toast.LENGTH_SHORT).show();
                else
                {
                    RealmList<ConsultationTiming> timeSlots = mClinicCalendar.getConsultationTimings();
                    if (!day_wise.isChecked())
                    {
                        int nonWorkingDays = 0;
                        for(Map.Entry<Integer, String> dayOfWeek : sDaysOfWeek.entrySet()) {
                            if (((CheckBox) dialog.findViewById(dayOfWeek.getKey())).isChecked())
                                nonWorkingDays++;
                        }

                        if(nonWorkingDays == sDaysOfWeek.size()) {
                            Toast.makeText(MyCalendarActivity.this, "Select at least one working day", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<ConsultationTiming> newList = new ArrayList<>();
                        for(Map.Entry<Integer, String> dayOfWeek : sDaysOfWeek.entrySet()){
                            boolean isChecked = ((CheckBox) dialog.findViewById(dayOfWeek.getKey())).isChecked();
                            if(dayOfWeek.getKey() == R.id.monday || isChecked)
                                continue;

                            for(ConsultationTiming timing: timeSlots){
                                ConsultationTiming newTiming = new ConsultationTiming();
                                newTiming.copy(timing);
                                newTiming.setDayOfWeek(dayOfWeek.getValue());
                                newList.add(newTiming);
                            }
                        }

                        realm.beginTransaction();

                        if(((CheckBox) dialog.findViewById(R.id.monday)).isChecked())
                            timeSlots.clear();

                        for(ConsultationTiming timing: newList)
                            timeSlots.add(timing);

                        for(ConsultationTiming timing: timeSlots)
                            timing.setClinicId(mClinicCalendar.getClinicId());

                        realm.commitTransaction();
                    }

                    Globals.VolleyCallback listener = new Globals.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject object = new JSONObject(result);
                                if (object.getBoolean("result")) {
                                    myCalendarAvailabilityList.updateView();
                                    dialog.dismiss();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(String result) {
                        }
                    };

                    if (addClinicCalendar)
                        Globals.POST(Urls.CLINIC + "/" + mClinicCalendar.getClinicId() + Urls.CALENDAR, mClinicCalendar.toCreateClinicCalendar(), listener, "");
                    else
                        Globals.PUT(Urls.CLINIC + "/" + mClinicCalendar.getClinicId() + Urls.CALENDAR, mClinicCalendar.toCreateClinicCalendar(), listener, "");
                }
            }
        });
    }

    private class CustomAdapter extends ArrayAdapter<DoctorClinic> {

        public CustomAdapter(Context context, List<DoctorClinic> clinicList) {
            super(context, R.layout.custom_spinner, clinicList);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spinner, null);
            TextView clinicName = (TextView) mySpinner.findViewById(R.id.string1);
            clinicName.setText(getItem(position).getClinicName() + " | ");
            TextView address = (TextView) mySpinner.findViewById(R.id.string2);
            address.setText(getItem(position).getAddress().getStreetName());
            return mySpinner;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View mySpinner;
            if(position == getCount() || getItem(position) == null)
            {
                mySpinner = getLayoutInflater().inflate(R.layout.custom_spinner, null);
                TextView clinicName = (TextView) mySpinner.findViewById(R.id.string1);
                clinicName.setText(position==0?"No Clinics Available":"My Clinics");
                clinicName.setTextColor(Color.LTGRAY);
                ((TextView) mySpinner.findViewById(R.id.string2)).setText("");
            }
            else
                mySpinner = getDropDownView(position, convertView, parent);
            return mySpinner;
        }

        @Override
        public int getCount() {
            int count = super.getCount();
            return (count==1) ? 1 : count-1;
        }
    }

    interface AddTimeSlotListener
    {
        void onAddTimeSlot(String fromTime, String toTime);
    }

    void showEditTimeSlotDialog(String fromTime, String toTime, final AddTimeSlotListener callback)
    {
        final Dialog dialog = CustomDialog.showCustomDialog(this, R.layout.mycalender_time_picker,
                Gravity.BOTTOM, true, true, false);

        final NumberPicker hourPickerFrom = (NumberPicker) dialog.findViewById(R.id.hourPickerFrom);
        final NumberPicker minutePickerFrom = (NumberPicker) dialog.findViewById(R.id.minutePickerFrom);
        final NumberPicker dayPickerFrom = (NumberPicker) dialog.findViewById(R.id.dayPickerFrom);
        final NumberPicker hourPickerTo = (NumberPicker) dialog.findViewById(R.id.hourPickerTo);
        final NumberPicker minutePickerTo = (NumberPicker) dialog.findViewById(R.id.minutePickerTo);
        final NumberPicker dayPickerTo = (NumberPicker) dialog.findViewById(R.id.dayPickerTo);

        MyCalendarActivity.initNumberPicker(hourPickerFrom, CLOCK_PICKER.HOURS);
        MyCalendarActivity.initNumberPicker(minutePickerFrom, CLOCK_PICKER.MINUTES);
        MyCalendarActivity.initNumberPicker(dayPickerFrom, CLOCK_PICKER.MERIDIEM);
        MyCalendarActivity.initNumberPicker(hourPickerTo, CLOCK_PICKER.HOURS);
        MyCalendarActivity.initNumberPicker(minutePickerTo, CLOCK_PICKER.MINUTES);
        MyCalendarActivity.initNumberPicker(dayPickerTo, CLOCK_PICKER.MERIDIEM);

        from_Hour = Globals.getHour(fromTime);
        from_Minute = Globals.getMinute(fromTime);
        fromMeridian = Globals.getMeridian(fromTime);
        to_Hour = Globals.getHour(toTime);
        to_Minute = Globals.getMinute(toTime);
        toMeridian = Globals.getMeridian(toTime);

        hourPickerFrom.setValue(from_Hour);
        minutePickerFrom.setValue(from_Minute);
        dayPickerFrom.setValue(fromMeridian.equals("PM")?1:0);
        hourPickerTo.setValue(to_Hour);
        minutePickerTo.setValue(to_Minute);
        dayPickerTo.setValue(toMeridian.equals("PM")?1:0);

        NumberPicker.OnValueChangeListener listener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(picker == hourPickerFrom)
                    from_Hour = newVal;
                else if(picker == minutePickerFrom)
                    from_Minute = newVal;
                else if(picker == dayPickerFrom) {
                    if (newVal == 0)
                        fromMeridian = "AM";
                    else
                        fromMeridian = "PM";
                }
                else if(picker == hourPickerTo)
                    to_Hour = newVal;
                else if(picker == minutePickerTo)
                    to_Minute = newVal;
                else if(picker == dayPickerTo) {
                    if (newVal == 0)
                        toMeridian = "AM";
                    else
                        toMeridian = "PM";
                }
            }
        };

        hourPickerFrom.setOnValueChangedListener(listener);
        minutePickerFrom.setOnValueChangedListener(listener);
        dayPickerFrom.setOnValueChangedListener(listener);
        hourPickerTo.setOnValueChangedListener(listener);
        minutePickerTo.setOnValueChangedListener(listener);
        dayPickerTo.setOnValueChangedListener(listener);

        View add_timer_close = dialog.findViewById(R.id.add_timer_close);
        add_timer_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button add_time = (Button) dialog.findViewById(R.id.add_time);
        add_time.setText("Done");
        add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromTime = String.format("%02d", from_Hour) + ":" + String.format("%02d", from_Minute) + " " + fromMeridian;
                String toTime = String.format("%02d", to_Hour) + ":" + String.format("%02d", to_Minute) + " " + toMeridian;
                callback.onAddTimeSlot(fromTime, toTime);
                dialog.dismiss();
            }
        });
    }

    class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.DataHolder> {

        Context context;
        private List<ConsultationTiming> timingList;

        public CalenderAdapter(List<ConsultationTiming> timingList, Context context) {
            this.timingList = timingList;
            this.context = context;
        }

        @Override
        public CalenderAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycalender_time_slot_item, parent, false);
            return new DataHolder(view);
        }

        @Override
        public void onBindViewHolder(final DataHolder holder, final int position) {

            holder.fromTimeText.setText(Globals.to12HourFormat(timingList.get(position).getFromTime()));
            holder.toTimeText.setText(Globals.to12HourFormat(timingList.get(position).getToTime()));
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    realm.beginTransaction();
                    timingList.remove(position);
                    realm.commitTransaction();
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, timingList.size());
                    if (timingList.size() == 0)
                        mRecyclerView.setVisibility(View.GONE);
                }
            });
        }

        public void refresh()
        {
            notifyDataSetChanged();

            if (getItemCount() > 0)
                mRecyclerView.setVisibility(View.VISIBLE);
            else
                mRecyclerView.setVisibility(View.GONE);
        }

        public void setDataSet(List<ConsultationTiming> timingList)
        {
            this.timingList = timingList;

            refresh();
        }

        @Override
        public int getItemCount() {
            return timingList.size();
        }

        public class DataHolder extends RecyclerView.ViewHolder {
            TextView fromTimeText, toTimeText, remove;

            public DataHolder(final View itemView) {
                super(itemView);
                fromTimeText = (TextView) itemView.findViewById(R.id.from_date);
                toTimeText = (TextView) itemView.findViewById(R.id.to_date);
                remove = (TextView) itemView.findViewById(R.id.remove_item);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEditTimeSlotDialog(fromTimeText.getText().toString(), toTimeText.getText().toString(), new AddTimeSlotListener() {
                            @Override
                            public void onAddTimeSlot(String fromTime, String toTime) {
                                fromTimeText.setText(fromTime);
                                toTimeText.setText(toTime);

                                realm.beginTransaction();
                                timingList.get(getPosition()).setFromTime(Globals.to24HourFormat(fromTime));
                                timingList.get(getPosition()).setToTime(Globals.to24HourFormat(toTime));
                                realm.commitTransaction();
                            }
                        });
                    }
                });
            }
        }
    }
}