package com.example.WordCFExam.repository.exam;

import android.app.Application;

import com.example.WordCFExam.dao.RandomExamPassedQuestionnaireDao;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.exam.RandomExamPassedQuestionnaire;
import com.example.WordCFExam.repository.BaseCrudRepository;

import java.util.List;

public class RandomExamPassedQuestionnaireRepository extends BaseCrudRepository<RandomExamPassedQuestionnaireDao, RandomExamPassedQuestionnaire> {

    public RandomExamPassedQuestionnaireRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().randomExamPassedQuestionnaireDao());
    }


    public List<Word> findAllForeignRandom(Long profileID, Long fromLanguageID, Long toLanguageID, Integer countNumber){
        return super.getDao().findAllForeignRandom(profileID,fromLanguageID,toLanguageID,countNumber);
    }

    public List<Word>  findAllNativeRandom(Long profileID, Long fromLanguageID, Long toLanguageID,Integer countNumber){
        return super.getDao().findAllNativeRandom(profileID,fromLanguageID,toLanguageID,countNumber);
    }

    public void deleteAll(Long profileID, Long fromLanguageID,Long toLanguageID){
            getDao().deleteAll(profileID, fromLanguageID, toLanguageID);
    }


}
