package com.eiadatech.eiada.Retrofit.Models;

import com.google.gson.annotations.SerializedName;

public class PatientModel {

    @SerializedName("userId")
    private String patientId;

    @SerializedName("name")
    private String name;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("gender")
    private String gender;

    @SerializedName("image")
    private String image;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("roleId")
    private String roleId;

    @SerializedName("address")
    private String address;

    @SerializedName("messageNotifications")
    private String messageNotifications;


    public PatientModel() {
    }

    public PatientModel(String patientId) {
        this.patientId = patientId;
    }

    public PatientModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public PatientModel(String name, String email, String password, String gender, String dateOfBirth) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
