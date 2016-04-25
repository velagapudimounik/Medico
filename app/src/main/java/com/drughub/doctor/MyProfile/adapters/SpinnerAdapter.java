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
    String hintText;

    public SpinnerAdapter(Context context, ArrayList<String> values, String hintText) {
        super(context, R.layout.myporfile_spinner_item, values);
        this.context = context;
        this.values = values;
        this.hintText = hintText;
    }

    public SpinnerAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.myporfile_spinner_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItem(position);
    }

    @Override
    public int getCount() {
        return values.size() - 1;
    }

    View createItem(int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View countrySpinner = inflater.inflate(R.layout.myporfile_spinner_item, null);
        String val = values.get(position);
        TextView textView = (TextView) countrySpinner.findViewById(R.id.spinner_text_view);
        textView.setText(val);
        if (val.equalsIgnoreCase("city") || val.equalsIgnoreCase("state")|| val.equalsIgnoreCase("country"))
            textView.setTextColor(Color.LTGRAY);
        else
            textView.setTextColor(Color.DKGRAY);
        return countrySpinner;

    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}