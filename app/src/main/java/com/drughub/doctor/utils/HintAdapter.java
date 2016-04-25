package com.drughub.doctor.utils;

/*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Allows adding a hint at the end of the list. It will show the hint when adding it and selecting
 * the last object. Otherwise, it will show the dropdown view implemented by the concrete class.
 */
public class HintAdapter<T> extends ArrayAdapter<T> {
    private static final String TAG = HintAdapter.class.getSimpleName();

    private static final int DEFAULT_LAYOUT_RESOURCE = android.R.layout.simple_spinner_dropdown_item;
    private final LayoutInflater layoutInflater;
    private int layoutResource;
    private String hintResource;
    private final Callback<T> callback;

    public HintAdapter(Context context, int hintResource, List<T> data, Callback<T> callback) {
        this(context, DEFAULT_LAYOUT_RESOURCE, context.getString(hintResource), data, callback);
    }

    public HintAdapter(Context context, String hint, List<T> data, Callback<T> callback) {
        this(context, DEFAULT_LAYOUT_RESOURCE, hint, data, callback);
    }

    public HintAdapter(Context context, int layoutResource, int hintResource, List<T> data, Callback<T> callback) {
        this(context, layoutResource, context.getString(hintResource), data, callback);
    }

    public HintAdapter(Context context, int layoutResource, String hintResource, List<T> data, Callback<T> callback) {
        // Create a copy, as we need to be able to add the hint without modifying the array passed in
        // or crashing when the user sets an unmodifiable.
        super(context, layoutResource, data);
        this.layoutResource = layoutResource;
        this.hintResource = hintResource;
        this.layoutInflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    /**
     * Hook method to set a custom view.
     * <p/>
     * Provides a default implementation using the simple spinner dropdown item.
     *
     * @param position    Position selected
     * @param convertView View
     * @param parent      Parent view group
     */
    protected View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = inflateLayout(parent, false);//inflateDefaultLayout(parent);
        Object item = getItem(position);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText(item.toString());
        textView.setHint("");
        callback.setItemDetails(view, position, (T) item);
        return view;
    }

    private View inflateDefaultLayout(ViewGroup parent) {
        return inflateLayout(DEFAULT_LAYOUT_RESOURCE, parent, false);
    }

    private View inflateLayout(int resource, android.view.ViewGroup root, boolean attachToRoot) {
        return layoutInflater.inflate(resource, root, attachToRoot);
    }

    public View inflateLayout(android.view.ViewGroup root, boolean attachToRoot) {
        return layoutInflater.inflate(layoutResource, root, attachToRoot);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "position: " + position + ", getCount: " + getCount());
        View view;
        if (position == getHintPosition()) {
            view = getDefaultView(parent);
        } else {
            view = getCustomView(position, convertView, parent);
        }
        return view;
    }

    private View getDefaultView(ViewGroup parent) {
        View view = inflateDefaultLayout(parent);
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        textView.setText("");
        textView.setHint(hintResource);
        return view;
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        return count > 0 ? count : 1;
    }

    /**
     * Gets the position of the hint.
     *
     * @return Position of the hint
     */
    public int getHintPosition() {
        int count = super.getCount();
        return count > 0 ? count + 1 : count;
    }

    public boolean hasItems() {
        return super.getCount() > 0;
    }

    public interface Callback<T> {
        /**
         * When a spinner item view created.
         *
         * @param position       Position
         * @param itemAtPosition Item
         */
        void setItemDetails(View view, int position, T itemAtPosition);
    }
}



