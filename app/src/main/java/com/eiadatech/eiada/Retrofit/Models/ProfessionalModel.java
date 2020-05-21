package com.eiadatech.eiada.Retrofit.Models;

import com.google.gson.annotations.SerializedName;

public class ProfessionalModel {

    @SerializedName("professionalId")
    private String professionalId;

    @SerializedName("specialistId")
    private String specialistId;

    @SerializedName("professionId")
    private String professionId;

    @SerializedName("specialistName")
    private String specialistName;

    @SerializedName("profileImage")
    private String profileImage;

    @SerializedName("licenseNumber")
    private String licenseNumber;

    @SerializedName("language")
    private String language;

    @SerializedName("name")
    private String name;

    @SerializedName("total")
    private String total;
    @SerializedName("type")
    private String type;

    @SerializedName("rating")
    private String rating;

    @SerializedName("email")
    private String email;

    @SerializedName("gender")
    private String gender;

    @SerializedName("password")
    private String password;

    @SerializedName("fees")
    private String fees;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("status")
    private String status;

    @SerializedName("experience")
    private String experience;

    @SerializedName("about")
    private String about;

    @SerializedName("address")
    private String address;

    @SerializedName("university")
    private String university;

    @SerializedName("hospitals")
    private String hospitals;

    @SerializedName("messageNotifications")
    private String messageNotifications;

    @SerializedName("specializations")
    private String specializations;


    public ProfessionalModel() {
    }

    public ProfessionalModel(String professionalId, String specialistId, String specialistName, String name, String email, String gender, String password) {
        this.professionalId = professionalId;
        this.specialistId = specialistId;
        this.specialistName = specialistName;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.password = password;
    }

    public ProfessionalModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public ProfessionalModel(String professionId) {
        this.professionId = professionId;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public String getSpecialistId() {
        return specialistId;
    }

    public void setSpecialistId(String specialistId) {
        this.specialistId = specialistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfessionId() {
        return professionId;
    }

    public void setProfessionId(String professionId) {
        this.professionId = professionId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPrice() {
        return fees;
    }

    public void setPrice(String price) {
        this.fees = price;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getHospitals() {
        return hospitals;
    }

    public void setHospitals(String hospitals) {
        this.hospitals = hospitals;
    }

    public String getSpecializations() {
        return specializations;
    }

    public void setSpecializations(String specializations) {
        this.specializations = specializations;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessageNotifications() {
        return messageNotifications;
    }

    public void setMessageNotifications(String messageNotifications) {
        this.messageNotifications = messageNotifications;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
