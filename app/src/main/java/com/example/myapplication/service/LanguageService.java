package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Profile;
import com.example.myapplication.repository.LanguageRepository;
import com.example.myapplication.repository.ProfileRepository;


public class LanguageService extends NameableCrudService<LanguageRepository, Language> {

    public LanguageService(Application application) {
        super(application,new LanguageRepository(application));
    }

}
