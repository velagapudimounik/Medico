package com.drughub.doctor.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;


public class Timings extends RealmObject {

    private Integer fromTimeMinutes = 1;
    private Integer toTimeHour;
    private Integer toTimeMinutes;
    private Integer fromTimeHour;

    public Integer getFromTimeHour() {
        return fromTimeHour;
    }

    public void setFromTimeHour(Integer fromTimeHour) {
        this.fromTimeHour = fromTimeHour;
    }

    public Integer getToTimeMinutes() {
        return toTimeMinutes;
    }

    public void setToTimeMinutes(Integer toTimeMinutes) {
        this.toTimeMinutes = toTimeMinutes;
    }

    public Integer getToTimeHour() {
        return toTimeHour;
    }

    public void setToTimeHour(Integer toTimeHour) {
        this.toTimeHour = toTimeHour;
    }

    public Integer getFromTimeMinutes() {
        return fromTimeMinutes;
    }

    public void setFromTimeMinutes(Integer fromTimeMinutes) {
        this.fromTimeMinutes = fromTimeMinutes;
    }


    public JSONObject getJSONObject() {

        JSONObject object = new JSONObject();
        try {
            object.put("fromTimeMinutes",getFromTimeMinutes());
            object.put("toTimeHour",getToTimeHour());
            object.put("toTimeMinutes",getToTimeMinutes());
            object.put("fromTimeHour",getFromTimeHour());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }
}
