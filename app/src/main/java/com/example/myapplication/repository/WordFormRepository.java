package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.WordFormDao;
import com.example.myapplication.entity.WordForm;

import java.util.List;

public class WordFormRepository extends BaseCrudRepository<WordFormDao, WordForm> {

    public WordFormRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().wordFormDao());
    }

    public List<WordForm> findAllByLanguageID(Long ID){
        return super.getDao().findAllByLanguageID(ID);
    }


}
