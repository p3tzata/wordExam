package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.LanguageDao;
import com.example.myapplication.dao.ProfileDao;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Profile;

public class LanguageRepository extends NameableCrudRepository<LanguageDao, Language> {

    public LanguageRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().languageDao());
    }
}
