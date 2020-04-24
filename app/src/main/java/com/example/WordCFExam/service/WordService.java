package com.example.WordCFExam.service;

import android.app.Application;

import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.WordCFExamCross;
import com.example.WordCFExam.repository.WordRepository;
import com.example.WordCFExam.service.base.BaseCrudService;
import com.example.WordCFExam.service.base.CrudService;

import java.util.List;


public class WordService extends BaseCrudService<WordRepository, Word> implements CrudService<Word> {

    public WordService(Application application) {
        super(application,new WordRepository(application));
    }

    public Word findByWordStringAndProfileIDAndLanguageID(String wordString,Long profileID,Long languageID){

        return super.getRepository().findByWordStringAndProfileIDAndLanguageID(wordString,profileID,languageID);

    }

    public List<Word> findByWordStringContainsAndProfileIDAndLanguageID(String wordStringContain, Long profileID, Long languageID){

        return super.getRepository().findByWordStringContainsAndProfileIDAndLanguageID(wordStringContain,profileID,languageID);

    }


    public List<WordCFExamCross> findByWordStringContainsAndProfileIDAndLanguageIDCFExamCross(String wordStringContain, Long profileId, Long langID, Long targetTranslateLangID){
        return super.getRepository().findByWordStringContainsAndProfileIDAndLanguageIDCFExamCross(wordStringContain, profileId, langID,targetTranslateLangID);
    }







}
