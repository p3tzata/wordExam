package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.dto.TranslationAndLanguages;
import com.example.myapplication.repository.TranslationRepository;

import java.util.List;


public class TranslationService extends BaseCrudService<TranslationRepository, Translation> implements NameableCrudService<Translation> {

    public TranslationService(Application application) {
        super(application,new TranslationRepository(application));
    }

    public List<Translation> findAllByProfile(Long profileID){
        return super.getRepository().findAllByProfile(profileID);
    }

    public List<TranslationAndLanguages> findAllTransAndLangByProfile(Long profileID){
        return super.getRepository().findAllTransAndLangByProfile(profileID);
    }


    @Override
    public List<Translation> findAllOrderAlphabetic(Object... objects) {

        if (objects.length==1) {
            if (objects[0] instanceof Long) {
                Long profileID =  (Long) objects[0];
                return super.getRepository().findAllByProfile(profileID);
            }
        }
        return null;
    }
}
