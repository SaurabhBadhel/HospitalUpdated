package com.pureintentions.hospital.Doctor;

public class History {
    private String Name;
    private String Date;
    private String Age;
    private String Bp;
    private String Gender;
    private String Blood_Group;
    private String Height;
    private String Remark;
    private String Uid;
    private String Weight;
    private String Medicine_Time1;
    private String Medicine1;



    public  History(){

    }


    public History(String name, String date, String age, String bp, String gender, String blood_Group, String height, String remark, String uid, String weight, String medicine_Time1, String medicine1) {
        Name = name;
        Date = date;
        Age = age;
        Bp = bp;
        Gender = gender;
        Blood_Group = blood_Group;
        Height = height;
        Remark = remark;
        Uid = uid;
        Weight = weight;
        Medicine_Time1 = medicine_Time1;
        Medicine1 = medicine1;
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

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBp() {
        return Bp;
    }

    public void setBp(String bp) {
        Bp = bp;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBlood_Group() {
        return Blood_Group;
    }

    public void setBlood_Group(String blood_Group) {
        Blood_Group = blood_Group;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getMedicine_Time1() {
        return Medicine_Time1;
    }

    public void setMedicine_Time1(String medicine_Time1) {
        Medicine_Time1 = medicine_Time1;
    }

    public String getMedicine1() {
        return Medicine1;
    }

    public void setMedicine1(String medicine1) {
        Medicine1 = medicine1;
    }
}
