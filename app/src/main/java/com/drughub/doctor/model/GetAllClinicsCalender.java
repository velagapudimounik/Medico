package com.drughub.doctor.model;

import android.text.format.Time;
import android.util.Log;

import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

import io.realm.RealmObject;

/**
 * Created by Uma Devi on 4/14/2016.
 */
public class GetAllClinicsCalender extends RealmObject {
    private String dayOfWeek;
    private int addressId;
    private int calenderId;
    private int yearOfEstablishment;
    private Date fromTime;
    private Date toTime;
    private DoctorClinic clinicdetails = new DoctorClinic();

    public int getCalenderId() {
        return calenderId;
    }

    public void setCalenderId(int calenderId) {
        this.calenderId = calenderId;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public DoctorClinic getClinicdetails() {
        return clinicdetails;
    }

    public void setClinicdetails(DoctorClinic clinicdetails) {
        this.clinicdetails = clinicdetails;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getYearOfEstablishment() {
        return yearOfEstablishment;
    }

    public void setYearOfEstablishment(int yearOfEstablishment) {
        this.yearOfEstablishment = yearOfEstablishment;
    }

    public static void getAllClinicsCalender(final Globals.VolleyCallback callback) {
        Globals.GET(Urls.GET_ALLCLINICS_CALENDER, null, new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
                Log.v("resultALCC====", result);
            }

            @Override
            public void onFail(String result) {
                callback.onFail(result);
                Log.v("FailALCC====", result);
            }
        });
    }

}

