package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.Language;
import com.example.myapplication.entity.Word;
import com.example.myapplication.repository.LanguageRepository;
import com.example.myapplication.repository.WordRepository;


public class WordService extends NameableCrudService<WordRepository, Word> {

    public WordService(Application application) {
        super(application,new WordRepository(application));
    }

}
