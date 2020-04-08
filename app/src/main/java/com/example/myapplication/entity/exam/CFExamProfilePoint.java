package com.example.myapplication.entity.exam;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.myapplication.entity.Profile;

@Entity(
        indices = {
                @Index(value = "CFExamProfileID"),
                @Index(unique = true,value = {"CFExamProfileID","name","lastOfPeriodInMinute"})},
        foreignKeys = {
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = CFExamProfile.class, parentColumns = "CFExamProfileID", childColumns = "CFExamProfileID")
        }
)
public class CFExamProfilePoint {

    @PrimaryKey
    private Long CFExamProfilePointID;

    @NonNull
    private Long CFExamProfileID;

    @NonNull
    private String name;

    @NonNull
    private Long lastOfPeriodInMinute;


    private boolean isLoopRepeat;

    public CFExamProfilePoint() {
        isLoopRepeat=false;
    }

    public Long getCFExamProfilePointID() {
        return CFExamProfilePointID;
    }

    public void setCFExamProfilePointID(Long CFExamProfilePointID) {
        this.CFExamProfilePointID = CFExamProfilePointID;
    }

    @NonNull
    public Long getCFExamProfileID() {
        return CFExamProfileID;
    }

    public void setCFExamProfileID(@NonNull Long CFExamProfileID) {
        this.CFExamProfileID = CFExamProfileID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public Long getLastOfPeriodInMinute() {
        return lastOfPeriodInMinute;
    }

    public void setLastOfPeriodInMinute(@NonNull Long lastOfPeriodInMinute) {
        this.lastOfPeriodInMinute = lastOfPeriodInMinute;
    }

    public boolean getIsLoopRepeat() {
        return isLoopRepeat;
    }

    public void setIsLoopRepeat(boolean loopRepeat) {
        isLoopRepeat = loopRepeat;
    }
}
