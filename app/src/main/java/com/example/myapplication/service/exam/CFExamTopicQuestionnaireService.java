package com.example.myapplication.service.exam;

import android.app.Application;

import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.exam.CFExamTopicQuestionnaire;
import com.example.myapplication.entity.exam.RandomExamPassedQuestionnaire;
import com.example.myapplication.repository.exam.CFExamTopicQuestionnaireRepository;
import com.example.myapplication.repository.exam.RandomExamPassedQuestionnaireRepository;

import java.util.List;


public class CFExamTopicQuestionnaireService extends
        BaseExamQuestionnaireService<CFExamTopicQuestionnaireRepository, CFExamTopicQuestionnaire> {

    private CFExamProfilePointService cfExamProfilePointService;


    public CFExamTopicQuestionnaireService(Application application) {
        super(application,new CFExamTopicQuestionnaireRepository(application));

    }


    //TODO
    @Override
    public boolean examProcessedOK(CFExamTopicQuestionnaire item) {

        return true;
    }

    //TODO
    @Override
    public boolean examProcessedFail(CFExamTopicQuestionnaire item) {

        return true;


    }












}
