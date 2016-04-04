package com.drughub.doctor.model;

import io.realm.RealmList;
import io.realm.RealmObject;


public class WeeklyScheduleList extends RealmObject {
    private Boolean isNonWorkingDay;
    private String  weekDay;
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


}
