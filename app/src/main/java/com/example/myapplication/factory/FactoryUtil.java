package com.example.myapplication.factory;

import android.app.Application;

import com.example.myapplication.entity.TranslationWordRelation;
import com.example.myapplication.service.LanguageService;
import com.example.myapplication.service.ProfileService;
import com.example.myapplication.service.TranslationService;

import com.example.myapplication.service.TranslationWordRelationService;
import com.example.myapplication.service.WordService;

import org.modelmapper.ModelMapper;

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

    static public TranslationWordRelationService createTranslationWordRelationService(Application application) {
        return new TranslationWordRelationService(application);
    }


    static public ModelMapper createModelMapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }




}
