package com.drughub.doctor.mycalendar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.Notification.NotificationActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;

import java.lang.reflect.Field;

enum CLOCK_PICKER
{
    HOURS,
    MINUTES,
    MERIDIEM,
}

public class MyCalendarActivity extends BaseActivity {

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

                Spinner dropdown = (Spinner)dialog.findViewById(R.id.addressSelection);
                String[] items = new String[]{"Clinic Name 1 | Location", "Clinic Name 2 | Location", "Clinic Name 3 | Location"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MyCalendarActivity.this, android.R.layout.simple_list_item_1, items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dropdown.setAdapter(adapter);

                initNumberPicker((NumberPicker) dialog.findViewById(R.id.hourPickerFrom), CLOCK_PICKER.HOURS);
                initNumberPicker((NumberPicker)dialog.findViewById(R.id.minutePickerFrom), CLOCK_PICKER.MINUTES);
                initNumberPicker((NumberPicker)dialog.findViewById(R.id.dayPickerFrom), CLOCK_PICKER.MERIDIEM);
                initNumberPicker((NumberPicker)dialog.findViewById(R.id.hourPickerTo), CLOCK_PICKER.HOURS);
                initNumberPicker((NumberPicker)dialog.findViewById(R.id.minutePickerTo), CLOCK_PICKER.MINUTES);
                initNumberPicker((NumberPicker)dialog.findViewById(R.id.dayPickerTo), CLOCK_PICKER.MERIDIEM);

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

    public static NumberPicker initNumberPicker(NumberPicker numberPicker, CLOCK_PICKER pickerType)
    {
        String[] displayValues = null;

        switch (pickerType)
        {
            case HOURS:
            {
                displayValues = new String[12];
                for(int i = 0; i<12; i++)
                    displayValues[i] = ""+(i+1);//String.format("%02d", i+1);

                numberPicker.setMaxValue(12);
                numberPicker.setMinValue(1);
            }
            break;
            case MINUTES:
            {
                displayValues = new String[60];
                for(int i = 0; i<60; i++)
                    displayValues[i] = String.format("%02d", i);

                numberPicker.setMaxValue(60);
                numberPicker.setMinValue(1);
            }
            break;
            case MERIDIEM:
            {
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

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Class<?> numberPickerClass = numberPicker.getClass();
                    Field selectorWheelPaintField = numberPickerClass.getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    child.setPadding(0, 0 ,0, 0);

                    Field selectionDivider = numberPickerClass.getDeclaredField("mSelectionDivider");
                    selectionDivider.setAccessible(true);
                    selectionDivider.set(numberPicker, null);

                    numberPicker.invalidate();
                    return true;
                }
                catch(Exception e){
                    Log.w("setNumberPickerColor", e);
                }
            }
        }
        return false;
    }

}