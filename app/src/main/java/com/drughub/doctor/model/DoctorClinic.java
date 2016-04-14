package com.drughub.doctor.model;

import android.content.Context;

import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;


public class DoctorClinic extends RealmObject {
    private String clinicId;
    private String clinicTimings;
    private String consultationFee;
    private String clinicName="Clinic Name";
    private String phoneNo;
    private boolean isHomeClinic;
    private Address address;
    private String website;
    GetAllClinicsCalender gacc=new GetAllClinicsCalender();
    public String getClinicTimings() {
        return clinicTimings;
    }

    public void setClinicTimings(String clinicTimings) {
        this.clinicTimings = clinicTimings;
    }

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

    public boolean getisHomeClinic() {
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

    public String toAddClinic() {
        JSONObject object = new JSONObject();
        JSONObject address_object = new JSONObject();
        try {
            object.put("consultationFee", getConsultationFee());
            object.put("clinicName", getClinicName());
            object.put("phoneNo", getPhoneNo());
            object.put("isHomeClinic", getisHomeClinic());
            object.put("clinicTimings", getClinicTimings());

//            address_object.put("buildingName", address.getBuildingName());
//            address_object.put("country", address.getCountry());
//            address_object.put("colony", address.getColony());
//            address_object.put("doorNumber", address.getDoorNumber());
//            address_object.put("city", address.getCity());
//            address_object.put("street", address.getStreet());
//            address_object.put("postalCode",address.getPostalCode());
//            address_object.put("state", address.getStreet());

            object.put("address", getFullAddress());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public void AddClinic(Context context ,  final Globals.VolleyCallback callback) {

        Globals.POST(Urls.ADD_CLINIC,  null, toAddClinic(), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    callback.onSuccess(result);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {
                callback.onFail(result);
            }
        });
    }

    public void UpdateClinic(Context context) {

        Globals.PUT(Urls.UPDATE_CLINIC, null, null, toAddClinic(), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFail(String result) {

            }
        });

    }


    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public JSONObject getFullAddress() {
        return address.toServiceProvider();
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }
}
