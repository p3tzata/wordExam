package com.example.WordCFExam.entity.exam;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.WordCFExam.database.DateConverter;
import com.example.WordCFExam.entity.TextLabelable;
import com.example.WordCFExam.entity.Topic;

import java.io.Serializable;
import java.util.Date;

@Entity(
        indices = {
                @Index(value = "currentCFExamProfilePointID"),
                @Index(value = "topicID"),
                @Index(unique = true,value = {"currentCFExamProfilePointID","topicID"})},
        foreignKeys = {
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = CFExamProfilePoint.class, parentColumns = "CFExamProfilePointID", childColumns = "currentCFExamProfilePointID"),
                @ForeignKey(onDelete = ForeignKey.CASCADE,entity = Topic.class, parentColumns = "topicID", childColumns = "topicID")
        }
)
@TypeConverters(DateConverter.class)
public class CFExamTopicQuestionnaire implements Serializable, TextLabelable {

    @PrimaryKey
    private Long CFExamQuestionnaireID;

    @NonNull
    private Long currentCFExamProfilePointID;


    @NonNull
    private Long topicID;

    @NonNull
    private Date entryPointDateTime;

    private Integer postponeInMinute;

    public Long getCFExamQuestionnaireID() {
        return CFExamQuestionnaireID;
    }

    public void setCFExamQuestionnaireID(Long CFExamQuestionnaireID) {
        this.CFExamQuestionnaireID = CFExamQuestionnaireID;
    }

    @NonNull
    public Long getTopicID() {
        return topicID;
    }

    public void setTopicID(@NonNull Long topicID) {
        this.topicID = topicID;
    }

    @NonNull
    public Long getCurrentCFExamProfilePointID() {
        return currentCFExamProfilePointID;
    }

    public void setCurrentCFExamProfilePointID(@NonNull Long currentCFExamProfilePointID) {
        this.currentCFExamProfilePointID = currentCFExamProfilePointID;
    }



    @NonNull
    public Date getEntryPointDateTime() {
        return entryPointDateTime;
    }

    public void setEntryPointDateTime(@NonNull Date entryPointDateTime) {
        this.entryPointDateTime = entryPointDateTime;
    }

    public Integer getPostponeInMinute() {
        return postponeInMinute;
    }

    public void setPostponeInMinute(Integer postponeInMinute) {
        this.postponeInMinute = postponeInMinute;
    }

    @Override
    public String getLabelText() {
        return "Test";
    }
}
