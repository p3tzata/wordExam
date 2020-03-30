package com.example.myapplication.service;

import android.app.Application;

import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.repository.HelpSentenceRepository;
import com.example.myapplication.service.base.BaseCrudService;
import com.example.myapplication.service.base.BaseNameCrudService;
import com.example.myapplication.service.base.NameableCrudService;

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
