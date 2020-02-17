package com.pureintentions.hospital.Patient;

public class Appointment {

    String Patient_DOB,Patient_name,Date,Time,Uid;

    public String getPatient_DOB() {
        return Patient_DOB;
    }

    public void setPatient_DOB(String patient_DOB) {
        Patient_DOB = patient_DOB;
    }

    public Appointment() {
    }

    public String getPatient_name() {
        return Patient_name;
    }

    public void setPatient_name(String patient_name) {
        Patient_name = patient_name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public Appointment(String patient_DOB, String patient_name, String date, String time, String uid) {
        Patient_DOB = patient_DOB;
        Patient_name = patient_name;
        Date = date;
        Time = time;
        Uid = uid;
    }
}
