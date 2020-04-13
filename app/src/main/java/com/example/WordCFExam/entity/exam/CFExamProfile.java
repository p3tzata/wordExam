package com.example.WordCFExam.entity.exam;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.entity.TextLabelable;

import java.io.Serializable;

@Entity(
        indices = {
                @Index(value = "profileID")},
        foreignKeys = {
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = Profile.class, parentColumns = "profileID", childColumns = "profileID")
        }
)
public class CFExamProfile implements Serializable, TextLabelable {

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

    @Override
    public String getLabelText() {
        return getName();
    }
}
