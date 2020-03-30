package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.Word;
import com.example.myapplication.repository.WordRepository;
import com.example.myapplication.service.base.BaseCrudService;
import com.example.myapplication.service.base.BaseNameCrudService;
import com.example.myapplication.service.base.CrudService;
import com.example.myapplication.service.base.NameableCrudService;

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


}
