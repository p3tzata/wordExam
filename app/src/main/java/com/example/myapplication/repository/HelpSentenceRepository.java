package com.example.myapplication.repository;

import android.app.Application;

import com.example.myapplication.dao.HelpSentenceDao;
import com.example.myapplication.dao.ProfileDao;
import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.entity.Profile;

import java.util.List;

public class HelpSentenceRepository extends CrudRepository<HelpSentenceDao, HelpSentence> {

    public HelpSentenceRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().helpSentenceDao());
    }

    public List<HelpSentence> findAllByWordID(Long ID){
    return super.getDao().findAllByWordID(ID);
    }


}