package com.example.WordCFExam.service;

import android.app.Application;

import com.example.WordCFExam.entity.Language;
import com.example.WordCFExam.repository.LanguageRepository;
import com.example.WordCFExam.service.base.BaseNameCrudService;


public class LanguageService extends BaseNameCrudService<LanguageRepository, Language> {

    public LanguageService(Application application) {
        super(application,new LanguageRepository(application));
    }

}
