package com.example.WordCFExam.service.exam;

import android.app.Application;

import com.example.WordCFExam.entity.Word;
import com.example.WordCFExam.entity.dto.RandomExamCounter;
import com.example.WordCFExam.entity.exam.RandomExamWordPassedQuestionnaire;
import com.example.WordCFExam.repository.exam.RandomExamWordPassedQuestionnaireRepository;

import java.util.List;


public class RandomExamWordQuestionnaireService extends BaseExamQuestionnaireService<RandomExamWordPassedQuestionnaireRepository, RandomExamWordPassedQuestionnaire> {

    private CFExamProfilePointService cfExamProfilePointService;


    public RandomExamWordQuestionnaireService(Application application) {
        super(application,new RandomExamWordPassedQuestionnaireRepository(application));

    }


    @Override
    public boolean examProcessedOK(RandomExamWordPassedQuestionnaire item) {
        getRepository().inset(item);
        return true;
    }

    @Override
    public boolean examProcessedFail(RandomExamWordPassedQuestionnaire item) {

        return true;


    }


    @Override
    public boolean examProcessedFailTotal(RandomExamWordPassedQuestionnaire item) {

        return true;


    }


    public List<Word>  findAllNativeRandom(Long profileID, Long fromLanguageID, Long toLanguageID,Integer countNumber){

        List<Word> allNativeRandom = super.getRepository().findAllNativeRandom(profileID, fromLanguageID, toLanguageID, countNumber);
        if (allNativeRandom.size()==0) {
            getRepository().deleteAll(profileID, fromLanguageID, toLanguageID);
            return super.getRepository().findAllNativeRandom(profileID, fromLanguageID, toLanguageID, countNumber);
        }
        return allNativeRandom;

    }

    public List<Word>  findAllForeignRandom(Long profileID, Long fromLanguageID, Long toLanguageID,Integer countNumber){

        List<Word> allForeignRandom = super.getRepository().findAllForeignRandom(profileID, fromLanguageID, toLanguageID, countNumber);
        if (allForeignRandom.size()==0) {
            getRepository().deleteAll(profileID, fromLanguageID, toLanguageID);
            return super.getRepository().findAllForeignRandom(profileID, fromLanguageID, toLanguageID, countNumber);
        }
        return allForeignRandom;
    }




    public RandomExamCounter findAllNativeRandomCounter(Long profileID, Long fromLanguageID, Long toLanguageID){
        return getRepository().findAllNativeRandomCounter(profileID,fromLanguageID,toLanguageID);
    }

    public RandomExamCounter findAllForeignRandomCounter(Long profileID, Long fromLanguageID, Long toLanguageID){
        return getRepository().findAllForeignRandomCounter(profileID,fromLanguageID,toLanguageID);
    }


}
