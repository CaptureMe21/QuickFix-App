package com.example.quickfixapp.model;

import java.io.Serializable;

public class MechanicModel implements Serializable {
    private String mFirstName;
    private String mLastName;
    private String mMiddleName;
    private String mSpecialty;
    private String mEmail;
    private String mPassword;

    private String dateAdded;
    public MechanicModel(){}

    public MechanicModel(String mFirstName, String mLastName, String mMiddleName, String mSpecialty, String mEmail, String mPassword, String dateAdded) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mMiddleName = mMiddleName;
        this.mSpecialty = mSpecialty;
        this.mEmail = mEmail;
        this.mPassword = mPassword;
        this.dateAdded = dateAdded;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmMiddleName() {
        return mMiddleName;
    }

    public void setmMiddleName(String mMiddleName) {
        this.mMiddleName = mMiddleName;
    }

    public String getmSpecialty() {
        return mSpecialty;
    }

    public void setmSpecialty(String mSpecialty) {
        this.mSpecialty = mSpecialty;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
