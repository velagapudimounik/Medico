package com.drughub.doctor.mycalendar;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.Notification.NotificationActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;

import java.lang.reflect.Field;
import java.util.ArrayList;

enum CLOCK_PICKER {
    HOURS,
    MINUTES,
    MERIDIEM,
}

public class MyCalendarActivity extends BaseActivity {

    final String[] spinnervalues = {"Clinic Name1 |", "Clinic Name2 |", "Clinic Name3 |", "My Clinics"};
    final String[] spinneraddress = {"Address1", "Address2", "Address3", ""};

    int from_day = -1, from_month = -1, from_year = -1;
    int to_day = -1, to_month = -1, to_year = -1;
    EditText from_date_picker_edt, to_date_picker_edt;

    TextView working_day;
    CheckBox day_wise;
    RecyclerView show_date_view;
    int from_Hour, from_Minute = 1, to_Hour, to_Minute = 1;
    String fromMeridian = "AM", toMeridian = "AM";
    CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    boolean checked;
    CalenderAdapter calenderAdapter;
    Button addCalender;
    ArrayList<String> fromTime, toTime, fromTimeMonday, fromTimeTuesday, fromTimeWednesday, fromTimeThursday, fromTimeFriday, fromTimeSaturday, fromTimeSunday;
    ArrayList<String> toTimeMonday, toTimeTuesday, toTimeWednesday, toTimeThursday, toTimeFriday, toTimeSaturday, toTimeSunday;

    public static NumberPicker initNumberPicker(NumberPicker numberPicker, CLOCK_PICKER pickerType) {
        String[] displayValues = null;

        switch (pickerType) {
            case HOURS: {
                displayValues = new String[12];
                for (int i = 0; i < 12; i++)
                    displayValues[i] = "" + (i + 1);//String.format("%02d", i+1);

                numberPicker.setMaxValue(12);
                numberPicker.setMinValue(1);
            }
            break;
            case MINUTES: {
                displayValues = new String[60];
                for (int i = 0; i < 60; i++)
                    displayValues[i] = String.format("%02d", i);

                numberPicker.setMaxValue(60);
                numberPicker.setMinValue(1);
            }
            break;
            case MERIDIEM: {
                displayValues = new String[2];
                displayValues[0] = "AM";
                displayValues[1] = "PM";

                numberPicker.setMaxValue(2);
                numberPicker.setMinValue(1);
            }
            break;
        }

        numberPicker.setDisplayedValues(displayValues);
        setNumberPickerTextColor(numberPicker, 0xFFFFFFFF);
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

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //RadioButton rb = (RadioButton) group.findViewById(checkedId);

                if (checkedId == R.id.availability)
                    getSupportFragmentManager().beginTransaction().replace(R.id.my_calendar_container, new MyCalendarAvailabilityList()).commit();
                else if (checkedId == R.id.booked)
                    getSupportFragmentManager().beginTransaction().replace(R.id.my_calendar_container, new MyCalendarBookedList()).commit();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.my_calendar_container, new MyCalendarAvailabilityList()).commit();

        View addCalendar = findViewById(R.id.addCalendar);
        addCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = CustomDialog.showCustomDialog(MyCalendarActivity.this, R.layout.mycalendar_create_new,
                        Gravity.BOTTOM, true, true, false);

                Spinner myspinner = (Spinner) dialog.findViewById(R.id.addressSelection);

                View add_time = dialog.findViewById(R.id.add_calender);
                day_wise = (CheckBox) dialog.findViewById(R.id.date_check_box);
                addCalender = (Button) dialog.findViewById(R.id.addBtn);

                working_day = (TextView) dialog.findViewById(R.id.working_days);
                show_date_view = (RecyclerView) dialog.findViewById(R.id.date_show);
                show_date_view.hasFixedSize();

                RecyclerView.LayoutManager layout = new LinearLayoutManager(getApplicationContext());
                show_date_view.setLayoutManager(layout);
                fromTime = new ArrayList<String>();
                toTime = new ArrayList<String>();
                fromTimeMonday = new ArrayList<String>();
                toTimeMonday = new ArrayList<String>();
                fromTimeTuesday = new ArrayList<String>();
                toTimeTuesday = new ArrayList<String>();
                fromTimeWednesday = new ArrayList<String>();
                toTimeWednesday = new ArrayList<String>();
                fromTimeThursday = new ArrayList<String>();
                toTimeThursday = new ArrayList<String>();
                fromTimeFriday = new ArrayList<String>();
                toTimeFriday = new ArrayList<String>();
                fromTimeSaturday = new ArrayList<String>();
                toTimeSaturday = new ArrayList<String>();
                fromTimeSunday = new ArrayList<String>();
                toTimeSunday = new ArrayList<String>();
               /* itemadd(fromTimeMonday, toTimeMonday, 1);
                itemadd(fromTimeTuesday, toTimeTuesday, 2);
                itemadd(fromTimeWednesday, toTimeWednesday, 1);
                itemadd(fromTimeThursday, toTimeThursday, 2);
                itemadd(fromTimeFriday, toTimeFriday, 1);
                itemadd(fromTimeSaturday, toTimeSaturday, 2);
                itemadd(fromTimeSunday, toTimeSunday, 1);
               *//* for (int i = 0; i < 1; i++) {
                    fromTime.add("09:00 AM");
                    toTime.add("11:00 AM");
                }*/

                monday = (CheckBox) dialog.findViewById(R.id.monday);
                tuesday = (CheckBox) dialog.findViewById(R.id.tuesday);
                wednesday = (CheckBox) dialog.findViewById(R.id.wednesday);
                thursday = (CheckBox) dialog.findViewById(R.id.thursday);
                friday = (CheckBox) dialog.findViewById(R.id.friday);
                saturday = (CheckBox) dialog.findViewById(R.id.saturday);
                sunday = (CheckBox) dialog.findViewById(R.id.sunday);


                calenderAdapter = new CalenderAdapter(fromTime, toTime, MyCalendarActivity.this);
                show_date_view.setAdapter(calenderAdapter);

                day_wise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked()) {
                            uncheckall();
                            working_day.setVisibility(View.GONE);
                            show_date_view.setVisibility(View.GONE);
                            calenderAdapter = new CalenderAdapter(fromTimeMonday, toTimeMonday, MyCalendarActivity.this);
                            show_date_view.setAdapter(calenderAdapter);
                            monday.setChecked(true);
                            checked = true;
                        } else {
                            uncheckall();
                            working_day.setVisibility(View.VISIBLE);
                            calenderAdapter = new CalenderAdapter(fromTime, toTime, MyCalendarActivity.this);
                            show_date_view.setAdapter(calenderAdapter);
                            checked = false;
                        }
                    }
                });

                CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (day_wise.isChecked()) {
                            uncheckall();
                            buttonView.setChecked(isChecked);

                            switch (buttonView.getId()) {
                                case R.id.monday:
                                    calenderAdapter = new CalenderAdapter(fromTimeMonday, toTimeMonday, MyCalendarActivity.this);
                                    show_date_view.setAdapter(calenderAdapter);
                                    Log.i("Frommonday", fromTimeMonday + "");
                                    Log.i("from to time", toTimeMonday + "");
                                    break;
                                case R.id.tuesday:
                                    calenderAdapter = new CalenderAdapter(fromTimeTuesday, toTimeTuesday, MyCalendarActivity.this);
                                    show_date_view.setAdapter(calenderAdapter);
                                    break;
                                case R.id.wednesday:
                                    calenderAdapter = new CalenderAdapter(fromTimeWednesday, toTimeWednesday, MyCalendarActivity.this);
                                    show_date_view.setAdapter(calenderAdapter);
                                    break;
                                case R.id.thursday:
                                    calenderAdapter = new CalenderAdapter(fromTimeThursday, toTimeThursday, MyCalendarActivity.this);
                                    show_date_view.setAdapter(calenderAdapter);
                                    break;
                                case R.id.friday:
                                    calenderAdapter = new CalenderAdapter(fromTimeFriday, toTimeFriday, MyCalendarActivity.this);
                                    show_date_view.setAdapter(calenderAdapter);
                                    break;
                                case R.id.saturday:
                                    calenderAdapter = new CalenderAdapter(fromTimeSaturday, toTimeSaturday, MyCalendarActivity.this);
                                    show_date_view.setAdapter(calenderAdapter);
                                    break;
                                case R.id.sunday:
                                    calenderAdapter = new CalenderAdapter(fromTimeSunday, toTimeSunday, MyCalendarActivity.this);
                                    show_date_view.setAdapter(calenderAdapter);
                                    break;
                            }
                            if (calenderAdapter.getItemCount() > 0) {
                                show_date_view.setVisibility(View.VISIBLE);
                            } else {
                                show_date_view.setVisibility(View.GONE);
                            }
                        }
                    }
                };
                monday.setOnCheckedChangeListener(listener);
                tuesday.setOnCheckedChangeListener(listener);
                wednesday.setOnCheckedChangeListener(listener);
                thursday.setOnCheckedChangeListener(listener);
                friday.setOnCheckedChangeListener(listener);
                saturday.setOnCheckedChangeListener(listener);
                sunday.setOnCheckedChangeListener(listener);


                add_time.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        final Dialog dialog = CustomDialog.showCustomDialog(MyCalendarActivity.this, R.layout.calender_time_picker,
                                Gravity.BOTTOM, true, true, false);
                        MyCalendarActivity.initNumberPicker((NumberPicker) dialog.findViewById(R.id.hourPickerFrom), CLOCK_PICKER.HOURS);
                        MyCalendarActivity.initNumberPicker((NumberPicker) dialog.findViewById(R.id.minutePickerFrom), CLOCK_PICKER.MINUTES);
                        MyCalendarActivity.initNumberPicker((NumberPicker) dialog.findViewById(R.id.dayPickerFrom), CLOCK_PICKER.MERIDIEM);
                        MyCalendarActivity.initNumberPicker((NumberPicker) dialog.findViewById(R.id.hourPickerTo), CLOCK_PICKER.HOURS);
                        MyCalendarActivity.initNumberPicker((NumberPicker) dialog.findViewById(R.id.minutePickerTo), CLOCK_PICKER.MINUTES);
                        MyCalendarActivity.initNumberPicker((NumberPicker) dialog.findViewById(R.id.dayPickerTo), CLOCK_PICKER.MERIDIEM);


                        NumberPicker hourfrom = (NumberPicker) dialog.findViewById(R.id.hourPickerFrom);
                        NumberPicker minutefrom = (NumberPicker) dialog.findViewById(R.id.minutePickerFrom);
                        NumberPicker dayfrom = (NumberPicker) dialog.findViewById(R.id.dayPickerFrom);
                        NumberPicker hourto = (NumberPicker) dialog.findViewById(R.id.hourPickerTo);
                        NumberPicker minuteto = (NumberPicker) dialog.findViewById(R.id.minutePickerTo);
                        NumberPicker dayto = (NumberPicker) dialog.findViewById(R.id.dayPickerTo);

                        hourfrom.setValue(from_Hour);
                        minutefrom.setValue(from_Minute);
                        hourto.setValue(to_Hour);
                        minuteto.setValue(to_Minute);


                        hourfrom.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                from_Hour = newVal;
                            }
                        });

                        minutefrom.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                from_Minute = newVal;

                            }
                        });
                        dayfrom.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                if (newVal == 2)
                                    fromMeridian = "PM";
                                else fromMeridian = "AM";
                            }
                        });
                        hourto.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                to_Hour = newVal;

                            }
                        });
                        minuteto.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                to_Minute = newVal;

                            }
                        });
                        dayto.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                            @Override
                            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                                if (newVal == 2)
                                    toMeridian = "PM";
                                else toMeridian = "AM";
                            }
                        });

                        View add_timer_close = dialog.findViewById(R.id.add_timer_close);
                        add_timer_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        View add_time = dialog.findViewById(R.id.add_time);
                        add_time.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                show_date_view.setVisibility(View.VISIBLE);
                                if (from_Hour == 0) {
                                    from_Hour = 12;
                                }
                                if (to_Hour == 0) {
                                    to_Hour = 12;
                                }
                                calenderAdapter.getFromTime().add(String.format("%02d", from_Hour) + ":" + String.format("%02d", (from_Minute - 1)) + " " + fromMeridian);
                                calenderAdapter.getToTime().add(String.format("%02d", to_Hour) + ":" + String.format("%02d", (to_Minute - 1)) + " " + toMeridian);
                                Log.i("from_hour12", fromTime.toString() + "  " + toTime);
                                /*calenderAdapter = new CalenderAdapter(fromTime, toTime, MyCalendarActivity.this);
                                show_date_view.setAdapter(calenderAdapter);
                                */
                                calenderAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                    }
                });
                myspinner.setAdapter(new CustomAdapter(getApplicationContext(), spinnervalues));
                myspinner.setSelection(myspinner.getCount());

                View addBtn = dialog.findViewById(R.id.addBtn);
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    private void uncheckall() {
        monday.setChecked(false);
        tuesday.setChecked(false);
        wednesday.setChecked(false);
        thursday.setChecked(false);
        friday.setChecked(false);
        saturday.setChecked(false);
        sunday.setChecked(false);


    }

    public void hideRecyclerView(boolean hide) {
        if (hide)
            show_date_view.setVisibility(View.GONE);
        else
            show_date_view.setVisibility(View.VISIBLE);
    }


    private class CustomAdapter extends ArrayAdapter<String> {
        private int position;
        private View convertView;
        private ViewGroup parent;
        private Context context;

        public CustomAdapter(Context context, String[] objects) {
            super(context, R.layout.custom_spinner, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.custom_spinner, null);
            TextView clinicname = (TextView) mySpinner.findViewById(R.id.string1);
            clinicname.setText(spinnervalues[position]);
            TextView Address = (TextView) mySpinner.findViewById(R.id.string2);
            Address.setText(spinneraddress[position]);
            return mySpinner;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (getLayoutInflater());
            View mySpinner = inflater.inflate(R.layout.custom_spinner, null);
            TextView clinicname = (TextView) mySpinner.findViewById(R.id.string1);
            clinicname.setText(spinnervalues[position]);
            TextView Address = (TextView) mySpinner.findViewById(R.id.string2);
            Address.setText(spinneraddress[position]);
            if (getCount() == position) {
                clinicname.setTextColor(Color.LTGRAY);
            }
            return mySpinner;
        }

        @Override
        public int getCount() {
            return super.getCount() - 1; // you dont display last item. It is used as hint.
        }

    }
}


class CalenderAdapter extends RecyclerView.Adapter<CalenderAdapter.DataHolder> {


    private ArrayList<String> fromTime, toTime;
    Context context;

    public CalenderAdapter(ArrayList<String> fromTime, ArrayList<String> toTime, Context context) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.context = context;
    }

    public ArrayList<String> getFromTime() {
        return fromTime;
    }

    public ArrayList<String> getToTime() {
        return toTime;
    }

    @Override
    public CalenderAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_calender_show_date, parent, false);
        DataHolder dataHolder = new DataHolder(view);

        return dataHolder;
    }

    @Override
    public void onBindViewHolder(final CalenderAdapter.DataHolder holder, final int position) {

        holder.fromdate.setText(String.format(fromTime.get(position)));
        holder.todate.setText(String.format(toTime.get(position)));
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                fromTime.remove(position);
                toTime.remove(position);
                notifyItemRemoved(position);
                if (fromTime.size() > 0)
                    ((MyCalendarActivity) context).hideRecyclerView(false);
                else
                    ((MyCalendarActivity) context).hideRecyclerView(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return fromTime.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder {
        TextView fromdate, todate, remove;
        int from_Hour, from_Minute = 1, to_Hour, to_Minute = 1;
        String fromMeridian = "AM", toMeridian = "AM";

        public DataHolder(final View itemView) {
            super(itemView);
            fromdate = (TextView) itemView.findViewById(R.id.from_date);
            todate = (TextView) itemView.findViewById(R.id.to_date);
            remove = (TextView) itemView.findViewById(R.id.remove_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = CustomDialog.showCustomDialog((MyCalendarActivity) context, R.layout.calender_time_picker,
                            Gravity.BOTTOM, true, true, false);

                    MyCalendarActivity.initNumberPicker((NumberPicker) dialog.findViewById(R.id.hourPickerFrom), CLOCK_PICKER.HOURS);
                    MyCalendarActivity.initNumberPicker((NumberPicker) dialog.findViewById(R.id.minutePickerFrom), CLOCK_PICKER.MINUTES);
                    MyCalendarActivity.initNumberPicker((NumberPicker) dialog.findViewById(R.id.dayPickerFrom), CLOCK_PICKER.MERIDIEM);
                    MyCalendarActivity.initNumberPicker((NumberPicker) dialog.findViewById(R.id.hourPickerTo), CLOCK_PICKER.HOURS);
                    MyCalendarActivity.initNumberPicker((NumberPicker) dialog.findViewById(R.id.minutePickerTo), CLOCK_PICKER.MINUTES);
                    MyCalendarActivity.initNumberPicker((NumberPicker) dialog.findViewById(R.id.dayPickerTo), CLOCK_PICKER.MERIDIEM);


                    NumberPicker hourfrom = (NumberPicker) dialog.findViewById(R.id.hourPickerFrom);
                    NumberPicker minutefrom = (NumberPicker) dialog.findViewById(R.id.minutePickerFrom);
                    NumberPicker dayfrom = (NumberPicker) dialog.findViewById(R.id.dayPickerFrom);
                    NumberPicker hourto = (NumberPicker) dialog.findViewById(R.id.hourPickerTo);
                    NumberPicker minuteto = (NumberPicker) dialog.findViewById(R.id.minutePickerTo);
                    NumberPicker dayto = (NumberPicker) dialog.findViewById(R.id.dayPickerTo);

                    hourfrom.setValue(from_Hour);
                    minutefrom.setValue(from_Minute);
                    hourto.setValue(to_Hour);
                    minuteto.setValue(to_Minute);


                    hourfrom.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            from_Hour = newVal;
                            Log.i("from_hour", from_Hour + "");

                        }
                    });
                    minutefrom.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            from_Minute = newVal;
                            Log.i("from_minute", from_Minute + "");
                        }
                    });
                    dayfrom.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            if (newVal == 2)
                                fromMeridian = "PM";
                            else fromMeridian = "AM";
                        }
                    });
                    hourto.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            to_Hour = newVal;
                        }
                    });
                    minuteto.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            to_Minute = newVal;
                        }
                    });
                    dayto.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                            if (newVal == 2)
                                toMeridian = "PM";
                            else toMeridian = "AM";
                        }
                    });

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
                            if (from_Hour == 0) {
                                from_Hour = 12;
                            }
                            if (to_Hour == 0) {
                                to_Hour = 12;
                            }
                            fromdate.setText(String.format("%02d", from_Hour) + ":" + String.format("%02d", (from_Minute - 1)) + " " + fromMeridian);
                            todate.setText(String.format("%02d", to_Hour) + ":" + String.format("%02d", (to_Minute - 1)) + " " + toMeridian);
                            fromTime.set(getPosition(), fromdate.getText().toString());
                            toTime.set(getPosition(), todate.getText().toString());
                            dialog.dismiss();
                        }
                    });
                }
            });

        }
    }
}