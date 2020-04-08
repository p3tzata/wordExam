package com.example.myapplication.repository.exam;

import android.app.Application;

import com.example.myapplication.dao.CFExamWordQuestionnaireDao;
import com.example.myapplication.entity.exam.CFExamWordQuestionnaire;
import com.example.myapplication.entity.exam.CFExamWordQuestionnaireCross;
import com.example.myapplication.repository.BaseCrudRepository;

import java.util.List;

public class CFExamWordQuestionnaireRepository extends BaseCrudRepository<CFExamWordQuestionnaireDao, CFExamWordQuestionnaire> {

    public CFExamWordQuestionnaireRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().cfExamQuestionnaireDao());
    }

    public List<CFExamWordQuestionnaireCross> findAllNeedProceed(Long currentTime){
        return getDao().findAllNeedProceed(currentTime);
    }

}
