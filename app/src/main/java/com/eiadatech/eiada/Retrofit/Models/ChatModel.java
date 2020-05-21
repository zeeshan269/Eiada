package com.eiadatech.eiada.Retrofit.Models;

public class ChatModel {
    private String chatId;
    private String userId;
    private String senderId;
    private String receiverId;
    private String name;
    private String message;
    private String createdAt;

    public ChatModel(String userId, String senderId, String receiverId, String name, String message) {
        this.userId = userId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.name = name;
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
