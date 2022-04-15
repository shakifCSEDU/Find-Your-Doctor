package com.example.myapplication;

public class CustomRowItem {
     private String Name,institute,educationalQualification,uid;


     public CustomRowItem(String Name, String institute, String educationalQualification,String uid) {
        this.Name = Name;
        this.institute = institute;
        this.educationalQualification = educationalQualification;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getEducationalQualification() {
        return educationalQualification;
    }

    public void setEducationalQualification(String educationalQualification) {
        this.educationalQualification = educationalQualification;
    }
}
