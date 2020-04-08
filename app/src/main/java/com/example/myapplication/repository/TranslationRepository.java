package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.TranslationDao;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.dto.TranslationAndLanguages;

import java.util.List;

public class TranslationRepository extends BaseNameCrudRepository<TranslationDao, Translation> {

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

    public Translation findByNativeLanguageIDAndForeignLanguageID(Long profileID,Long nativeLanguageID, Long foreignLanguageID){
        return getDao().findByNativeLanguageIDAndForeignLanguageID(profileID, nativeLanguageID, foreignLanguageID);
    }

    }
