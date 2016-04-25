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
import io.realm.annotations.PrimaryKey;


public class ClinicCalendar extends RealmObject {

    @PrimaryKey
    private Integer clinicId = -1;
    private RealmList<ConsultationTiming> consultationTimings;
    private DoctorClinic clinic;

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

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

    public String toCreateClinicCalendar() {
        JSONArray array = new JSONArray();
        try {
            for (ConsultationTiming consultationTiming : getConsultationTimings()) {
                array.put(consultationTiming.getJSONObject());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return array.toString();
    }
}
