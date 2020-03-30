package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.Language;
import com.example.myapplication.repository.LanguageRepository;
import com.example.myapplication.service.base.BaseNameCrudService;


public class LanguageService extends BaseNameCrudService<LanguageRepository, Language> {

    public LanguageService(Application application) {
        super(application,new LanguageRepository(application));
    }

}
