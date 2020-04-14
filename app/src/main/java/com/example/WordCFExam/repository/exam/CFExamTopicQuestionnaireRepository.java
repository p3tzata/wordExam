package com.example.WordCFExam.repository.exam;

import android.app.Application;

import com.example.WordCFExam.dao.CFExamTopicQuestionnaireDao;
import com.example.WordCFExam.entity.Profile;
import com.example.WordCFExam.entity.exam.CFExamTopicQuestionnaire;
import com.example.WordCFExam.entity.exam.CFExamTopicQuestionnaireCross;
import com.example.WordCFExam.repository.BaseCrudRepository;

import java.util.List;

public class CFExamTopicQuestionnaireRepository extends BaseCrudRepository<CFExamTopicQuestionnaireDao, CFExamTopicQuestionnaire> {

    public CFExamTopicQuestionnaireRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().cfExamTopicQuestionnaireDao());
    }

    public List<CFExamTopicQuestionnaireCross> findAllNeedProceed(Long profileID,Long currentTime){
        return getDao().findAllNeedProceed(profileID,currentTime);
    }

    public List<Profile> findAllProfileNeedProceed(Long currentTime){
        return getDao().findAllProfileNeedProceed(currentTime);
    }


    public CFExamTopicQuestionnaire findByTopicID(Long topicID){
        return getDao().findByTopicID(topicID);
    }

}
