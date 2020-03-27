package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.LanguageDao;
import com.example.myapplication.entity.Language;

public class LanguageRepository extends BaseNameCrudRepository<LanguageDao, Language> {

    public LanguageRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().languageDao());
    }
}
