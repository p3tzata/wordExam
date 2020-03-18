package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.LanguageDao;
import com.example.myapplication.dao.TranslationDao;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Translation;

public class TranslationRepository extends NameableCrudRepository<TranslationDao, Translation> {

    public TranslationRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().transactionDao());
    }
}
