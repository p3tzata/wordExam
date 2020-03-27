package com.example.myapplication.entity.dto;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;

import java.util.List;

public class NativeWithForeignWords {

    @Embedded
    private Word nativeWord;

    @Relation(parentColumn =  "wordID",
              entityColumn = "wordID",
            associateBy = @Junction(value=TranslationWordRelation.class,
                    parentColumn = "nativeWordID",
                    entityColumn = "foreignWordID"

                    ))

    private List<Word> foreignWords;

    public NativeWithForeignWords() {
    }

    public Word getNativeWord() {
        return nativeWord;
    }

    public void setNativeWord(Word nativeWord) {
        this.nativeWord = nativeWord;
    }

    public List<Word> getForeignWords() {
        return foreignWords;
    }

    public void setForeignWords(List<Word> foreignWords) {
        this.foreignWords = foreignWords;
    }
}
