package com.eiadatech.eiada.Retrofit.Models;

import com.google.gson.annotations.SerializedName;

public class SlotModel {
    @SerializedName("slotId")
    private String slotId;

    @SerializedName("professionalId")
    private String professionalId;

    @SerializedName("name")
    private String name;

    @SerializedName("date")
    private String date;

    @SerializedName("status")
    private String status;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("endTime")
    private String endTime;

    public SlotModel() {
    }

    public SlotModel(String professionalId) {
        this.professionalId = professionalId;
    }


    public SlotModel(String slotId, String professionalId, String name, String date, String status, String startTime, String endTime) {
        this.slotId = slotId;
        this.professionalId = professionalId;
        this.name = name;
        this.date = date;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public SlotModel(String professionalId, String name, String date, String startTime, String endTime, String status) {
        this.professionalId = professionalId;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getProfessionalId() {
        return professionalId;
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
