package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.Translation;
import com.example.myapplication.entity.dto.TranslationAndLanguages;
import com.example.myapplication.repository.BaseNameCrudRepository;
import com.example.myapplication.repository.TranslationRepository;
import com.example.myapplication.service.base.BaseCrudService;
import com.example.myapplication.service.base.BaseNameCrudService;
import com.example.myapplication.service.base.NameableCrudService;

import java.util.List;


public class TranslationService extends BaseNameCrudService<TranslationRepository, Translation> implements NameableCrudService<Translation> {

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
