package com.eiadatech.eiada.Retrofit.Models;

import com.google.gson.annotations.SerializedName;

public class FamilyMemberModel {

    @SerializedName("familyMemberId")
    private String familyMemberId;

    @SerializedName("userId")
    private String userId;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private String age;

    @SerializedName("icon")
    private String icon;

    @SerializedName("relation")
    private String relation;

    @SerializedName("gender")
    private String gender;

    @SerializedName("dateOfBirth")
    private String birthDay;

    public FamilyMemberModel() {
    }

    public FamilyMemberModel(String familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public FamilyMemberModel(String name, String relation, String gender, String birthDay, String userId) {
        this.name = name;
        this.relation = relation;
        this.gender = gender;
        this.birthDay = birthDay;
        this.userId = userId;
    }

    public String getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(String familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
