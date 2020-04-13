package com.example.WordCFExam.service;

import android.app.Application;

import com.example.WordCFExam.entity.PartOfSpeech;
import com.example.WordCFExam.repository.PartOfSpeechRepository;
import com.example.WordCFExam.service.base.BaseNameCrudService;

import java.util.List;


public class PartOfSpeechService extends BaseNameCrudService<PartOfSpeechRepository, PartOfSpeech> {

    public PartOfSpeechService(Application application) {
        super(application,new PartOfSpeechRepository(application));
    }

    public List<PartOfSpeech> findAllByLanguageID(Long languageID){
        return super.getRepository().findAllByLanguageID(languageID);
    }




}
