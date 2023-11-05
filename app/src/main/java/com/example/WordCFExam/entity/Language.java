package com.example.WordCFExam.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "language", indices = {@Index(unique = true, value = {"languageName"})})
public class Language implements Serializable, TextLabelable {

    @PrimaryKey
    private Long languageID;
    @NonNull
    private String languageName;
    private String definitionUrl;
    private String localeLanguageTag;
    private String tts_voice;
    private Float tts_pitch;
    private Float tts_speechRate;

    public Language() {
    }

    public String getLocaleLanguageTag() {
        return localeLanguageTag;
    }

    public void setLocaleLanguageTag(String localeLanguageTag) {
        this.localeLanguageTag = localeLanguageTag;
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

    public String getTts_voice() {
        return tts_voice;
    }

    public void setTts_voice(String tts_voice) {
        this.tts_voice = tts_voice;
    }
    public Float getTts_pitch() {
        return tts_pitch;
    }

    public Float getTts_speechRate() {
        return tts_speechRate;
    }

    public void setTts_speechRate(Float tts_speechRate) {
        this.tts_speechRate = tts_speechRate;
    }

    public void setTts_pitch(Float tts_pitch) {
        this.tts_pitch = tts_pitch;
    }
    @Override
    public String getLabelText() {
        return getLanguageName();
    }
}
