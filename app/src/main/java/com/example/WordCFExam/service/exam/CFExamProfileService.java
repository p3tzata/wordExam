package com.example.WordCFExam.service.exam;

import android.app.Application;

import com.example.WordCFExam.entity.exam.CFExamProfile;
import com.example.WordCFExam.repository.exam.CFExamProfileRepository;
import com.example.WordCFExam.service.base.BaseNameCrudService;
import com.example.WordCFExam.service.base.NameableCrudService;


public class CFExamProfileService extends BaseNameCrudService<CFExamProfileRepository, CFExamProfile>
implements NameableCrudService<CFExamProfile> {

    public CFExamProfileService(Application application) {
        super(application,new CFExamProfileRepository(application));
    }


}
