package com.example.WordCFExam.repository.exam;

import android.app.Application;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.example.WordCFExam.dao.RandomExamWordPassedQuestionnaireDao;
import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.RandomExamCounter;
import com.example.WordCFExam.entity.exam.RandomExamWordPassedQuestionnaire;
import com.example.WordCFExam.repository.BaseCrudRepository;

import java.util.List;

public class RandomExamWordPassedQuestionnaireRepository extends BaseCrudRepository<RandomExamWordPassedQuestionnaireDao, RandomExamWordPassedQuestionnaire> {

    public RandomExamWordPassedQuestionnaireRepository(Application application) {
        super(application);
        super.setDao(super.getAppDatabase().randomExamWordPassedQuestionnaireDao());
    }


    public List<Word> findAllForeignRandom(Long profileID, Long fromLanguageID, Long toLanguageID, Integer countNumber){
        return super.getDao().findAllForeignRandom(profileID,fromLanguageID,toLanguageID,countNumber);
    }

    public List<Word>  findAllNativeRandom(Long profileID, Long fromLanguageID, Long toLanguageID,Integer countNumber){
        return super.getDao().findAllNativeRandom(profileID,fromLanguageID,toLanguageID,countNumber);
    }


    public RandomExamCounter findAllNativeRandomCounter(Long profileID, Long fromLanguageID, Long toLanguageID){
        return super.getDao().findAllNativeRandomCounter(profileID,fromLanguageID,toLanguageID);
    }

    public RandomExamCounter findAllForeignRandomCounter(Long profileID, Long fromLanguageID, Long toLanguageID){
        return super.getDao().findAllForeignRandomCounter(profileID,fromLanguageID,toLanguageID);
    }


    public void deleteAll(Long profileID, Long fromLanguageID,Long toLanguageID){
            getDao().deleteAll(profileID, fromLanguageID, toLanguageID);
    }


}
