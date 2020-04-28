package com.example.WordCFExam.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "helpSentence",
        indices = {
                @Index(value = "wordID"),@Index(value = "toLanguageID")},
        foreignKeys = {
                @ForeignKey(onDelete = ForeignKey.CASCADE,entity = Word.class, parentColumns = "wordID", childColumns = "wordID"),
                @ForeignKey(onDelete = ForeignKey.CASCADE,entity = Language.class, parentColumns = "languageID", childColumns = "toLanguageID")
        }
)
public class HelpSentence implements Serializable,TextLabelable {

    @PrimaryKey
    private Long helpSentenceID;
    @NonNull
    private Long wordID;
    @NonNull
    private Long toLanguageID;

    private String sentenceTranslation;

    @NonNull
    private String sentenceString;


    public String getSentenceTranslation() {
        return sentenceTranslation;
    }

    public void setSentenceTranslation(String sentenceTranslation) {
        this.sentenceTranslation = sentenceTranslation;
    }

    public HelpSentence() {
    }

    @NonNull
    public Long getToLanguageID() {
        return toLanguageID;
    }

    public void setToLanguageID(@NonNull Long toLanguageID) {
        this.toLanguageID = toLanguageID;
    }

    public Long getHelpSentenceID() {
        return helpSentenceID;
    }

    public void setHelpSentenceID(Long helpSentenceID) {
        this.helpSentenceID = helpSentenceID;
    }

    @NonNull
    public Long getWordID() {
        return wordID;
    }

    public void setWordID(@NonNull Long wordID) {
        this.wordID = wordID;
    }

    @NonNull
    public String getSentenceString() {
        return sentenceString;
    }

    public void setSentenceString(@NonNull String sentenceString) {
        this.sentenceString = sentenceString;
    }

    @Override
    public String getLabelText() {
        return getSentenceString();
    }
}
