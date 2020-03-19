package com.example.myapplication.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = "foreignWordID"), @Index(value = "nativeWordID"),
                   @Index(unique = true, value = {"foreignWordID","nativeWordID"}) },
        foreignKeys = {@ForeignKey(entity = Word.class, parentColumns = "wordID", childColumns = "foreignWordID"),
                @ForeignKey(entity = Word.class, parentColumns = "wordID", childColumns = "nativeWordID") })
public class TranslationWordRelation {

    @PrimaryKey
    private Long translationWordRelationID;

    @NonNull
    private Long foreignWordID;

    @NonNull
    private Long nativeWordID;

    public TranslationWordRelation() {
    }

    public Long getTranslationWordRelationID() {
        return translationWordRelationID;
    }

    public void setTranslationWordRelationID(Long translationWordRelationID) {
        this.translationWordRelationID = translationWordRelationID;
    }

    @NonNull
    public Long getForeignWordID() {
        return foreignWordID;
    }

    public void setForeignWordID(@NonNull Long foreignWordID) {
        this.foreignWordID = foreignWordID;
    }

    @NonNull
    public Long getNativeWordID() {
        return nativeWordID;
    }

    public void setNativeWordID(@NonNull Long nativeWordID) {
        this.nativeWordID = nativeWordID;
    }
}
