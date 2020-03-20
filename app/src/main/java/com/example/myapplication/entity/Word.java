package com.example.myapplication.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index("languageID"),@Index("profileID"),
        @Index(unique = true, value = {"wordString","languageID","profileID"})},
        foreignKeys = {@ForeignKey(entity = Language.class, parentColumns = "languageID", childColumns = "languageID"),
                @ForeignKey(entity = Profile.class, parentColumns = "profileID", childColumns = "profileID")})
public class Word implements Serializable {

    @PrimaryKey
    private Long wordID;

    @NonNull
    private String wordString;

    @NonNull
    private Long languageID;

    @NonNull
    private Long profileID;

    public Word() {
    }

    @NonNull
    public Long getProfileID() {
        return profileID;
    }

    public void setProfileID(@NonNull Long profileID) {
        this.profileID = profileID;
    }

    public Long getWordID() {
        return wordID;
    }

    public void setWordID(Long wordID) {
        this.wordID = wordID;
    }

    @NonNull
    public String getWordString() {
        return wordString;
    }

    public void setWordString(@NonNull String wordString) {
        this.wordString = wordString;
    }

    @NonNull
    public Long getLanguageID() {
        return languageID;
    }

    public void setLanguageID(@NonNull Long languageID) {
        this.languageID = languageID;
    }
}
