package com.example.WordCFExam.repository;

import android.app.Application;

import com.example.WordCFExam.dao.LanguageDao;
import com.example.WordCFExam.entity.Language;

public class LanguageRepository extends BaseNameCrudRepository<LanguageDao, Language> {

    public LanguageRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().languageDao());
    }
}
