package com.example.WordCFExam.repository.exam;

import android.app.Application;
import android.content.Context;

import com.example.WordCFExam.dao.CFExamWordQuestionnaireDao;
import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaire;
import com.example.WordCFExam.entity.exam.CFExamWordQuestionnaireCross;
import com.example.WordCFExam.repository.BaseCrudRepository;

import java.util.List;

public class CFExamWordQuestionnaireRepository extends BaseCrudRepository<CFExamWordQuestionnaireDao, CFExamWordQuestionnaire> {

    public CFExamWordQuestionnaireRepository(Context context) {
        super(context);
        super.setDao(super.getAppDatabase().cfExamQuestionnaireDao());
    }

    public List<Profile> findAllProfileNeedProceed(Long currentTime,int currentHour){
        return getDao().findAllProfileNeedProceed(currentTime,currentHour);
    }

    public List<CFExamWordQuestionnaireCross> findAllNeedProceed(Long profileID,Long currentTime){
        return getDao().findAllNeedProceed(profileID,currentTime);
    }



    public CFExamWordQuestionnaireCross findByWordID(Long wordID, Long toLanguageID){
       return getDao().findByWordID(wordID, toLanguageID);
    }


}
