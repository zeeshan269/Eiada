package com.eiadatech.eiada.Retrofit.Models;

public class TokenModel {
    private String userId;
    private String token;




    public TokenModel(String userId, String tokenId) {
        this.userId = userId;
        this.token = tokenId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTokenId() {
        return token;
    }

    public void setTokenId(String tokenId) {
        this.token = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
