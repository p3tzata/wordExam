package com.example.myapplication.factory;

import android.app.Application;

import com.example.myapplication.service.LanguageService;
import com.example.myapplication.service.ProfileService;
import com.example.myapplication.service.TranslationService;

import com.example.myapplication.service.WordService;

public class FactoryUtil {

        static public ProfileService createProfileService(Application application) {
            return new ProfileService(application);
        }

       static public LanguageService createLanguageService(Application application) {
           return new LanguageService(application);
       }

       static public TranslationService createTranslationService(Application application) {
         return new TranslationService(application);
       }

    static public WordService createWordService(Application application) {
        return new WordService(application);
    }



}
