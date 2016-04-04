package com.drughub.doctor.model;

import android.content.Context;

import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;

import org.json.JSONObject;

import java.util.HashMap;

import io.realm.RealmObject;


public class ServiceProvider extends RealmObject {

    private String firstName = "Achyuth";
    private String lastName = "Kumar";
    private String phone = "123456789";
    private String buildingName;
    private String colony;
    private String doorNumber;
    private String street;
    private String postalCode;
    private String City = "HYd";
    private String State;
    private String Country = "India";
    private String email = "achyuth@drughub.in";
    private String qualification = "Btech";
    private String specialization = "Android";
    private Integer experienceInYears = 1;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String contry) {
        Country = contry;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Integer getExperienceInYears() {
        return experienceInYears;
    }

    public void setExperienceInYears(Integer experienceInYears) {
        this.experienceInYears = experienceInYears;
    }

    public String toUpdateServiceProvider() {
        JSONObject object = new JSONObject();
        JSONObject address_object = new JSONObject();
        try {
            object.put("firstName", getFirstName());
            object.put("lastname", getLastName());
            object.put("phone", getPhone());
            object.put("qualification", getQualification());
            object.put("specialization", getSpecialization());
            object.put("experienceInYears", getExperienceInYears());

            address_object.put("buildingName", address.getBuildingName());
            address_object.put("country", address.getCountry());
            address_object.put("colony", address.getColony());
            address_object.put("doorNumber", address.getDoorNumber());
            address_object.put("city", address.getCity());
            address_object.put("street", address.getStreet());
            address_object.put("postalCode", address.getPostalCode());
            address_object.put("state", address.getState());


            object.put("address", address_object);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return object.toString();
    }


    public void UpdateServiceProvider(Context context) {
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> params = new HashMap<>();
        Globals.PUT(Urls.SERVICE_PROVIDER, headers, params, toUpdateServiceProvider(), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject response = new JSONObject();
                    // ToDo UpdateServiceProvider(SP)

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFail(String result) {

            }
        });

    }

    public void CreateServiceProvider() {
        HashMap<String, String> headers = new HashMap<>();
        Globals.GET(Urls.SERVICE_PROVIDER, headers, new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFail(String result) {

            }
        });


    }
}
