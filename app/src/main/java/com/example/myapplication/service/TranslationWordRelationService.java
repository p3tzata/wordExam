package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.entity.Word;
import com.example.myapplication.repository.TranslationRepository;
import com.example.myapplication.repository.TranslationWordRelationRepository;

import java.util.List;


public class TranslationWordRelationService extends CrudService<TranslationWordRelationRepository, TranslationWordRelation> {



    public TranslationWordRelationService(Application application) {
        super(application,new TranslationWordRelationRepository(application));
    }

    public boolean createRelation(Word attachToDstWord, List<Word> attachSrcWordList) {
        TranslationWordRelation translationWordRelation = findTranslationWordRelationByDstWordID(attachToDstWordID);
        Long wordRelationID=null;

        if (translationWordRelation!=null) {
            wordRelationID=translationWordRelation.getWordRelationID();
        } else {
            wordRelationID=Max;
            TranslationWordRelation newDstTranslationWordRelation = new TranslationWordRelation();
            newDstTranslationWordRelation.setWordID(attachToDstWord.getWordID());
            newDstTranslationWordRelation.setWordRelationID(wordRelationID);
            super.insert(newDstTranslationWordRelation);
        }

        for (int i = 0; i < attachSrcWordList.size(); i++) {

            TranslationWordRelation newTranslationWordRelation = new TranslationWordRelation();
            newTranslationWordRelation.setWordID(attachSrcWordList.get(i).getWordID());
            newTranslationWordRelation.setWordRelationID(wordRelationID);
            super.insert(newTranslationWordRelation);

        }
        
        return true;
    }


    private TranslationWordRelation findTranslationWordRelationByDstWordID(Long ID) {
        return getRepository().findTranslationWordRelationByDstWordID(ID);
    }


}
