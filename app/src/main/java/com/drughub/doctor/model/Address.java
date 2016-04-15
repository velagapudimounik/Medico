package com.drughub.doctor.model;

import org.json.JSONObject;

import io.realm.RealmObject;

public class Address extends RealmObject {
    private String addressId ;
    private String buildingName;
    private Country country = new Country();
    private String colony;
    private City city = new City();
    private String street;
    private State state = new State();
    private String doorNumber;
    private String postalCode;
    private String areaCode;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    private String areaName;
    private String addressType;
    private String district;
    private String tinno;
    private String companyName;
    private String colonyName;
    private String streetName;
    private String landMark;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
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
            addressObject.put("landMark", getLandmark());
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
        return landMark;
    }

    public void setLandmark(String landmark) {
        this.landMark = landmark;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
