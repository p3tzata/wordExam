package com.example.myapplication.service.exam;

import android.app.Application;

import com.example.myapplication.entity.Word;
import com.example.myapplication.entity.exam.RandomExamPassedQuestionnaire;
import com.example.myapplication.repository.exam.RandomExamPassedQuestionnaireRepository;

import java.util.List;


public class RandomExamWordQuestionnaireService extends BaseExamQuestionnaireService<RandomExamPassedQuestionnaireRepository,RandomExamPassedQuestionnaire> {

    private CFExamProfilePointService cfExamProfilePointService;


    public RandomExamWordQuestionnaireService(Application application) {
        super(application,new RandomExamPassedQuestionnaireRepository(application));

    }


    @Override
    public boolean examProcessedOK(RandomExamPassedQuestionnaire item) {
        getRepository().inset(item);
        return true;
    }

    @Override
    public boolean examProcessedFail(RandomExamPassedQuestionnaire item) {

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







}
