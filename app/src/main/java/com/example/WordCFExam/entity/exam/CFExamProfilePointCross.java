package com.example.WordCFExam.entity.exam;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.WordCFExam.entity.TextLabelable;

import java.io.Serializable;

public class CFExamProfilePointCross implements Serializable,TextLabelable {

    @Embedded
    public CFExamProfilePoint cfExamProfilePoint;

    @Relation(
            parentColumn = "CFExamProfileID",
            entityColumn = "CFExamProfileID"
    )
    public CFExamProfile cfExamProfile;

    public CFExamProfilePoint getCfExamProfilePoint() {
        return cfExamProfilePoint;
    }

    public void setCfExamProfilePoint(CFExamProfilePoint cfExamProfilePoint) {
        this.cfExamProfilePoint = cfExamProfilePoint;
    }

    public CFExamProfile getCfExamProfile() {
        return cfExamProfile;
    }

    public void setCfExamProfile(CFExamProfile cfExamProfile) {
        this.cfExamProfile = cfExamProfile;
    }

    public CFExamProfilePointCross() {

    }

    @Override
    public String getLabelText() {
        return String.format("%s",
                cfExamProfilePoint.getName());
    }
}
