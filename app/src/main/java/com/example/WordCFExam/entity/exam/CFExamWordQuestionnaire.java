package com.example.WordCFExam.entity.exam;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.WordCFExam.database.DateConverter;
import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.entity.TextLabelable;
import com.example.WordCFExam.entity.Word;

import java.io.Serializable;
import java.util.Date;

@Entity(
        indices = {
                @Index(value = "currentCFExamProfilePointID"),
                @Index(value = "targetTranslationLanguageID"),
                @Index(value = "wordID"),
                @Index(unique = true,value = {"currentCFExamProfilePointID","targetTranslationLanguageID","wordID"})},
        foreignKeys = {
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = CFExamProfilePoint.class, parentColumns = "CFExamProfilePointID", childColumns = "currentCFExamProfilePointID"),
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = Language.class, parentColumns = "languageID", childColumns = "targetTranslationLanguageID"),
                @ForeignKey(onDelete = ForeignKey.CASCADE,entity = Word.class, parentColumns = "wordID", childColumns = "wordID")
        }
)
@TypeConverters(DateConverter.class)
public class CFExamWordQuestionnaire implements Serializable, TextLabelable {

    @PrimaryKey
    private Long CFExamQuestionnaireID;

    @NonNull
    private Long currentCFExamProfilePointID;

    @NonNull
    private Long targetTranslationLanguageID;

    @NonNull
    private Long wordID;

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
    public Long getCurrentCFExamProfilePointID() {
        return currentCFExamProfilePointID;
    }

    public void setCurrentCFExamProfilePointID(@NonNull Long currentCFExamProfilePointID) {
        this.currentCFExamProfilePointID = currentCFExamProfilePointID;
    }

    @NonNull
    public Long getTargetTranslationLanguageID() {
        return targetTranslationLanguageID;
    }

    public void setTargetTranslationLanguageID(@NonNull Long targetTranslationLanguageID) {
        this.targetTranslationLanguageID = targetTranslationLanguageID;
    }

    @NonNull
    public Long getWordID() {
        return wordID;
    }

    public void setWordID(@NonNull Long wordID) {
        this.wordID = wordID;
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
