package com.example.WordCFExam.repository;

import android.app.Application;

import com.example.WordCFExam.dao.WordFormDao;
import com.example.WordCFExam.entity.WordForm;

import java.util.List;

public class WordFormRepository extends BaseNameCrudRepository<WordFormDao, WordForm> {

    public WordFormRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().wordFormDao());
    }

    public List<WordForm> findAllByLanguageID(Long ID){
        return super.getDao().findAllByLanguageID(ID);
    }


}
