package com.example.WordCFExam.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "language",indices = {@Index(unique = true, value = {"languageName"})})
public class Language implements Serializable,TextLabelable {

    @PrimaryKey
    private Long languageID;

    @NonNull
    private String languageName;

    private String definitionUrl;

    public Language() {
    }

    public Long getLanguageID() {
        return languageID;
    }

    public void setLanguageID(Long languageID) {
        this.languageID = languageID;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getDefinitionUrl() {
        return definitionUrl;
    }

    public void setDefinitionUrl(String definitionUrl) {
        this.definitionUrl = definitionUrl;
    }

    @Override
    public String getLabelText() {
        return getLanguageName();
    }
}
