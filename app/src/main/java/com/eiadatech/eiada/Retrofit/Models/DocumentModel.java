package com.eiadatech.eiada.Retrofit.Models;

import com.google.gson.annotations.SerializedName;

public class DocumentModel {

    @SerializedName("documentId")
    private String documentId;
    @SerializedName("userId")
    private String userId;
    @SerializedName("documentNumber")
    private String documentNumber;
    @SerializedName("image")
    private String image;
    @SerializedName("type")
    private String type;
    @SerializedName("backImage")
    private String backImage;

    public DocumentModel() {
    }


    public DocumentModel(String userId, String documentNumber, String image, String type) {
        this.userId = userId;
        this.documentNumber = documentNumber;
        this.image = image;
        this.type = type;
    }

    public DocumentModel(String userId, String documentNumber, String image, String backImage, String type) {
        this.userId = userId;
        this.documentNumber = documentNumber;
        this.image = image;
        this.backImage = backImage;
        this.type = type;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }
}
