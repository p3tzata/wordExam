package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.TranslationWordRelationDao;
import com.example.myapplication.dao.WordPartOfSpeechDao;
import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.WordPartOfSpeech;
import com.example.myapplication.entity.dto.ForeignWithNativeWords;
import com.example.myapplication.entity.dto.ForeignWordWithDefPartOfSpeech;

import java.util.List;


public class WordPartOfSpeechRepository extends CrudRepository<WordPartOfSpeechDao, WordPartOfSpeech> {

    public WordPartOfSpeechRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().wordPartOfSpeechDao());
    }

    public ForeignWordWithDefPartOfSpeech findForeignWordWithDefPartOfSpeech(Long foreignWordID){
        return super.getDao().findForeignWordWithDefPartOfSpeech(foreignWordID);
    }

    public WordPartOfSpeech findByWordIDAndPartOfSpeechID(Long wordID,Long partOfSpeechID){
        return super.getDao().findByWordIDAndPartOfSpeechID(wordID,partOfSpeechID);
    }

}
