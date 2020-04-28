package com.example.WordCFExam.repository.exam;

import android.app.Application;

import com.example.WordCFExam.dao.RandomExamHelpSentencePassedQuestionnaireDao;
import com.example.WordCFExam.dao.RandomExamWordPassedQuestionnaireDao;
import com.example.WordCFExam.entity.HelpSentence;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.RandomExamCounter;
import com.example.WordCFExam.entity.exam.RandomExamHelpSentencePassedQuestionnaire;
import com.example.WordCFExam.entity.exam.RandomExamWordPassedQuestionnaire;
import com.example.WordCFExam.repository.BaseCrudRepository;

import java.util.List;

public class RandomExamHelpSentencePassedQuestionnaireRepository extends BaseCrudRepository<RandomExamHelpSentencePassedQuestionnaireDao, RandomExamHelpSentencePassedQuestionnaire> {

    public RandomExamHelpSentencePassedQuestionnaireRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().randomExamHelpSentencePassedQuestionnaireDao());
    }


    public List<HelpSentence> findAllForeignRandom(Long profileID, Long fromLanguageID, Long toLanguageID, Integer countNumber){
        return super.getDao().findAllForeignRandom(profileID,fromLanguageID,toLanguageID,countNumber);
    }

    public List<HelpSentence>  findAllNativeRandom(Long profileID, Long fromLanguageID, Long toLanguageID,Integer countNumber){
        return super.getDao().findAllNativeRandom(profileID,fromLanguageID,toLanguageID,countNumber);
    }

    public void deleteNativeAll(Long profileID, Long fromLanguageID,Long toLanguageID){
            getDao().deleteNativeAll(profileID, fromLanguageID, toLanguageID);
    }

    public void deleteForeignAll(Long profileID, Long fromLanguageID,Long toLanguageID){
        getDao().deleteForeignAll(profileID, fromLanguageID, toLanguageID);
    }

    public RandomExamCounter findAllNativeRandomCounter(Long profileID, Long fromLanguageID,Long toLanguageID){
        return getDao().findAllNativeRandomCounter(profileID,fromLanguageID,toLanguageID);
    }

    public RandomExamCounter findAllForeignRandomCounter(Long profileID, Long fromLanguageID,Long toLanguageID){
        return getDao().findAllForeignRandomCounter(profileID,fromLanguageID,toLanguageID);
    }


}
