package com.example.myapplication.entity;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("languageID"),
        @Index(unique = true, value = {"languageID","wordFormName"})},
        foreignKeys = {
           @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = Language.class, parentColumns = "languageID", childColumns = "languageID")
                })
public class WordForm implements TextLabelable{

    @PrimaryKey
    private Long wordFormID;

    @NonNull
    private Long languageID;

    @NonNull
    private String wordFormName;

    public WordForm() {
    }

    public Long getWordFormID() {
        return wordFormID;
    }

    public void setWordFormID(Long wordFormID) {
        this.wordFormID = wordFormID;
    }

    @NonNull
    public Long getLanguageID() {
        return languageID;
    }

    public void setLanguageID(@NonNull Long languageID) {
        this.languageID = languageID;
    }

    @NonNull
    public String getWordFormName() {
        return wordFormName;
    }

    public void setWordFormName(@NonNull String wordFormName) {
        this.wordFormName = wordFormName;
    }

    @Override
    public String getLabelText() {
        return getWordFormName();
    }
}
