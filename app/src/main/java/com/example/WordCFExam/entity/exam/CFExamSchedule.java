package com.example.WordCFExam.entity.exam;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.WordCFExam.entity.Profile;

@Entity(
        indices = {
                @Index(value = "profileID")},
        foreignKeys = {
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = Profile.class, parentColumns = "profileID", childColumns = "profileID")
        }
)
public class CFExamSchedule {

    @PrimaryKey
    private Long CFExamSchedule;

    @NonNull
    private Long profileID;

    @NonNull
    private int fromHour;
    @NonNull
    private int toHour;

    public CFExamSchedule() {
    }

    public Long getCFExamSchedule() {
        return CFExamSchedule;
    }

    public void setCFExamSchedule(Long CFExamSchedule) {
        this.CFExamSchedule = CFExamSchedule;
    }

    @NonNull
    public Long getProfileID() {
        return profileID;
    }

    public void setProfileID(@NonNull Long profileID) {
        this.profileID = profileID;
    }

    public int getFromHour() {
        return fromHour;
    }

    public void setFromHour(int fromHour) {
        this.fromHour = fromHour;
    }

    public int getToHour() {
        return toHour;
    }

    public void setToHour(int toHour) {
        this.toHour = toHour;
    }
}
