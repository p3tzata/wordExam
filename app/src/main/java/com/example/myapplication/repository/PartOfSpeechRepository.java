package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.PartOfSpeechDao;
import com.example.myapplication.dao.ProfileDao;
import com.example.myapplication.entity.PartOfSpeech;
import com.example.myapplication.entity.Profile;

import java.util.List;

public class PartOfSpeechRepository extends CrudRepository<PartOfSpeechDao, PartOfSpeech> {

    public PartOfSpeechRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().partOfSpeechDao());
    }

     public List<PartOfSpeech> findAllByLanguageID(Long languageID){
        return super.getDao().findAllByLanguageID(languageID);
    }

}
