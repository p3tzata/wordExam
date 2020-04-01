package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.WordForm;
import com.example.myapplication.repository.WordFormRepository;
import com.example.myapplication.service.base.BaseCrudService;
import com.example.myapplication.service.base.BaseNameCrudService;

import java.util.List;


public class WordFormService extends BaseNameCrudService<WordFormRepository, WordForm> {

    public WordFormService(Application application) {
        super(application,new WordFormRepository(application));
    }


    public List<WordForm> findAllByLanguageID(Long ID){
        return super.getRepository().findAllByLanguageID(ID);
    }


}
