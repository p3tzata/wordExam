package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.Profile;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.dto.TranslationAndLanguages;
import com.example.myapplication.repository.ProfileRepository;
import com.example.myapplication.repository.TranslationRepository;

import java.util.List;


public class TranslationService extends NameableCrudService<TranslationRepository, Translation> {

    public TranslationService(Application application) {
        super(application,new TranslationRepository(application));
    }

    public List<Translation> findAllByProfile(Long profileID){
        return super.getRepository().findAllByProfile(profileID);
    }

    public List<TranslationAndLanguages> findAllTransAndLangByProfile(Long profileID){
        return super.getRepository().findAllTransAndLangByProfile(profileID);
    }


}
