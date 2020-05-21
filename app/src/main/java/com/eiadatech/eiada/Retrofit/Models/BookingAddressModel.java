package com.eiadatech.eiada.Retrofit.Models;

import com.google.gson.annotations.SerializedName;

public class BookingAddressModel {

    @SerializedName("bookingAddressId")
    private String bookingAddressId;

    @SerializedName("userId")
    private String userId;

    @SerializedName("locationName")
    private String locationName;

    @SerializedName("address")
    private String address;

    @SerializedName("type")
    private String type;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    public BookingAddressModel() {
    }


    public BookingAddressModel(String bookingAddressId) {
        this.bookingAddressId = bookingAddressId;
    }

    public BookingAddressModel(String userId, String locationName, String address, String mobile, String latitude, String longitude, String type) {
        this.userId = userId;
        this.locationName = locationName;
        this.address = address;
        this.mobile = mobile;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public BookingAddressModel(String bookingAddressId, String userId, String locationName, String address, String type, String mobile, String latitude, String longitude) {
        this.bookingAddressId = bookingAddressId;
        this.userId = userId;
        this.locationName = locationName;
        this.address = address;
        this.type = type;
        this.mobile = mobile;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBookingAddressId() {
        return bookingAddressId;
    }

    public void setBookingAddressId(String bookingAddressId) {
        this.bookingAddressId = bookingAddressId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
