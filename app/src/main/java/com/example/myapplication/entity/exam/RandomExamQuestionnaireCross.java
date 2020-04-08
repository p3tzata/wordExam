package com.example.myapplication.entity.exam;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.TextLabelable;
import com.example.myapplication.entity.Word;

import java.io.Serializable;

public class RandomExamQuestionnaireCross implements Serializable,TextLabelable {

    @Relation(
            parentColumn = "wordID",
            entityColumn = "wordID"
    )
    public Word word;

    @Relation(
            parentColumn = "targetTranslationLanguageID",
            entityColumn = "languageID"
    )
    public Language language;


    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public String getLabelText() {
        return String.format("%s (%s)",
                getWord().getWordString(),getLanguage().getLanguageName());
    }
}
