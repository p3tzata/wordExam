package com.example.myapplication.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "language")
public class Language implements Serializable,TextLabelable {

    @PrimaryKey
    private Long languageID;

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
