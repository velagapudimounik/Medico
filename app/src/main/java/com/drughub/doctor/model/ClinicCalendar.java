package com.drughub.doctor.model;

import android.content.Context;

import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

import io.realm.RealmList;
import io.realm.RealmObject;


public class ClinicCalendar extends RealmObject {

    private Integer clinicId = 1;
    private Integer profileId = 2;
    private Date startDate;
    private Date endDate;
    private Integer clinicCalendarId;
    private Boolean isWeeklyTiming = true;
    private RealmList<WeeklyScheduleLists> weeklyScheduleLists ;

    public RealmList<WeeklyScheduleLists> getWeeklyScheduleLists() {
        return weeklyScheduleLists;
    }

    public void setWeeklyScheduleLists(RealmList<WeeklyScheduleLists> weeklyScheduleLists) {
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

   public String toCreateClinicCalendar()
   {
       JSONObject object = new JSONObject();
        try
        {
            object.put("clinicId",getClinicId());
            object.put("profileId",getProfileId());
            object.put("startDate",getStartDate());
            object.put("endDate",getEndDate());
            object.put("clinicCalendarId",getClinicCalendarId());
            object.put("isWeeklyTiming",getIsWeeklyTiming());
            object.put("profileId",getProfileId());

            JSONArray array = new JSONArray();
            for(WeeklyScheduleLists weeklyScheduleList : getWeeklyScheduleLists())
            {
                array.put(weeklyScheduleList.getJSONObject());
            }

            object.put("weeklyScheduleList", array);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
       return object.toString();
   }

    public void CreateClinicCalendar(Context context)
    {
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> params = new HashMap<>();
        Globals.POST(Urls.CREATE_CALANDER, headers, params, toCreateClinicCalendar(), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                JSONObject object = new JSONObject();
                // ToDo ClinicCalendar
            }

            @Override
            public void onFail(String result) {

            }
        });
    }

    public void GetClinicCalendar()
    {
        HashMap<String, String> headers = new HashMap<>();
        Globals.GET(Urls.CREATE_CALANDER, headers, new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                JSONObject object = new JSONObject();
                // ToDo GetCalendar
            }

            @Override
            public void onFail(String result) {

            }
        });
    }

    public void UpdateClinicCalendar(Context context)
    {
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> params = new HashMap<>();
        String body_string;

        Globals.PUT(Urls.CREATE_CALANDER, headers, params, toCreateClinicCalendar() , new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                JSONObject object = new JSONObject();
                // ToDo UpdateCalender
            }

            @Override
            public void onFail(String result) {

            }
        });

    }
}
