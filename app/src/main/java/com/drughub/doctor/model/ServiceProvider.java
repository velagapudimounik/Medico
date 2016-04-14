package com.drughub.doctor.model;

import android.content.Context;

import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;
import com.drughub.doctor.utils.PrefUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import io.realm.RealmList;
import io.realm.RealmObject;


public class ServiceProvider extends RealmObject {

    private ValueIds gender;
    private String mobile;
    private int spProfileId;
    private String emailId;
    private RealmList<ValueIds> qualificationList;
    private RealmList<ValueIds> specializationList;
    private String practiseStartDate;
    private String partnerNature;
    private String profileName;
    private String profileDescription;
    private String middleName;
    private String lastName;
    private String firstName;
    private Address address = new Address();

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public JSONObject getFullAddress(){
        return address.toServiceProvider();
    }

    public String toUpdateServiceProvider() {
        JSONObject object = new JSONObject();
        try {
            object.put("address", getFullAddress());
            JSONArray qualificationList = new JSONArray();
            for (ValueIds qualification : getQualificationList()) {
                qualificationList.put(qualification.getValueIds());
            }
            JSONArray specializationList = new JSONArray();
            for (ValueIds spec : getSpecializationList()) {
                specializationList.put(spec.getValueIds());
            }

            object.put("qualificationList", qualificationList);
            object.put("specializationList", specializationList);
            object.put("practiseStartDate", getPractiseStartDate());
            object.put("profileDescription", getProfileDescription());
            object.put("mobile", getMobile());
            object.put("spProfileId", getSpProfileId());
            object.put("firstName", getFirstName());
            object.put("lastName", getLastName());
            object.put("middleName", getLastName());
            object.put("profileName", getProfileName());
//            object.put("experienceInYears", getExperienceInYears());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return object.toString();
    }


    public void UpdateServiceProvider(Context context, final Globals.VolleyCallback callback) {
        Globals.PUT(Urls.SERVICE_PROVIDER+ PrefUtils.getUserName(context), null, null, toUpdateServiceProvider(), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFail(String result) {
                callback.onFail(result);
            }
        });

    }

    public void GetServiceProvider() {
        Globals.GET(Urls.SERVICE_PROVIDER, null, new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFail(String result) {

            }
        });
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getSpProfileId() {
        return spProfileId;
    }

    public void setSpProfileId(int spProfileId) {
        this.spProfileId = spProfileId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public ValueIds getGender() {
        return gender;
    }

    public void setGender(ValueIds gender) {
        this.gender = gender;
    }

    public RealmList<ValueIds> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(RealmList<ValueIds> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public RealmList<ValueIds> getSpecializationList() {
        return specializationList;
    }

    public void setSpecializationList(RealmList<ValueIds> specializationList) {
        this.specializationList = specializationList;
    }

    public String getPractiseStartDate() {
        return practiseStartDate;
    }

    public void setPractiseStartDate(String practiseStartDate) {
        this.practiseStartDate = practiseStartDate;
    }

    public String getPartnerNature() {
        return partnerNature;
    }

    public void setPartnerNature(String partnerNature) {
        this.partnerNature = partnerNature;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }
}
