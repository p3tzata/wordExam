package com.example.WordCFExam.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity(tableName = "partOfSpeech",
        indices = {
                @Index(value = "languageID"),
                @Index(unique = true,value = {"name","languageID"}) },
        foreignKeys = {
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = Language.class, parentColumns = "languageID", childColumns = "languageID"),
        }
)
public class PartOfSpeech implements TextLabelable {

    @PrimaryKey
    private Long partOfSpeechID;

    @NonNull
    private Long languageID;

    @NonNull
    private String name;

    private String desc;

    private String example;

    public PartOfSpeech() {
    }

    public Long getPartOfSpeechID() {
        return partOfSpeechID;
    }

    public void setPartOfSpeechID(Long partOfSpeechID) {
        this.partOfSpeechID = partOfSpeechID;
    }

    @NonNull
    public Long getLanguageID() {
        return languageID;
    }

    public void setLanguageID(@NonNull Long languageID) {
        this.languageID = languageID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @Override
    public String getLabelText() {
        return getName();
    }
}
