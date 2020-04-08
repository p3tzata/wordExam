package com.example.myapplication.entity.exam;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.myapplication.entity.Profile;
import com.example.myapplication.entity.Word;

@Entity(
        indices = {
                @Index(value = "profileID")},
        foreignKeys = {
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = Profile.class, parentColumns = "profileID", childColumns = "profileID")
        }
)
public class CFExamProfile {

    @PrimaryKey
    private Long CFExamProfileID;

    @NonNull
    private Long profileID;

    @NonNull
    private String name;

    public Long getCFExamProfileID() {
        return CFExamProfileID;
    }

    public void setCFExamProfileID(Long CFExamProfileID) {
        this.CFExamProfileID = CFExamProfileID;
    }

    @NonNull
    public Long getProfileID() {
        return profileID;
    }

    public void setProfileID(@NonNull Long profileID) {
        this.profileID = profileID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
