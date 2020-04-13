package com.example.WordCFExam.repository.exam;

import android.app.Application;

import com.example.WordCFExam.dao.CFExamWordQuestionnaireDao;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaire;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaireCross;
import com.example.WordCFExam.repository.BaseCrudRepository;

import java.util.List;

public class CFExamWordQuestionnaireRepository extends BaseCrudRepository<CFExamWordQuestionnaireDao, CFExamWordQuestionnaire> {

    public CFExamWordQuestionnaireRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().cfExamQuestionnaireDao());
    }

    public List<CFExamWordQuestionnaireCross> findAllNeedProceed(Long currentTime){
        return getDao().findAllNeedProceed(currentTime);
    }

    public CFExamWordQuestionnaire findByWordID(Long wordID, Long toLanguageID){
       return getDao().findByWordID(wordID, toLanguageID);
    }


}
