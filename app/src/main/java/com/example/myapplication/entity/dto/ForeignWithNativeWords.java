package com.example.myapplication.entity.dto;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;

import java.util.List;

public class ForeignWithNativeWords {

    @Embedded
    private Word foreignWord;

    @Relation(parentColumn =  "wordID",
              entityColumn = "wordID",
            associateBy = @Junction(value=TranslationWordRelation.class,
                    parentColumn = "foreignWordID",
                    entityColumn = "nativeWordID"

                    ))

    private List<Word> nativeWords;

    public ForeignWithNativeWords() {
    }

    public Word getForeignWord() {
        return foreignWord;
    }

    public void setForeignWord(Word foreignWord) {
        this.foreignWord = foreignWord;
    }

    public List<Word> getNativeWords() {
        return nativeWords;
    }

    public void setNativeWords(List<Word> nativeWords) {
        this.nativeWords = nativeWords;
    }
}
