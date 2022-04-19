package com.example.myapplication;

public class homeUserClass {
     private int imageId;
     private String backgroundColor;

    private String  chamber,doctorCancelState,
            doctorConfirmState,doctorName,doctorPhoneNumber,doctorType,doctorUid,
    issueDate,issueTime , patientCancelState , patientConfirmState,patientName,
            patientPhoneNumber , patientReview,patientUid,visitDate,visitId;


    public homeUserClass() {

    }

    public homeUserClass(String doctorName,String visitDate , String visitId, String doctorPhoneNumber,String doctorType , String chamber){
        this.doctorName = doctorName;
        this.visitDate = visitDate;
        this.visitId = visitId;
        this.doctorPhoneNumber = doctorPhoneNumber;
        this.doctorType = doctorType;
        this.chamber = chamber;
    }
    public homeUserClass(String patientName , String visitDate,String visitId,String patientPhoneNumber){
        this.patientName = patientName;
        this.visitDate  = visitDate;
        this.visitId = visitId;
        this.patientPhoneNumber = patientPhoneNumber;
    }


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getDoctorCancelState() {
        return doctorCancelState;
    }

    public void setDoctorCancelState(String doctorCancelState) {
        this.doctorCancelState = doctorCancelState;
    }

    public String getDoctorConfirmState() {
        return doctorConfirmState;
    }

    public void setDoctorConfirmState(String doctorConfirmState) {
        this.doctorConfirmState = doctorConfirmState;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorPhoneNumber() {
        return doctorPhoneNumber;
    }

    public void setDoctorPhoneNumber(String doctorPhoneNumber) {
        this.doctorPhoneNumber = doctorPhoneNumber;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public String getDoctorUid() {
        return doctorUid;
    }

    public void setDoctorUid(String doctorUid) {
        this.doctorUid = doctorUid;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getPatientCancelState() {
        return patientCancelState;
    }

    public void setPatientCancelState(String patientCancelState) {
        this.patientCancelState = patientCancelState;
    }

    public String getPatientConfirmState() {
        return patientConfirmState;
    }

    public void setPatientConfirmState(String patientConfirmState) {
        this.patientConfirmState = patientConfirmState;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhoneNumber() {
        return patientPhoneNumber;
    }

    public void setPatientPhoneNumber(String patientPhoneNumber) {
        this.patientPhoneNumber = patientPhoneNumber;
    }

    public String getPatientReview() {
        return patientReview;
    }

    public void setPatientReview(String patientReview) {
        this.patientReview = patientReview;
    }

    public String getPatientUid() {
        return patientUid;
    }

    public void setPatientUid(String patientUid) {
        this.patientUid = patientUid;
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
}
