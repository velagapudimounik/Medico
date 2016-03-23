package com.drughub.doctor.patient_record;

/**
 * Created by Deepak on 3/22/2016.
 */
public class PatientRecord {
    private String name;
    private String dob;
    private String records;


    public PatientRecord(String name, String dob, String records) {
        this.name = name;
        this.dob = dob;
        this.records = records;
    }

    public String getDob() {
        return dob;
    }

    public String getName() {
        return name;
    }

    public String getRecords() {
        return records;
    }
}
