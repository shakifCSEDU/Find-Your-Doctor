package com.example.myapplication;

public class homeUserClass {
    private String name,visitDate,visitId,phoneNo,doctorType,chamber;
    private int imageId;


    public homeUserClass(String name, String visitDate, String visitId, String phoneNo, String doctorType, String chamber, int imageId) {
        this.name = name;
        this.visitDate = visitDate;
        this.visitId = visitId;
        this.phoneNo = phoneNo;
        this.doctorType = doctorType;
        this.chamber = chamber;
        this.imageId = imageId;
    }

    public homeUserClass(String name, String visitDate, String visitId, String phoneNo, int imageId) {
        this.name = name;
        this.visitDate = visitDate;
        this.visitId = visitId;
        this.phoneNo = phoneNo;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
