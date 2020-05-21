package com.eiadatech.eiada.Retrofit.Models;

import com.google.gson.annotations.SerializedName;

public class ReviewModel {
    @SerializedName("reviewId")
    private String reviewId;

    @SerializedName("professionalId")
    private String professionalId;

    @SerializedName("userId")
    private String patientId;

    @SerializedName("gender")
    private String patientGender;

    @SerializedName("patientImage")
    private String patientImage;

    @SerializedName("name")
    private String patientName;

    @SerializedName("appointmentId")
    private String appointmentId;

    @SerializedName("review")
    private String review;

    @SerializedName("comment")
    private String comment;

    @SerializedName("date")
    private String date;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientImage() {
        return patientImage;
    }

    public void setPatientImage(String patientImage) {
        this.patientImage = patientImage;
    }
}
