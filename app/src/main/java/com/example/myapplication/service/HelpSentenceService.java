package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.entity.Profile;
import com.example.myapplication.repository.HelpSentenceRepository;
import com.example.myapplication.repository.ProfileRepository;

import java.util.List;


public class HelpSentenceService extends CrudService<HelpSentenceRepository, HelpSentence> {

    public HelpSentenceService(Application application) {
        super(application,new HelpSentenceRepository(application));
    }

    public List<HelpSentence> findAllByWordID(Long ID){
        return super.getRepository().findAllByWordID(ID);
    }

}
