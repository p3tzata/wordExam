package com.example.WordCFExam.entity.dto;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.entity.Translation;

import java.io.Serializable;

public class TranslationAndLanguages implements Serializable {

    @Embedded
    public Translation translation;


    @Relation(parentColumn =  "foreignLanguageID", entityColumn = "languageID")
    Language foreignLanguage;

    @Relation(parentColumn =  "nativeLanguageID", entityColumn = "languageID")
    Language nativeLanguage;

    public TranslationAndLanguages() {
    }

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }

    public Language getForeignLanguage() {
        return foreignLanguage;
    }

    public void setForeignLanguage(Language foreignLanguage) {
        this.foreignLanguage = foreignLanguage;
    }

    public Language getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(Language nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }
}
