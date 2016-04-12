package com.drughub.doctor.model;

import org.json.JSONObject;

import io.realm.RealmObject;

public class Address extends RealmObject {
    private String addressId = "123";
    private String buildingName = "123";
    private ValueIds country;
    private String colony;
    private ValueIds city;
    private String street;
    private ValueIds state;
    private String doorNumber;
    private Integer postalCode;
    private String areaCode;
    private String addressType;
    private String district;
    private String tinno;
    private String companyName;
    private String colonyName;
    private String streetName;
    private String landmark;
    private double lng;
    private double lat;
    private Integer dlno;


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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTinno() {
        return tinno;
    }

    public void setTinno(String tinno) {
        this.tinno = tinno;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getColonyName() {
        return colonyName;
    }

    public void setColonyName(String colonyName) {
        this.colonyName = colonyName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Integer getDlno() {
        return dlno;
    }

    public void setDlno(Integer dlno) {
        this.dlno = dlno;
    }

    public JSONObject toServiceProvider() {
        JSONObject addressObject = new JSONObject();
        try {

            addressObject.put("addressId", getAddressId());
            addressObject.put("doorNumber", getDoorNumber());
            addressObject.put("buildingName", getBuildingName());
            addressObject.put("streetName", getStreet());
            addressObject.put("areaCode", getAreaCode());
            addressObject.put("landmark", getLandmark());
            addressObject.put("postalCode", getPostalCode());
            addressObject.put("lat", getLat());
            addressObject.put("lng", getLng());
            addressObject.put("state", getState().getValueIds());
            addressObject.put("country", getCountry().getValueIdsCode());
            addressObject.put("city", getCity().getValueIds());
            addressObject.put("colony", getColony());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return addressObject;

    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public ValueIds getCountry() {
        return country;
    }

    public void setCountry(ValueIds country) {
        this.country = country;
    }

    public ValueIds getCity() {
        return city;
    }

    public void setCity(ValueIds city) {
        this.city = city;
    }

    public ValueIds getState() {
        return state;
    }

    public void setState(ValueIds state) {
        this.state = state;
    }
}
