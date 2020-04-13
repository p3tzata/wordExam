package com.example.WordCFExam.service;

import android.app.Application;

import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.repository.HelpSentenceRepository;
import com.example.WordCFExam.service.base.BaseNameCrudService;
import com.example.WordCFExam.service.base.NameableCrudService;

import java.util.List;


public class HelpSentenceService extends BaseNameCrudService<HelpSentenceRepository, HelpSentence>
implements NameableCrudService<HelpSentence> {

    public HelpSentenceService(Application application) {
        super(application,new HelpSentenceRepository(application));
    }

    public List<HelpSentence> findAllByWordID(Long ID){
        return super.getRepository().findAllByWordID(ID);
    }


}
