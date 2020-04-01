package com.example.myapplication.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile",indices = {@Index(unique = true, value = {"profileName"})})
public class Profile implements TextLabelable {

    @PrimaryKey
    private Long profileID;

    private String profileName;

    private String profileDesc;

    public Profile() {
    }



    public Long getProfileID() {
        return profileID;
    }

    public void setProfileID(Long profileID) {
        this.profileID = profileID;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileDesc() {
        return profileDesc;
    }

    public void setProfileDesc(String profileDesc) {
        this.profileDesc = profileDesc;
    }

    @Override
    public String getLabelText() {
        return getProfileName();
    }
}
