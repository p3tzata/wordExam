package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.LanguageDao;
import com.example.myapplication.dao.TranslationDao;
import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.dto.TranslationAndLanguages;

import java.util.List;

public class TranslationRepository extends NameableCrudRepository<TranslationDao, Translation> {

    public TranslationRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().transactionDao());
    }

    public List<Translation> findAllByProfile(Long profileID){
       return super.getDao().findAllByProfile(profileID);
    }

    public List<TranslationAndLanguages> findAllTransAndLangByProfile(Long profileID){
        return super.getDao().findAllTransAndLangByProfile(profileID);
    }


}
