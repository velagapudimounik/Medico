package com.drughub.doctor.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmList;
import io.realm.RealmObject;


public class WeeklyScheduleLists extends RealmObject {
    private Boolean isNonWorkingDay;
    private String  weekDay = "fri";
    private RealmList<Timings> timings;


    public Boolean getIsNonWorkingDay() {
        return isNonWorkingDay;
    }

    public void setIsNonWorkingDay(Boolean isNonWorkingDay) {
        this.isNonWorkingDay = isNonWorkingDay;
    }
    public RealmList<Timings> getTimings() {
        return timings;
    }

    public void setTimings(RealmList<Timings> timings) {
        this.timings = timings;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public JSONObject getJSONObject(){
        JSONObject object = new JSONObject();
        try {
            object.put("weekDay",getWeekDay());
            object.put("isNonWorkingDay",getIsNonWorkingDay());

            JSONArray array = new JSONArray();
            for (Timings timings: getTimings()) {
                array.put(timings.getJSONObject().toString());
            }
            object.put("timings", array);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }


}
