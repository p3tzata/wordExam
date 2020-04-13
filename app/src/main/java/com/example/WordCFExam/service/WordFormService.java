package com.example.WordCFExam.service;

import android.app.Application;

import com.example.WordCFExam.entity.WordForm;
import com.example.WordCFExam.repository.WordFormRepository;
import com.example.WordCFExam.service.base.BaseNameCrudService;

import java.util.List;


public class WordFormService extends BaseNameCrudService<WordFormRepository, WordForm> {

    public WordFormService(Application application) {
        super(application,new WordFormRepository(application));
    }


    public List<WordForm> findAllByLanguageID(Long ID){
        return super.getRepository().findAllByLanguageID(ID);
    }


}
