package com.example.WordCFExam.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "helpSentence",
        indices = {
                @Index(value = "wordID")},
        foreignKeys = {
                @ForeignKey(onDelete = ForeignKey.CASCADE,entity = Word.class, parentColumns = "wordID", childColumns = "wordID")
        }
)
public class HelpSentence implements TextLabelable {

    @PrimaryKey
    private Long helpSentenceID;
    @NonNull
    private Long wordID;
    @NonNull
    private String sentenceString;

    public HelpSentence() {
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
