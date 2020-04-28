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
                @Index(value = "wordID"),
                @Index(unique = true,value = {"targetTranslationLanguageID","wordID"})},
        foreignKeys = {
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = Language.class, parentColumns = "languageID", childColumns = "targetTranslationLanguageID"),
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = Word.class, parentColumns = "wordID", childColumns = "wordID")
        }
)
@TypeConverters(DateConverter.class)
public class RandomExamWordPassedQuestionnaire implements Serializable, TextLabelable {

    @PrimaryKey
    private Long RandomExamPassedQuestionnaireID;


    @NonNull
    private Long targetTranslationLanguageID;

    @NonNull
    private Long wordID;

    @NonNull
    private Date entryPointDateTime;

    public Long getRandomExamPassedQuestionnaireID() {
        return RandomExamPassedQuestionnaireID;
    }

    public void setRandomExamPassedQuestionnaireID(Long randomExamPassedQuestionnaireID) {
        RandomExamPassedQuestionnaireID = randomExamPassedQuestionnaireID;
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

    @Override
    public String getLabelText() {
        return "Test";
    }
}
