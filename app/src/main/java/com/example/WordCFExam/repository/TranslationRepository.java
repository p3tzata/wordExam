package com.example.WordCFExam.repository;

import android.app.Application;

import com.example.WordCFExam.dao.TranslationDao;
import com.example.WordCFExam.entity.Translation;
import com.example.WordCFExam.entity.dto.TranslationAndLanguages;

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
