package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.repository.HelpSentenceRepository;

import java.util.List;


public class HelpSentenceService extends BaseCrudService<HelpSentenceRepository, HelpSentence>
implements NameableCrudService<HelpSentence>{

    public HelpSentenceService(Application application) {
        super(application,new HelpSentenceRepository(application));
    }

    public List<HelpSentence> findAllByWordID(Long ID){
        return super.getRepository().findAllByWordID(ID);
    }

    @Override
    public List<HelpSentence> findAllOrderAlphabetic(Object... objects) {

        if (objects.length==1) {
            if (objects[0] instanceof Long) {
                Long wordID =  (Long) objects[0];
               return super.getRepository().findAllByWordID(wordID);
            }
       }
        return null;
    }
}
