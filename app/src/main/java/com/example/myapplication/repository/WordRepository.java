package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.LanguageDao;
import com.example.myapplication.dao.WordDao;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Word;

public class WordRepository extends NameableCrudRepository<WordDao, Word> {

    public WordRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().wordDao());
    }
}
