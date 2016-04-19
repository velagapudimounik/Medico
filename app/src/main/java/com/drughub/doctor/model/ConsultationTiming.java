package com.drughub.doctor.model;

import org.json.JSONArray;
import org.json.JSONObject;

import io.realm.RealmList;
import io.realm.RealmObject;


public class ConsultationTiming extends RealmObject {

    private Integer calendarId;
    private Integer clinicId;
    private String dayOfWeek;
    private String fromTime;
    private String toTime;

    public void copy(ConsultationTiming timing){
        this.calendarId = timing.getCalendarId();
        this.clinicId = timing.getClinicId();
        this.dayOfWeek = timing.getDayOfWeek();
        this.fromTime = timing.getFromTime();
        this.toTime = timing.getToTime();
    }

    public Integer getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public JSONObject getJSONObject()
    {
        JSONObject object = new JSONObject();
        try {
            //object.put("calendarId", getCalendarId());
            object.put("clinicId", getClinicId());
            object.put("dayOfWeek", getDayOfWeek());
            object.put("fromTime", getFromTime());
            object.put("toTime", getToTime());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }
}