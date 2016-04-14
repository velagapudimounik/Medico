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

    private RealmList<ConsultationTiming> consultationTimings;
    private DoctorClinic clinic;

    public RealmList<ConsultationTiming> getConsultationTimings() {
        return consultationTimings;
    }

    public void setConsultationTimings(RealmList<ConsultationTiming> consultationTimings) {
        this.consultationTimings = consultationTimings;
    }

    public DoctorClinic getClinic() {
        return clinic;
    }

    public void setClinic(DoctorClinic clinic) {
        this.clinic = clinic;
    }


//    public String toCreateClinicCalendar() {
//        JSONObject object = new JSONObject();
//        try {
//            object.put("consultationTimings", getClinicId());
//            object.put("doctorId", getProfileId());
//            object.put("clinic", getStartDate());
//            object.put("endDate", getEndDate());
//            object.put("clinicCalendarId", getClinicCalendarId());
//            object.put("isWeeklyTiming", getIsWeeklyTiming());
//            object.put("profileId", getProfileId());
//
//            JSONArray array = new JSONArray();
//            for (WeeklyScheduleLists weeklyScheduleList : getWeeklyScheduleLists()) {
//                array.put(weeklyScheduleList.getJSONObject());
//            }
//
//            object.put("weeklyScheduleList", array);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return object.toString();
//    }
//
//    public void CreateClinicCalendar(Context context) {
//        HashMap<String, String> params = new HashMap<>();
//        Globals.POST(Urls.CLINIC_CALENDAR, params, toCreateClinicCalendar(), new Globals.VolleyCallback() {
//            @Override
//            public void onSuccess(String result) {
//                JSONObject object = new JSONObject();
//                // ToDo ClinicCalendar
//            }
//
//            @Override
//            public void onFail(String result) {
//
//            }
//        });
//    }
//
//    public void GetClinicCalendar() {
//        HashMap<String, String> headers = new HashMap<>();
//        Globals.GET(Urls.CLINIC_CALENDAR, headers, new Globals.VolleyCallback() {
//            @Override
//            public void onSuccess(String result) {
//                JSONObject object = new JSONObject();
//                // ToDo GetCalendar
//            }
//
//            @Override
//            public void onFail(String result) {
//
//            }
//        });
//    }
//
//    public void UpdateClinicCalendar(Context context) {
//        HashMap<String, String> headers = new HashMap<>();
//        HashMap<String, String> params = new HashMap<>();
//        String body_string;
//
//        Globals.PUT(Urls.CLINIC_CALENDAR, headers, params, toCreateClinicCalendar(), new Globals.VolleyCallback() {
//            @Override
//            public void onSuccess(String result) {
//                JSONObject object = new JSONObject();
//                // ToDo UpdateCalender
//            }
//
//            @Override
//            public void onFail(String result) {
//
//            }
//        });
//
//    }
}
