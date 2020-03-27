package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.WordForm;
import com.example.myapplication.repository.WordFormRepository;

import java.util.List;


public class WordFormService extends BaseCrudService<WordFormRepository, WordForm> {

    public WordFormService(Application application) {
        super(application,new WordFormRepository(application));
    }


    public List<WordForm> findAllByLanguageID(Long ID){
        return super.getRepository().findAllByLanguageID(ID);
    }


}
