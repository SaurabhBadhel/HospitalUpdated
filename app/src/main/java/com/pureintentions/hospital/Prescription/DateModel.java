package com.pureintentions.hospital.Prescription;

public class DateModel {

    String Name;
    String Date;

    public DateModel() {
    }

    public DateModel(String name, String date) {
        Name = name;
        Date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
