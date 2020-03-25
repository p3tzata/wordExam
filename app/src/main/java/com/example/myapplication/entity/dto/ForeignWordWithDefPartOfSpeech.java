package com.example.myapplication.entity.dto;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.myapplication.entity.PartOfSpeech;
import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.WordPartOfSpeech;

import java.util.List;

public class ForeignWordWithDefPartOfSpeech {

    @Embedded
    private Word foreignWord;

    @Relation(parentColumn =  "wordID",
              entityColumn = "partOfSpeechID",
            associateBy = @Junction(value= WordPartOfSpeech.class,
                    parentColumn = "wordID",
                    entityColumn = "partOfSpeechID"

                    ))

    private List<PartOfSpeech> partOfSpeeches;

    public ForeignWordWithDefPartOfSpeech() {
    }

    public Word getForeignWord() {
        return foreignWord;
    }

    public void setForeignWord(Word foreignWord) {
        this.foreignWord = foreignWord;
    }

    public List<PartOfSpeech> getPartOfSpeeches() {
        return partOfSpeeches;
    }

    public void setPartOfSpeeches(List<PartOfSpeech> partOfSpeeches) {
        this.partOfSpeeches = partOfSpeeches;
    }
}
