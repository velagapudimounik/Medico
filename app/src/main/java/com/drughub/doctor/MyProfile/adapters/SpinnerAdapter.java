package com.drughub.doctor.MyProfile.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.drughub.doctor.R;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<String> {
    private Context context;
    ArrayList<String> values;

    public SpinnerAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.myporfile_spinner_addclinic, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return countrySet(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return countrySet(position);


    }

    @Override
    public int getCount() {
        return values.size() - 1;
    }

    View countrySet(int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View countrySpinner = inflater.inflate(R.layout.myporfile_spinner_addclinic, null);
        String val = values.get(position);
        TextView countryTextview = (TextView) countrySpinner.findViewById(R.id.spinner_textview);
        countryTextview.setText(val);
        if (val.equalsIgnoreCase("city") || val.equalsIgnoreCase("state")|| val.equalsIgnoreCase("country"))
            countryTextview.setTextColor(Color.LTGRAY);
        else
            countryTextview.setTextColor(Color.DKGRAY);
        return countrySpinner;

    }
}