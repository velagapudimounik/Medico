package com.drughub.doctor.model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;


public class ClinicCalendar extends RealmObject {

    private Integer clinicId;
    private Integer profileId;
    private Date startDate;
    private Date endDate;
    private Integer clinicCalendarId;
    private Boolean isWeeklyTiming;
    private RealmList<WeeklyScheduleList> weeklyScheduleLists ;

    public RealmList<WeeklyScheduleList> getWeeklyScheduleLists() {
        return weeklyScheduleLists;
    }

    public void setWeeklyScheduleLists(RealmList<WeeklyScheduleList> weeklyScheduleLists) {
        this.weeklyScheduleLists = weeklyScheduleLists;
    }
    public Boolean getIsWeeklyTiming() {
        return isWeeklyTiming;
    }

    public void setIsWeeklyTiming(Boolean isWeeklyTiming) {
        this.isWeeklyTiming = isWeeklyTiming;
    }

    public Integer getClinicCalendarId() {
        return clinicCalendarId;
    }

    public void setClinicCalendarId(Integer clinicCalendarId) {
        this.clinicCalendarId = clinicCalendarId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }



}
