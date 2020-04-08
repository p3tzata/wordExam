package com.example.myapplication.repository.exam;

import android.app.Application;

import com.example.myapplication.dao.CFExamTopicQuestionnaireDao;
import com.example.myapplication.dao.CFExamWordQuestionnaireDao;
import com.example.myapplication.entity.exam.CFExamTopicQuestionnaire;
import com.example.myapplication.entity.exam.CFExamWordQuestionnaire;
import com.example.myapplication.entity.exam.CFExamWordQuestionnaireCross;
import com.example.myapplication.repository.BaseCrudRepository;

import java.util.List;

public class CFExamTopicQuestionnaireRepository extends BaseCrudRepository<CFExamTopicQuestionnaireDao, CFExamTopicQuestionnaire> {

    public CFExamTopicQuestionnaireRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().cfExamTopicQuestionnaireDao());
    }

    public List<CFExamTopicQuestionnaire> findAllNeedProceed(Long currentTime){
        return getDao().findAllNeedProceed(currentTime);
    }

}
