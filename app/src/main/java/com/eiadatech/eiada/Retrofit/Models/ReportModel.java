package com.eiadatech.eiada.Retrofit.Models;

import com.google.gson.annotations.SerializedName;

public class ReportModel {


    @SerializedName("reportId")
    private String reportId;


    @SerializedName("professionalId")
    private String professionalId;

    @SerializedName("professionalName")
    private String professionalName;

    @SerializedName("specialist")
    private String specialist;

    @SerializedName("professionalImage")
    private String professionalImage;

    @SerializedName("patientId")
    private String patientId;

    @SerializedName("patient")
    private String patientName;

    @SerializedName("gender")
    private String gender;
    @SerializedName("dateOfBirth")
    private String dateOfBirth;


    @SerializedName("license")
    private String license;


    @SerializedName("email")
    private String email;


    @SerializedName("diagnosis")
    private String diagnosis;

    @SerializedName("appointmentId")
    private String appointmentId;


    @SerializedName("date")
    private String date;


    @SerializedName("time")
    private String time;


    @SerializedName("duration")
    private String duration;


    @SerializedName("reference")
    private String reference;


    @SerializedName("complaint")
    private String complaint;


    @SerializedName("assesment")
    private String assesment;

    @SerializedName("plan")
    private String plan;

    @SerializedName("medication")
    private String medication;

    @SerializedName("impression")
    private String impression;


    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getAssesment() {
        return assesment;
    }

    public void setAssesment(String assesment) {
        this.assesment = assesment;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getImpression() {
        return impression;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }


    public String getProfessionalImage() {
        return professionalImage;
    }

    public void setProfessionalImage(String professionalImage) {
        this.professionalImage = professionalImage;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }


}
