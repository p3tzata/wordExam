package com.example.WordCFExam.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(indices = {@Index(value = "wordID"), @Index(value = "partOfSpeechID"),
        @Index(unique = true, value = {"wordID","partOfSpeechID"}) },
        foreignKeys = {
                @ForeignKey(onDelete = ForeignKey.CASCADE,entity = Word.class, parentColumns = "wordID", childColumns = "wordID"),
                @ForeignKey(onDelete = ForeignKey.SET_NULL,entity = PartOfSpeech.class, parentColumns = "partOfSpeechID", childColumns = "partOfSpeechID") })
public class WordPartOfSpeech {

    @PrimaryKey
    private Long wordPartOfSpeechID;
    @NonNull
    private Long wordID;
    @NonNull
    private Long partOfSpeechID;

    public WordPartOfSpeech() {
    }

    public Long getWordPartOfSpeechID() {
        return wordPartOfSpeechID;
    }

    public void setWordPartOfSpeechID(Long wordPartOfSpeechID) {
        this.wordPartOfSpeechID = wordPartOfSpeechID;
    }

    @NonNull
    public Long getWordID() {
        return wordID;
    }

    public void setWordID(@NonNull Long wordID) {
        this.wordID = wordID;
    }

    @NonNull
    public Long getPartOfSpeechID() {
        return partOfSpeechID;
    }

    public void setPartOfSpeechID(@NonNull Long partOfSpeechID) {
        this.partOfSpeechID = partOfSpeechID;
    }
}
