package com.example.myapplication.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "wordRelationID"), @Index(value = "wordID"),
                   @Index(unique = true, value = {"wordRelationID","wordID"}) },
        foreignKeys = @ForeignKey(entity = Word.class, parentColumns = "wordID", childColumns = "wordID"))
public class TranslationWordRelation {

    @PrimaryKey
    private Long translationWordRelationID;

    @NonNull
    private Long wordRelationID;

    @NonNull
    private Long wordID;

    public TranslationWordRelation() {
    }

    public Long getTranslationWordRelationID() {
        return translationWordRelationID;
    }

    public void setTranslationWordRelationID(Long translationWordRelationID) {
        this.translationWordRelationID = translationWordRelationID;
    }

    @NonNull
    public Long getWordRelationID() {
        return wordRelationID;
    }

    public void setWordRelationID(@NonNull Long wordRelationID) {
        this.wordRelationID = wordRelationID;
    }

    @NonNull
    public Long getWordID() {
        return wordID;
    }

    public void setWordID(@NonNull Long wordID) {
        this.wordID = wordID;
    }
}
