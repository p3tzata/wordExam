package com.example.myapplication.service.exam;

import android.app.Application;

import com.example.myapplication.entity.HelpSentence;
import com.example.myapplication.entity.exam.CFExamProfile;
import com.example.myapplication.repository.HelpSentenceRepository;
import com.example.myapplication.repository.exam.CFExamProfileRepository;
import com.example.myapplication.service.base.BaseNameCrudService;
import com.example.myapplication.service.base.NameableCrudService;

import java.util.List;


public class CFExamProfileService extends BaseNameCrudService<CFExamProfileRepository, CFExamProfile>
implements NameableCrudService<CFExamProfile> {

    public CFExamProfileService(Application application) {
        super(application,new CFExamProfileRepository(application));
    }


}
