package com.drughub.doctor.model;

import io.realm.RealmObject;
public class City extends RealmObject {
    private String value;
    private int id;
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
