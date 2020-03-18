package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.Profile;
import com.example.myapplication.entity.Translation;
import com.example.myapplication.repository.ProfileRepository;
import com.example.myapplication.repository.TranslationRepository;


public class TranslationService extends NameableCrudService<TranslationRepository, Translation> {

    public TranslationService(Application application) {
        super(application,new TranslationRepository(application));
    }

}
