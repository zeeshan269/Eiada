package com.eiadatech.eiada.Retrofit.Models;

import com.google.gson.annotations.SerializedName;

public class SymptonsModel {

    @SerializedName("symptonId")
    private String symptonId;
    @SerializedName("professionId")
    private String professionId;
    @SerializedName("category")
    private String category;
    @SerializedName("name")
    private String name;
    @SerializedName("date")
    private String date;

    public SymptonsModel(String professionId) {
        this.professionId = professionId;
    }



    public SymptonsModel() {
    }

    public String getSymptonId() {
        return symptonId;
    }

    public void setSymptonId(String symptonId) {
        this.symptonId = symptonId;
    }

    public String getProfessionId() {
        return professionId;
    }

    public void setProfessionId(String professionId) {
        this.professionId = professionId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
