package com.example.quickfixapp.model;

import java.sql.Date;
import java.sql.Timestamp;

public class clientSignupModel {

    private String userId;
    private String lastName;
    private String firstName;
    private String mIddleName;
    private String userName;
    private String phoneNum;
    private String email;
    private int role;
    private String dateRegistered;
    private boolean isVerified;

    public clientSignupModel(String userId, String lastName, String firstName, String mIddleName, String userName, String phoneNum, String email, Integer role, String dateRegistered, boolean isVerified) {
        this.userId = userId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.mIddleName = mIddleName;
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.email = email;
        this.role = role;
        this.dateRegistered = dateRegistered;
        this.isVerified = isVerified;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getmIddleName() {
        return mIddleName;
    }

    public void setmIddleName(String mIddleName) {
        this.mIddleName = mIddleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRole() {
        return role;
    }
    public void setRole(Integer role) {
        this.role = role;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }
    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

}


