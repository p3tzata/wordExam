package com.example.myapplication.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "translation",
        indices = {@Index(value = "nativeLanguageID") ,
                   @Index(value = "foreignLanguageID"),
                   @Index(value = "profileID"),
                   @Index(unique = true,value = {"profileID","nativeLanguageID","foreignLanguageID"}) },

        foreignKeys = {
            @ForeignKey(entity = Language.class, parentColumns = "languageID", childColumns = "nativeLanguageID"),
            @ForeignKey(entity = Language.class, parentColumns = "languageID", childColumns = "foreignLanguageID"),
            @ForeignKey(entity = Profile.class, parentColumns = "profileID", childColumns = "profileID"),
        }
)
public class Translation implements Serializable {

    @PrimaryKey
    private Long translationID;

    @NonNull
    private String translationName;

    private String translationDesc;

    @NonNull
    private Long profileID;

    @NonNull
    private Long nativeLanguageID;

    @NonNull
    private Long foreignLanguageID;

    public Translation() {
    }

    public Long getTranslationID() {
        return translationID;
    }

    public void setTranslationID(Long translationID) {
        this.translationID = translationID;
    }

    @NonNull
    public String getTranslationName() {
        return translationName;
    }

    public void setTranslationName(@NonNull String translationName) {
        this.translationName = translationName;
    }

    public String getTranslationDesc() {
        return translationDesc;
    }

    public void setTranslationDesc(String translationDesc) {
        this.translationDesc = translationDesc;
    }

    @NonNull
    public Long getProfileID() {
        return profileID;
    }

    public void setProfileID(@NonNull Long profileID) {
        this.profileID = profileID;
    }


    @NonNull
    public Long getNativeLanguageID() {
        return nativeLanguageID;
    }

    public void setNativeLanguageID(@NonNull Long nativeLanguageID) {
        this.nativeLanguageID = nativeLanguageID;
    }

    @NonNull
    public Long getForeignLanguageID() {
        return foreignLanguageID;
    }

    public void setForeignLanguageID(@NonNull Long foreignLanguageID) {
        this.foreignLanguageID = foreignLanguageID;
    }
}
