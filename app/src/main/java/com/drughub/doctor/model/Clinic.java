package com.drughub.doctor.model;

import io.realm.RealmObject;

/**
 * Created by Uma Devi on 4/4/2016.
 */
public class Clinic extends RealmObject {
    private String clinicId;
    private String consultationFee;
    private String clinicName;
    private String phoneNo;
    private boolean isHomeClinic;
    private Address address;


    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public boolean isHomeClinic() {
        return isHomeClinic;
    }

    public void setIsHomeClinic(boolean isHomeClinic) {
        this.isHomeClinic = isHomeClinic;
    }
    public String getClinicId() {
        return clinicId;
    }

    public void setClinicId(String clinicId) {
        this.clinicId = clinicId;
    }

    public String getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(String consultationFee) {
        this.consultationFee = consultationFee;
    }
}
