package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.PartOfSpeech;
import com.example.myapplication.repository.PartOfSpeechRepository;
import com.example.myapplication.service.base.BaseCrudService;

import java.util.List;


public class PartOfSpeechService extends BaseCrudService<PartOfSpeechRepository, PartOfSpeech> {

    public PartOfSpeechService(Application application) {
        super(application,new PartOfSpeechRepository(application));
    }

    public List<PartOfSpeech> findAllByLanguageID(Long languageID){
        return super.getRepository().findAllByLanguageID(languageID);
    }




}
