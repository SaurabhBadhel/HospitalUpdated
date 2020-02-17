package com.pureintentions.hospital.Prescription;

public class PrescriptionModel {

    public  static   String age;
    public  static   String height,weight;
    public  static   String bp,name,patient_name;
    public  static   String medicine1,duration_1,medicine_time1;
    public  static   String medicine2,duration_2,medicine_time2;
    public  static   String medicine3,duration_3,medicine_time3;


    public PrescriptionModel(String age, String height, String weight, String bp, String name, String patient_name, String medicine1, String duration_1, String medicine_time1, String medicine2, String duration_2, String medicine_time2, String medicine3, String duration_3, String medicine_time3) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bp = bp;
        this.name = name;
        this.patient_name = patient_name;
        this.medicine1 = medicine1;
        this.duration_1 = duration_1;
        this.medicine_time1 = medicine_time1;
        this.medicine2 = medicine2;
        this.duration_2 = duration_2;
        this.medicine_time2 = medicine_time2;
        this.medicine3 = medicine3;
        this.duration_3 = duration_3;
        this.medicine_time3 = medicine_time3;
    }

    public static String getAge() {
        return age;
    }

    public static void setAge(String age) {
        PrescriptionModel.age = age;
    }

    public static String getHeight() {
        return height;
    }

    public static void setHeight(String height) {
        PrescriptionModel.height = height;
    }

    public static String getWeight() {
        return weight;
    }

    public static void setWeight(String weight) {
        PrescriptionModel.weight = weight;
    }

    public static String getBp() {
        return bp;
    }

    public static void setBp(String bp) {
        PrescriptionModel.bp = bp;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        PrescriptionModel.name = name;
    }

    public static String getPatient_name() {
        return patient_name;
    }

    public static void setPatient_name(String patient_name) {
        PrescriptionModel.patient_name = patient_name;
    }

    public static String getMedicine1() {
        return medicine1;
    }

    public static void setMedicine1(String medicine1) {
        PrescriptionModel.medicine1 = medicine1;
    }

    public static String getDuration_1() {
        return duration_1;
    }

    public static void setDuration_1(String duration_1) {
        PrescriptionModel.duration_1 = duration_1;
    }

    public static String getMedicine_time1() {
        return medicine_time1;
    }

    public static void setMedicine_time1(String medicine_time1) {
        PrescriptionModel.medicine_time1 = medicine_time1;
    }

    public static String getMedicine2() {
        return medicine2;
    }

    public static void setMedicine2(String medicine2) {
        PrescriptionModel.medicine2 = medicine2;
    }

    public static String getDuration_2() {
        return duration_2;
    }

    public static void setDuration_2(String duration_2) {
        PrescriptionModel.duration_2 = duration_2;
    }

    public static String getMedicine_time2() {
        return medicine_time2;
    }

    public static void setMedicine_time2(String medicine_time2) {
        PrescriptionModel.medicine_time2 = medicine_time2;
    }

    public static String getMedicine3() {
        return medicine3;
    }

    public static void setMedicine3(String medicine3) {
        PrescriptionModel.medicine3 = medicine3;
    }

    public static String getDuration_3() {
        return duration_3;
    }

    public static void setDuration_3(String duration_3) {
        PrescriptionModel.duration_3 = duration_3;
    }

    public static String getMedicine_time3() {
        return medicine_time3;
    }

    public static void setMedicine_time3(String medicine_time3) {
        PrescriptionModel.medicine_time3 = medicine_time3;
    }
}

