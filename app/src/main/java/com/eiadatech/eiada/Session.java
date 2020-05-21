package com.eiadatech.eiada;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.eiadatech.eiada.Retrofit.Models.PatientModel;

public class Session {

    static final String User_ID = "id";
    static final String User_Email = "email";
    static final String User_Name = "name";
    static final String User_Password = "password";
    static final String User_Gender = "gender";
    static final String User_Date_Of_Birth = "dateOfBirth";
    static final String Remember_Password = "rememberPassword";
    static final String Image = "image";
    static final String Phone = "phone";
    static final String Address = "address";
    static final String Device_Token = "token";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUser(Context ctx, PatientModel user) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(User_ID, user.getPatientId());
        editor.putString(User_Email, user.getEmail());
        editor.putString(User_Password, user.getPassword());
        editor.putString(User_Name, user.getName());
        editor.putString(User_Gender, user.getGender());
        editor.putString(User_Date_Of_Birth, user.getDateOfBirth());
        editor.putString(Phone, user.getMobile());
        editor.commit();
    }


    public static String getUser_Email(Context ctx) {
        return getSharedPreferences(ctx).getString(User_Email, "");
    }

    public static String getUser_Mobile(Context ctx) {
        return getSharedPreferences(ctx).getString(Phone, "");
    }


    public static void saveMobileNumber(Context ctx, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Phone, value);
        editor.commit();
    }

    public static void savePrimaryAddress(Context ctx, String address) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Address, address);
        editor.commit();
    }

    public static void saveName(Context ctx, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(User_Name, value);
        editor.commit();
    }

    public static void saveEmail(Context ctx, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(User_Email, value);
        editor.commit();
    }

    public static void saveGender(Context ctx, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(User_Gender, value);
        editor.commit();
    }

    public static void saveDateOfBirth(Context ctx, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(User_Date_Of_Birth, value);
        editor.commit();
    }


    public static void saveToken(Context ctx, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Device_Token, value);
        editor.commit();
    }

    public static void saveImage(Context ctx, String path) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Image, path);
        editor.commit();
    }

    public static String getImage(Context ctx) {
        return getSharedPreferences(ctx).getString(Image, "");
    }

    public static String getPfrimaryAddress(Context ctx) {
        return getSharedPreferences(ctx).getString(Address, "");
    }

    public static String getDevice_Token(Context ctx) {
        return getSharedPreferences(ctx).getString(Device_Token, "");
    }


    public static void savePassword(Context ctx, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(User_Password, value);
        editor.commit();
    }


    public static String getRememberPassword(Context ctx) {
        return getSharedPreferences(ctx).getString(Remember_Password, "");
    }


    public static void saveRememberPassword(Context ctx, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(Remember_Password, value);
        editor.commit();
    }


    public static String getUser_ID(Context ctx) {
        return getSharedPreferences(ctx).getString(User_ID, "");
    }

    public static void setUser_ID(Context ctx, String email) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(User_ID, email);
        editor.commit();
    }

    public static String getUser_Name(Context ctx) {
        return getSharedPreferences(ctx).getString(User_Name, "");
    }

    public static String getUser_Gender(Context ctx) {
        return getSharedPreferences(ctx).getString(User_Gender, "");
    }

    public static String getUser_Date_Of_Birth(Context ctx) {
        return getSharedPreferences(ctx).getString(User_Date_Of_Birth, "");
    }

    public static String getUser_Password(Context ctx) {
        return getSharedPreferences(ctx).getString(User_Password, "");
    }


}
