package com.example.WordCFExam.repository;

import android.app.Application;

import com.example.WordCFExam.dao.HelpSentenceDao;
import com.example.WordCFExam.entity.HelpSentence;

import java.util.List;

public class HelpSentenceRepository extends BaseNameCrudRepository<HelpSentenceDao, HelpSentence> {

    public HelpSentenceRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().helpSentenceDao());
    }

    public List<HelpSentence> findAllByWordID(Long ID){
    return super.getDao().findAllByWordID(ID);
    }


}
