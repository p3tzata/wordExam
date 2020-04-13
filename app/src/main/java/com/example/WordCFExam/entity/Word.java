package com.example.WordCFExam.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(indices = {@Index("languageID"),@Index("profileID"),@Index("wordFormID"),
        @Index(unique = true, value = {"wordString","languageID","profileID"})},
        foreignKeys = {
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = Language.class, parentColumns = "languageID", childColumns = "languageID"),
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = Profile.class, parentColumns = "profileID", childColumns = "profileID")})
public class Word implements Serializable, TextLabelable {

    @PrimaryKey
    private Long wordID;

    @NonNull
    private String wordString;

    @NonNull
    private Long languageID;

    @NonNull
    private Long profileID;

    private Long wordFormID;

    public Word() {
    }


    public Long getWordFormID() {
        return wordFormID;
    }

    public void setWordFormID(Long wordFormID) {
        this.wordFormID = wordFormID;
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

    @Override
    public String getLabelText() {
        return getWordString();
    }
}
