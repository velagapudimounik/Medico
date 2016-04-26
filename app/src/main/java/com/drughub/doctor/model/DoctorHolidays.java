package com.drughub.doctor.model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class DoctorHolidays extends RealmObject {
    private RealmList<DateObject> holidays;

    public RealmList<DateObject> getHolidays() {
        return holidays;
    }

    public void setHolidays(RealmList<DateObject> holidays) {
        this.holidays = holidays;
    }
}
