package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.LanguageDao;
import com.example.myapplication.dao.WordDao;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Word;

import java.util.List;

public class WordRepository extends NameableCrudRepository<WordDao, Word> {

    public WordRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().wordDao());
    }

    public Word findByWordStringAndProfileIDAndLanguageID(String wordString,Long profileID,Long languageID){

        return super.getDao().findByWordStringAndProfileIDAndLanguageID(wordString,profileID,languageID);

    }

    public List<Word> findByWordStringContainsAndProfileIDAndLanguageID(String wordStringContain, Long profileID, Long languageID){

        return super.getDao().findByWordStringContainsAndProfileIDAndLanguageID(wordStringContain,profileID,languageID);

    }




}
