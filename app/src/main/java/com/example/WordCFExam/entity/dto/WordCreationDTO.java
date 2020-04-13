package com.example.WordCFExam.entity.dto;

import androidx.annotation.NonNull;

public class WordCreationDTO {

    @NonNull
    private String wordString;

    @NonNull
    private Long languageID;

    @NonNull
    private Long profileID;

    public WordCreationDTO(@NonNull String wordString, @NonNull Long languageID, @NonNull Long profileID) {
        this.wordString = wordString;
        this.languageID = languageID;
        this.profileID = profileID;
    }

    public WordCreationDTO() {
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

    @NonNull
    public Long getProfileID() {
        return profileID;
    }

    public void setProfileID(@NonNull Long profileID) {
        this.profileID = profileID;
    }



}
