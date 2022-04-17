package com.example.myapplication;

public class homeUserClass {
    private String name,visitDate,visitId,phoneNo,doctorType,chamber,cancelState,confirmState,uid;
    private int imageId;

    public String getCancelState() {
        return cancelState;
    }

    public void setCancelState(String cancelState) {
        this.cancelState = cancelState;
    }

    public String getConfirmState() {
        return confirmState;
    }

    public void setConfirmState(String confirmState) {
        this.confirmState = confirmState;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public homeUserClass(String name, String visitDate, String visitId, String phoneNo, String doctorType, String chamber, int imageId, String cancelState, String confirmState, String uid) {
        this.name = name;
        this.visitDate = visitDate;
        this.visitId = visitId;
        this.phoneNo = phoneNo;
        this.doctorType = doctorType;
        this.chamber = chamber;
        this.imageId = imageId;
        this.cancelState = cancelState;
        this.confirmState =confirmState;
        this.uid = uid;
    }

    public homeUserClass(String name, String visitDate, String visitId, String phoneNo, int imageId,String cancelState,String confirmState,String uid){
        this.name = name;
        this.visitDate = visitDate;
        this.visitId = visitId;
        this.phoneNo = phoneNo;
        this.imageId = imageId;
        this.cancelState = cancelState;
        this.confirmState =confirmState;
        this.uid = uid;
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
